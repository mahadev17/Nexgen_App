package com.linkites.nexgenfilmfestival;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.VideoView;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class RatingVideo extends Activity{
	
	
	Context mContext;
	VideoView view;
	String result;
	String videoPath;
	String deviceId;
	String videoId;
	int ratingActor,ratingActress,ratingStory,ratingActing,ratingCinematography;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.rating_video);
	mContext=this;
	
	RatingBar ratingBarStory=(RatingBar)findViewById(R.id.ratingBar1);
	RatingBar ratingBarActor=(RatingBar)findViewById(R.id.ratingBar3);
	RatingBar ratingBarActress=(RatingBar)findViewById(R.id.ratingBar4);
	RatingBar ratingBarActing=(RatingBar)findViewById(R.id.ratingBar5);
	RatingBar ratingBarCinematography=(RatingBar)findViewById(R.id.ratingBar2);
	
	ratingActing=(int) ratingBarActing.getRating();
	ratingActing=(int) ratingBarActing.getRating();
	ratingActing=(int) ratingBarActing.getRating();
	ratingActing=(int) ratingBarActing.getRating();
	ratingActing=(int) ratingBarActing.getRating();
	
	
	
	new GetVideos().execute();
	 view = (VideoView)findViewById(R.id.videoView1);
	view.setVisibility(View.VISIBLE);
	Uri video = Uri.parse(Constant.imageURL+videoPath);
	//String path = "android.resource://" + getPackageName() + "/" + R.r
	//Uri uri = Uri.parse(video);
	view.setVideoURI(video);
	
	view.start();
	}

	
	public class GetVideos extends AsyncTask<String, String, String>{

		Dialog dialog;
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			videoPath=result;
			super.onPostExecute(result);
			dialog.dismiss();
			Uri video = Uri.parse(Constant.imageURL+videoPath);
			//String path = "android.resource://" + getPackageName() + "/" + R.r
			//Uri uri = Uri.parse(video);
			view.setVideoURI(video);
			
			view.start();
			
		
		}

		

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			String filepath = null;
			String url=Constant.serverURL+"method=GetVideos";
			result= Utility.findJSONFromUrl(url);
			try {
				JSONObject jsonObject=new JSONObject(result);
				
				JSONArray jsonArray=jsonObject.getJSONArray("videos");
				
				for(int i=0;i<jsonArray.length();i++){
					JSONObject videoObject=jsonArray.getJSONObject(0);
					JSONObject vidObject=videoObject.getJSONObject("video");
					 filepath=vidObject.getString("filepath");
					 videoId=vidObject.getString("videoid");
					 deviceId=vidObject.getString("deviceid");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return filepath;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(mContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();
		}
	}
}
