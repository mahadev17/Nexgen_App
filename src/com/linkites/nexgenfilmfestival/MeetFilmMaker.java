package com.linkites.nexgenfilmfestival;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;


public class MeetFilmMaker extends Activity {
	CommentAdapter adapter;
	Button btn_comment;
	ListView listView;
	ArrayList<String> list;
	String UserId, userType;
	String film_id;
	ImageView imageView;
	TextView tvName,tvAge,tvAboutMe,textView1;
	Dialog mChooseImageDialog;
	String urlImage;
	Context appContext=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.meet_filmmaker);
		btn_comment = (Button) findViewById(R.id.btn_post);
		textView1=(TextView)findViewById(R.id.textView151);
		listView = (ListView) findViewById(R.id.listViewComment);
		tvName=(TextView)findViewById(R.id.textName1);
		tvAge=(TextView)findViewById(R.id.textAge);
		tvAboutMe=(TextView)findViewById(R.id.textDescription2);
		imageView=(ImageView)findViewById(R.id.imageProfile);
		
		UserId = Utility.getSharedPreferences(MeetFilmMaker.this, "UserId");
		userType = Utility.getSharedPreferences(MeetFilmMaker.this, "userType");

		film_id=Utility.getSharedPreferences(MeetFilmMaker.this, "FilmId");
		
		new GetFilmData().execute();
		new GetComment().execute();
		
		list = new ArrayList<String>();
		adapter = new CommentAdapter(list, this);
		listView.setAdapter(adapter);
		btn_comment.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeetFilmMaker.this,
						CommentActivity.class);
			
				startActivity(intent);
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		new GetComment().execute();
	}
	public class GetFilmData extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			mChooseImageDialog = new Dialog(appContext);
			mChooseImageDialog.setContentView(R.layout.custom_dialog_view);
			mChooseImageDialog.setTitle("NexGen");
			mChooseImageDialog.show();
		}
		String result;
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... arg0) {
			String url = Constant.serverURL + "method=GetFilmMakerInfo&film_id="+ 3;
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);
			return result;
		}
		

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result==null){
				
				Utility.ShowAlertWithMessage(appContext, R.string.Alert,
						R.string.network_connection);
			}else{
				try{
					JSONObject jsonObject=new JSONObject(result);
					JSONObject jsonObject2=jsonObject.getJSONObject("filmmaker");
					String name=jsonObject2.getString("name");
					tvName.setText(name);
					
					
					String age=jsonObject2.getString("age");
					if(age.contains("")){
						
					}else{
						textView1.setVisibility(View.VISIBLE);
						tvAge.setText(age);
					}
					tvAge.setText(age);
					String aboutMe=jsonObject2.getString("about_me");
					tvAboutMe.setText(aboutMe);
					urlImage=jsonObject2.getString("image");
					
					new DownloadImageTask().execute();
					
					}catch (Exception e) {
						// TODO: handle exception
					}
				
			}
		}
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	   

	   

	    protected Bitmap doInBackground(String... urls) {
	        String url=Constant.imageURL+urlImage;
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(url).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	    	
	    	mChooseImageDialog.dismiss();
	        imageView.setImageBitmap(result);
	    }
	}
/*	public static Bitmap loadBitmap(String url) {
	    Bitmap bitmap = null;
	    InputStream in = null;
	    BufferedOutputStream out = null;

	    try {
	        in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

	        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	        out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
	        copy(in, out);
	        out.flush();

	        final byte[] data = dataStream.toByteArray();
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        //options.inSampleSize = 1;

	        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
	    } catch (IOException e) {
	        Log.e(TAG, "Could not load Bitmap from: " + url);
	    } finally {
	        closeStream(in);
	        closeStream(out);
	    }

	    return bitmap;
	}
*/	
	
	
	public class GetComment extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		String result;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			
			String url = Constant.serverURL + "method=GetComments&film_id="+ 3;
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			parseJson(result);
		}
	}

	/**
	 * @param result
	 */
	public void parseJson(String result) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArrayUser = jsonObject.getJSONArray("comments");
			for (int i = 0; i < jsonArrayUser.length(); i++) {
				JSONObject jsonObject2 = jsonArrayUser.getJSONObject(i);
				String jsonObject3 = jsonObject2.getString("comment");
				list.add(jsonObject3);

				Log.d("Check", "cHECK:");
			}
			

			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
