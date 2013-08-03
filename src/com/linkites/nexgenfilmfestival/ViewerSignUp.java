package com.linkites.nexgenfilmfestival;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class ViewerSignUp extends Activity {
	
	Button btnViewerSignUp;
	private ArrayList<NameValuePair> listNameValuePairs;
	EditText edViewerName,edViewerEmailId,edViewerPassword,edViewerConfirmPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.viewer_registration);

		btnViewerSignUp=(Button)findViewById(R.id.btn_viewer_login);
		edViewerConfirmPassword=(EditText)findViewById(R.id.edit_viewer_confirm_pass);
		edViewerEmailId=(EditText)findViewById(R.id.edit_viewer_email);
		edViewerName=(EditText)findViewById(R.id.edit_viewer);
		edViewerPassword=(EditText)findViewById(R.id.edit_viewer_pass);
		listNameValuePairs = new ArrayList<NameValuePair>();
		
		btnViewerSignUp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String Viewername=edViewerName.getText().toString().trim();
				String viewerId=edViewerEmailId.getText().toString().trim();
				String ViewerPassword=edViewerPassword.getText().toString().trim();
				String ViewerConfirmPassword=edViewerConfirmPassword.getText().toString().trim();
				
				
				TelephonyManager telephone_manager = (TelephonyManager) ViewerSignUp.this.getSystemService(Context.TELEPHONY_SERVICE);
				String device_id = telephone_manager.getDeviceId();
				
				listNameValuePairs.add(new BasicNameValuePair("viewer_name",Viewername));
				listNameValuePairs.add(new BasicNameValuePair("viewer_email",viewerId));
				listNameValuePairs.add(new BasicNameValuePair("password",ViewerPassword));
				listNameValuePairs.add(new BasicNameValuePair("token_id","0"));
				listNameValuePairs.add(new BasicNameValuePair("device_id",device_id));
				
				if(ViewerPassword.equalsIgnoreCase(ViewerConfirmPassword)){
					new PostDatatoServerViewer().execute();
				}else{
					Toast.makeText(ViewerSignUp.this, "Re-Insert password", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}
	
	
	public class PostDatatoServerViewer extends AsyncTask<Void, Void, String> {
		String result;

		protected void onPreExecute() {
			super.onPreExecute();
			/*applicationDialog = ProgressDialog.show(PostDatatoServer.this, "",
					"Please Wait ");*/
			//applicationDialog.setCancelable(false);
		}
		

		@Override
		protected String doInBackground(Void... params) {
			
			String url = Constant.serverURL + "method=ViewerSignUp";
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			listNameValuePairs.clear();
			return result;
		}
		protected void onPostExecute(String result) {
			//applicationDialog.cancel();
			System.out.println("value of result in create account " + result);
			/*if (result == null) {
				Utility.ShowAlertWithMessage(appContext, R.string.Alert,
						R.string.Alert_internetConnection);
			//	applicationDialog = null;
			} else if (result.contains("1")) {
				
				first_name_edt.setText("");
				last_name_edt.setText("");
				email_edt.setText("");
				password_edt.setText("");

				System.out.println("value of result in sign up" + result);
				result = null;
				Utility.ShowAlertWithMessage(appContext, R.string.Alert,
						R.string.alert_signup, true);
							
			}else if (result.contains("0")) {
				
				Utility.ShowAlertWithMessage(appContext, R.string.Alert,R.string.alert_failed);
			}*/
			
			//btn_countinue.setText("");
			if(result.contains("1"))
			{
			edViewerConfirmPassword.setText("");
			edViewerEmailId.setText("");
			edViewerName.setText("");
			edViewerPassword.setText("");
			
			Toast.makeText(getBaseContext(), "Congrats!!! Sign Up Successfull", Toast.LENGTH_SHORT).show();
			Intent viewerIntent=new Intent(ViewerSignUp.this,FestivalViewer.class);
			startActivity(viewerIntent);
			}else if(result.contains("0")){
				Toast.makeText(getBaseContext(), "Oops!!! Something went Wrong", Toast.LENGTH_SHORT).show();
			}else if(result.contains("-1")){
				Toast.makeText(getBaseContext(), "Email Id Already Exists", Toast.LENGTH_SHORT).show();
			}

			}
			
		public void popActivity() {
			listNameValuePairs = null;
			finish();
		}	
	}
	
	
	
	
	
	
	
}
