package com.linkites.nexgenfilmfestival;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.FestivalViewer.GetJackPot;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class LoginActivity extends Activity {
	EditText mUserName, mPassword;
	Button btnLogin, btnForgot;
	Context appContext = this;
	String strUserName;
	Dialog dialog;
	String Value;
	TextView jackpot_txt;
	private String userId, userName;
	private ArrayList<NameValuePair> listNameValuePairs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
		findViewByIds();
		listNameValuePairs = new ArrayList<NameValuePair>();
		/*mUserName.setText("test@test.com");
		mPassword.setText("123456");*/
		
		new GetJackPot().execute();
		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager input_manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				input_manager.hideSoftInputFromWindow(
						btnLogin.getWindowToken(), 0);

				strUserName = mUserName.getText().toString().trim();
				
				if (strUserName.length() == 0) {
					Utility.ShowAlertWithMessage(appContext,
							R.string.Alert, R.string.enter_email_address);
					return;
				}
				String strPassword = mPassword.getText().toString().trim();
				if (strPassword.length() == 0) {
					Utility.ShowAlertWithMessage(appContext,
							R.string.Alert, R.string.enter_password);
					return;
				}
				
				
			

				listNameValuePairs.add(new BasicNameValuePair("email",
						mUserName.getText().toString()));
				listNameValuePairs.add(new BasicNameValuePair("password",
						mPassword.getText().toString()));

				if (isEmailValid(strUserName)) {
					if (checkValiationForData(strPassword)) {
						new PostDatatoServer().execute();

					} else {
						Utility.ShowAlertWithMessage(appContext,
								R.string.Alert, R.string.email_validation);
					}
				} else {
					Utility.ShowAlertWithMessage(appContext, R.string.Alert,
							R.string.email_validation);
				}

			}
		});

		btnForgot.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent forgotIntent = new Intent(LoginActivity.this,
						ForgotPassword.class);
				startActivity(forgotIntent);
			}
		});

	}

	private void findViewByIds() {
		mUserName = (EditText) findViewById(R.id.edit_email);
		mPassword = (EditText) findViewById(R.id.edit_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnForgot = (Button) findViewById(R.id.btn_forgot);
		jackpot_txt=(TextView) findViewById(R.id.jackpot_txt);
	}

	public class PostDatatoServer extends AsyncTask<Void, Void, String> {
		String result;
		Dialog dialog;

		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new Dialog(appContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();

		}

		@Override
		protected String doInBackground(Void... params) {

			String url = Constant.serverURL + "method=SignIn"/*&email="+ +&password=+*/;
			System.out.println("set url is   " + url);
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			//result=Utility.findJSONFromUrl(url);
			listNameValuePairs.clear();

			return result;

		}

		protected void onPostExecute(String result) {
			
			if(result==null){
				Utility.ShowAlertWithMessage(appContext, R.string.Alert,
						R.string.network_connection);
				dialog.dismiss();
			}else if(result.contains("wrong-pass")) {

				Utility.ShowAlertWithMessage(appContext, R.string.Alert,
						R.string.wrong_email_password);
				dialog.dismiss();
			} else {
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("user");
					final String strUserId = jsonObject2.getString("id");
					final String emailStr=jsonObject2.getString("email");
					Utility.setSharedPreference(appContext, "UserId", strUserId);
					Utility.setSharedPreference(appContext, "userType",
							"FilmMaker");
					String paymentStatus=jsonObject2.getString("payment_status");
					if (paymentStatus.equalsIgnoreCase("unpaid")) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								LoginActivity.this);
						builder.setCancelable(false);
						builder.setMessage("Please proceed to payment so as to complete the registration");
						builder.setTitle("Payment");
						// Set behavior of negative button
						builder.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// Cancel the dialog
										//String url = "http://projects.linkites.com/nexgen/paypal.php?fname=narendra&lname=rajput&email=rajputnarendra@gmail.com&userid="+strUserId;
										String url = "http://projects.linkites.com/nexgen/paypal.php?fname=narendra&lname=rajput&email="+emailStr+"&userid="+strUserId;
										Intent i = new Intent(
												Intent.ACTION_VIEW);
										i.setData(Uri.parse(url));
										startActivity(i);
										dialog.cancel();
									}
								});

						builder.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// Cancel the dialog
										/*
										 * String url =
										 * "http://projects.linkites.com/nexgen/paypal.php?fname=narendra&lname=rajput&email=rajputnarendra@gmail.com&userid=2"
										 * ; Intent i = new
										 * Intent(Intent.ACTION_VIEW);
										 * i.setData(Uri.parse(url));
										 * startActivity(i); dialog.cancel();
										 */
									}
								});

						AlertDialog alert = builder.create();
						alert.show();

					}else{
					
					
					Intent intent = new Intent(LoginActivity.this,
							TaboptionsActivity.class);
					
					Utility.setSharedPreference(appContext, "requestBy", 1);
					startActivity(intent);
					finish();
						}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			dialog.dismiss();
		}
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean checkValiationForData(String txt) {
		return (txt.trim().length() == 0) ? false : true;
	}
	
	
	public class GetJackPot extends AsyncTask<Void, Void, String> {

		String result;

		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new Dialog(appContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();

		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String url = Constant.serverURL + "method=GetJackpotPrice";
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);

			if (result != null) {
				try {
					// list_petName.add("Select Pet");
					JSONObject jsonObject = new JSONObject(result);
					jsonObject = jsonObject.getJSONObject("jackpot");

					String jackPotName = jsonObject.getString("name");
					Value = jsonObject.getString("value");

					System.out.println("Jacpot name is   " + jackPotName
							+ "  >><<< " + Value);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		protected void onPostExecute(String result) {

			if (result == null) {
				Utility.ShowAlertWithMessage(appContext, R.string.Alert,
						R.string.network_connection);

			} else {
				dialog.dismiss();
				jackpot_txt.setText(Value);
			}

		}
	}
}
