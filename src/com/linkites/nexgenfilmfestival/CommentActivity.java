/**
 * 
 */
package com.linkites.nexgenfilmfestival;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

/**
 * @author ${Umesh I speak my mind. I never mind what I speak. }
 * 
 *         ${Changes are in your Hands. Do not get Inspired By other}
 */
public class CommentActivity extends Activity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	String userId, filmId, strComment;
	Button btn_post;
	EditText edComment;
	Dialog mChooseImageDialog;
	Context appContext=this;
	String UserId, userType;
	private ArrayList<NameValuePair> listNameValuePairs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.comment);
		

		//filmId = bundle.getString("film_Id");
		UserId = Utility.getSharedPreferences(CommentActivity.this, "UserId");
		userType = Utility.getSharedPreferences(CommentActivity.this, "userType");

		listNameValuePairs = new ArrayList<NameValuePair>();
		btn_post = (Button) findViewById(R.id.btn_post_comment);
		edComment = (EditText) findViewById(R.id.edit_comment);

		btn_post.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				strComment = edComment.getText().toString().trim();
				if(strComment.length()>0){
					listNameValuePairs.add(new BasicNameValuePair("userid",
							UserId));
					listNameValuePairs.add(new BasicNameValuePair("film_id",
							"3"));
					listNameValuePairs.add(new BasicNameValuePair("comment",
							strComment));
					listNameValuePairs.add(new BasicNameValuePair("userType",
							userType));

					new PostComment().execute();
				}else{
					Toast.makeText(appContext, "Please Enter Comment", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}



	public class PostComment extends AsyncTask<String, String, String> {

		String result;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			mChooseImageDialog = new Dialog(appContext);
			mChooseImageDialog.setContentView(R.layout.custom_dialog_view);
			mChooseImageDialog.setTitle("NexGen");
			mChooseImageDialog.show();
		}

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = Constant.serverURL + "method=AddComment";
			System.out.println("set url is   " + url);
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			listNameValuePairs.clear();

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result.contains("1")){
				/*Intent intent=new Intent(CommentActivity.this,MeetFilmMaker.class);
				startActivity(intent);*/
				mChooseImageDialog.dismiss();
				finish();
			}else{
				Toast.makeText(getBaseContext(), "Oops!!! Something went wrong", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
