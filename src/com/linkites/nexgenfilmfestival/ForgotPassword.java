package com.linkites.nexgenfilmfestival;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends Activity {
	
	Button mForgotSubmit;
	String forgotEmail;
	EditText mForgotEmail;
	private ArrayList<NameValuePair> listNameValuePairs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.forgot_password);
		listNameValuePairs = new ArrayList<NameValuePair>();
		mForgotEmail=(EditText)findViewById(R.id.ed_forgot_email);
		mForgotSubmit=(Button)findViewById(R.id.btn_forgot_submit);
		
		mForgotSubmit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			 forgotEmail=mForgotEmail.getText().toString().trim();
			if(RegisterFilmMaker.isEmailValid(forgotEmail)){
				listNameValuePairs.add(new BasicNameValuePair("email",forgotEmail));
				new ForgotpaaswordTask().execute();
			}else{
				Toast.makeText(getBaseContext(), "Please Enter Correct Email Address",Toast.LENGTH_SHORT).show();
			}
			}
		});
	}
	
	
	
	
	
	
	
	public class ForgotpaaswordTask extends AsyncTask<Void, Void, String> {
		String result;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			String url = Constant.serverURL + "method=ChangePassword";
			//listNameValuePairs.add(object)
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			//result=Utility.findJSONFromUrl(url);
			listNameValuePairs.clear();
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.contains("-1")){
				Toast.makeText(getApplicationContext(), "InCorrect Email id", Toast.LENGTH_SHORT).show();
				
			}else if(result.contains("1")){
				mForgotEmail.setText("");
				Intent LoginIntent=new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(LoginIntent);
			}
		}
	}
}
