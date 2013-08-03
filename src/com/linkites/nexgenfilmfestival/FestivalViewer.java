package com.linkites.nexgenfilmfestival;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.linkites.nexgenfilmfestival.utility.Utility;

public class FestivalViewer extends Activity {

	EditText mUserName, mPassword;
	Button btnLogin,btnForgot;
	Context appContext = this;
	String strUserName;
	private String userId, userName;
	Dialog dialog;
	String Value;
	TextView jackpot_txt;
	private ArrayList<NameValuePair> listNameValuePairs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.festival_viewer);

		findViewByIds();

	/*	mUserName.setText("mahadev");
		mPassword.setText("123456");*/
		listNameValuePairs = new ArrayList<NameValuePair>();
		btnForgot=(Button)findViewById(R.id.btn_forgot);
		btnForgot.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(FestivalViewer.this,ViewerForgotPassword.class);
			startActivity(intent);
			}
		});
		new GetJackPot().execute();
		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager input_manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				input_manager.hideSoftInputFromWindow(
						btnLogin.getWindowToken(), 0);

				strUserName = mUserName.getText().toString().trim();
				/*
				 * if (strUserName.length() == 0) {
				 * Utility.ShowAlertWithMessage(this, "",""); return; }
				 */
				String strPassword = mPassword.getText().toString().trim();
				
				  if (strPassword.length() == 0) {
				  Utility.ShowAlertWithMessage(appContext, R.string.Alert,
				  R.string.enter_password); return; }
				 

				// validations passed. Now send data to server
				listNameValuePairs.add(new BasicNameValuePair("viewer_email",
						mUserName.getText().toString()));
				listNameValuePairs.add(new BasicNameValuePair("password",
						mPassword.getText().toString()));

				new PostDatatoServer().execute();
			}
		});
	}

	private void findViewByIds() {
		mUserName = (EditText) findViewById(R.id.edit_email_viewer);
		mPassword = (EditText) findViewById(R.id.edit_password_viewer);
		btnLogin = (Button) findViewById(R.id.btn_login_viewer);
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

			// String
			// newURL=Constant.serverURL+"method=SignIn&email="+"umesh123"+"&password="+"umesh111";

			// result=Utility.findJSONFromUrl(newURL);
			String url = Constant.serverURL + "method=ViewerSignIn";
			System.out.println("set url is   " + url);
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			listNameValuePairs.clear();

			return result;

		}

		protected void onPostExecute(String result) {

			dialog.dismiss();
			if (result.contains("wrong_pass")) {

				Toast.makeText(appContext, "Invalid Email Or Password",
						Toast.LENGTH_SHORT).show();

			} else {
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("user");
					String strUserId = jsonObject2.getString("id");

					Utility.setSharedPreference(appContext, "UserId", strUserId);
					Utility.setSharedPreference(appContext, "userType","Viewer");
					String u=Utility.getSharedPreferences(appContext, "UserId");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(FestivalViewer.this,
						TaboptionsActivity.class);
				Utility.setSharedPreference(appContext, "requestBy", 2);
				startActivity(intent);
				finish();
			}

		}
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
