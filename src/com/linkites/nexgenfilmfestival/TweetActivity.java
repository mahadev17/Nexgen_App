package com.linkites.nexgenfilmfestival;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TweetActivity extends ListActivity {
	Context context;
	TwitAdapter adaptor;
	ArrayList<Tweet> tweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = this;
		new LoadTweets().execute();

		tweets = new ArrayList<Tweet>();
		Tweet tweet = new Tweet();
		tweet.author = "dbradby";
		tweet.content = "Android in space";
		ArrayList<Tweet> items = new ArrayList<Tweet>();
		items.add(tweet);
		adaptor = new TwitAdapter(context, R.layout.list_item, tweets);
		setListAdapter(adaptor);
	}

	public class LoadTweets extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpGet get = new // HttpGet("search.twitter.com/search.json?q=android");
				HttpGet(
						"http://search.twitter.com/search.json?q=nexgenfilmfest");
				HttpResponse rp = hc.execute(get);
				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					result = EntityUtils.toString(rp.getEntity());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				Log.d("Twitter", "Twitter" + result);
				JSONObject root = new JSONObject(result);
				JSONArray sessions = root.getJSONArray("results");
				for (int i = 0; i < sessions.length(); i++) {
					JSONObject session = sessions.getJSONObject(i);
					Tweet tweet = new Tweet();
					tweet.content = session.getString("text");
					tweet.author = session.getString("from_user");
					tweets.add(tweet);
					adaptor.notifyDataSetChanged();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private ArrayList<Tweet> loadTweets() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		try {
			HttpClient hc = new DefaultHttpClient();
			HttpGet get = new // HttpGet("search.twitter.com/search.json?q=android");
			HttpGet("http://search.twitter.com/search.json?q=nexgenfilmfest");
			HttpResponse rp = hc.execute(get);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(rp.getEntity());
				Log.d("Twitter", "Twitter" + result);
				JSONObject root = new JSONObject(result);
				JSONArray sessions = root.getJSONArray("results");
				for (int i = 0; i < sessions.length(); i++) {
					JSONObject session = sessions.getJSONObject(i);
					Tweet tweet = new Tweet();
					tweet.content = session.getString("text");
					tweet.author = session.getString("from_user");
					tweets.add(tweet);
				}
			}
		} catch (Exception e) {
			Log.e("TwitterFeedActivity", "Error loading JSON", e);
		}
		return tweets;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(TweetActivity.this, TaboptionsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {


			
			
final Dialog mChooseImageDialog;

			mChooseImageDialog = new Dialog(TweetActivity.this);
			mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mChooseImageDialog.setContentView(R.layout.log_out);
			mChooseImageDialog.setCancelable(true);

			Button btn_Use_Cam = (Button) mChooseImageDialog
					.findViewById(R.id.btn_restart);
			Button btn_Use_Lib = (Button) mChooseImageDialog
					.findViewById(R.id.btn_no_restart);

			btn_Use_Cam.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					/**
					 * create Intent to take a picture and return control to the
					 * calling application
					 **/


					Intent intent = new Intent(TweetActivity.this, UserCategory.class);
					startActivity(intent);
					mChooseImageDialog.dismiss();
					finish();

				}
			});

			btn_Use_Lib.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					
					mChooseImageDialog.dismiss();
				}
			});
			mChooseImageDialog.show();
	
		}
		return true;
	}
}
