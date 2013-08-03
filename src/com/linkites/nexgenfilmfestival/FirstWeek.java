package com.linkites.nexgenfilmfestival;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.SecondWeek.GetFilms;
import com.linkites.nexgenfilmfestival.utility.CommonUtility;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class FirstWeek extends Activity {

	


	String selectedPath;
	Dialog mChooseImageDialog;
	Button btnFirstWeek, btnSecondWeek, btnThirdWeek, btnFourthWeek, btnFwd;
	boolean isUpload = false;
	LinearLayout firstWeekLayout,SecondWeekLayout,fwdLayout,backLayout;
	Context applicationContext;
	public static int REQ_CODE_PICK_VIDEO = 101;
	private String title, filePath;
	String album, artist;
	String bitmapTostring;
	Bitmap curThumb = null;
	String deviceId;
	String week_id;
	String catagory_id, heading;
	Dialog dialog;
	String pointReward;
	LinearLayout rewardLayout;
	Button btnFifthWeek,btnSixthWeek,btnSeventhWeek,btnEigthWeek,btnBack;
	TextView reward_value_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setContentView(R.layout.schedule_first);
		findViewByIds();
		applicationContext = this;
		
		//int userRequeste=Utility.getSharedPreferences1(applicationContext, "requestBy");
		
		/*if(userRequeste==2){
			new GetRewards().execute();
		}else if(userRequeste==1){
			Toast.makeText(applicationContext, "FilmMaker", Toast.LENGTH_SHORT).show();
		}*/
		
		
		isUpload = Utility.getSharedPreferences2(FirstWeek.this, "Upload");
		if (!isUpload) {/*
						 * final Dialog dialog = new Dialog(this);
						 * dialog.setContentView(R.layout.upload_dialog);
						 * dialog.setTitle("Title...");
						 * 
						 * // set the custom dialog components - text, image and
						 * button TelephonyManager telephone_manager =
						 * (TelephonyManager)
						 * FirstWeek.this.getSystemService(Context
						 * .TELEPHONY_SERVICE); deviceId=
						 * telephone_manager.getDeviceId();
						 * 
						 * 
						 * Button btnUploadYes = (Button) dialog
						 * .findViewById(R.id.btn_upload_yes); Button
						 * btnUploadNo = (Button) dialog
						 * .findViewById(R.id.btn_upload_no); // if button is
						 * clicked, close the custom dialog
						 * btnUploadYes.setOnClickListener(new OnClickListener()
						 * {
						 * 
						 * public void onClick(View v) {
						 * 
						 * Intent intent = new Intent(); //
						 * intent.setType("video/*");
						 * intent.setAction(Intent.ACTION_GET_CONTENT);
						 * intent.setDataAndType(
						 * MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
						 * "video/*"); startActivityForResult(
						 * Intent.createChooser(intent, "Select Video"),
						 * REQ_CODE_PICK_VIDEO);
						 * 
						 * dialog.dismiss(); } });
						 * 
						 * btnUploadNo.setOnClickListener(new OnClickListener()
						 * {
						 * 
						 * public void onClick(View v) { isUpload=false;
						 * Utility.
						 * setSharedPreferenceBoolean(FirstWeek.this,"Upload"
						 * ,isUpload); dialog.dismiss(); } });
						 * 
						 * dialog.show();
						 */
		}
		// addTab(5, intent_calender, R.drawable.calender_tab);
		btnFwd.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				firstWeekLayout.setVisibility(View.GONE);
				SecondWeekLayout.setVisibility(View.VISIBLE);
				fwdLayout.setVisibility(View.GONE);
				backLayout.setVisibility(View.VISIBLE);
				
				/*
				Intent weekIntent = new Intent(FirstWeek.this, TabOptionNew.class);
				startActivity(weekIntent);*/
			}
		});

		btnFirstWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "1";
				new GetFilms().execute();

			}
		});

		btnSecondWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "2";
				new GetFilms().execute();
			}
		});

		btnThirdWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "3";
				new GetFilms().execute();
			}
		});

		btnFourthWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "4";
				new GetFilms().execute();
			}
		});

		/** Second Week Program Buttons**/
		

		btnBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				firstWeekLayout.setVisibility(View.VISIBLE);
				SecondWeekLayout.setVisibility(View.GONE);
				fwdLayout.setVisibility(View.VISIBLE);
				backLayout.setVisibility(View.GONE);
				
			}
		});
		
		btnFifthWeek.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_id = "5";
				new GetFilms().execute();	
			}
		});
		
		btnSixthWeek.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub	
					week_id = "6";
					new GetFilms().execute();
				}
			});

		btnSeventhWeek.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_id = "7";
				new GetFilms().execute();	
			}
		});
		
		btnEigthWeek.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_id = "8";
				new GetFilms().execute();	
			}
		});
		
		
		
		
		
		
		
		
	}

	
	
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Toast.makeText(applicationContext, "Hi I am Back", Toast.LENGTH_SHORT).show();
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

		

			
			
final Dialog mChooseImageDialog;

			mChooseImageDialog = new Dialog(FirstWeek.this);
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


					Intent intent = new Intent(FirstWeek.this, UserCategory.class);
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
	
	
	
	private void findViewByIds() {
		btnFirstWeek = (Button) findViewById(R.id.btn_week1);
		btnSecondWeek = (Button) findViewById(R.id.btn_week2);
		btnThirdWeek = (Button) findViewById(R.id.btn_week3);
		btnFourthWeek = (Button) findViewById(R.id.btn_week4);
		btnFwd = (Button) findViewById(R.id.btn_fwd);
		rewardLayout=(LinearLayout) findViewById(R.id.reward_layout);
		reward_value_txt=(TextView) findViewById(R.id.reward_txt2);
		
		fwdLayout=(LinearLayout)findViewById(R.id.layout_fwd);
		backLayout=(LinearLayout)findViewById(R.id.layout_back);
		firstWeekLayout=(LinearLayout)findViewById(R.id.first_week_layout);
		SecondWeekLayout=(LinearLayout)findViewById(R.id.second_week_layout);
		
		btnFifthWeek=(Button)findViewById(R.id.btn_week5);
		btnSixthWeek=(Button)findViewById(R.id.btn_week6);
		btnSeventhWeek=(Button)findViewById(R.id.btn_week7);
		btnEigthWeek=(Button)findViewById(R.id.btn_week8);
		btnBack=(Button)findViewById(R.id.btn_back);
		
		
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent mediaReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, mediaReturnedIntent);

		if (requestCode == REQ_CODE_PICK_VIDEO) {

			/*
			 * System.out.println("SELECT_VIDEO"); Uri selectedImageUri =
			 * data.getData(); selectedPath = getPath(selectedImageUri);
			 * System.out.println("SELECT_VIDEO Path : " + selectedPath);
			 */

			Uri selectedVideo = mediaReturnedIntent.getData();

			String[] filePathColumn = { MediaStore.Video.Media.DATA,
					MediaColumns.TITLE, AudioColumns.DURATION,
					MediaColumns.DATA, };
			Cursor cursor = getContentResolver().query(selectedVideo,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			title = cursor.getString(cursor.getColumnIndex(MediaColumns.TITLE));
			System.out.println("Video Name= " + title);
			filePath = cursor.getString(columnIndex);
			System.out.println("filepath is: " + filePath);
			// for retareaving image and other things from video
			int video_column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			ContentResolver thumb = getContentResolver();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			curThumb = MediaStore.Video.Thumbnails.getThumbnail(thumb,
					video_column_index, MediaStore.Video.Thumbnails.MICRO_KIND,
					options);
			if (curThumb != null) {
				bitmapTostring = CommonUtility.encodeTobase64(curThumb);
			} else {
				bitmapTostring = "";
			}

			// for getting genre ,title and year
			String[] array_path = { MediaStore.Video.Media._ID,
					MediaStore.Video.Media.TITLE,
					MediaStore.Video.Media.ARTIST,
					MediaStore.Video.Media.ALBUM, MediaStore.Video.Media.DATA };
			cursor = getContentResolver().query(selectedVideo, array_path,
					null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				/*
				 * artist = getStringFromColumn(cursor,
				 * MediaStore.Video.Media.ARTIST); album =
				 * getStringFromColumn(cursor, MediaStore.Video.Media.ALBUM);
				 */

				artist = "umesh";
				album = "umesh";

				System.out.println("value of artist and album" + artist
						+ "album is" + album);
				if (artist == null && album == null) {
					artist = "";
					album = "";
				}
			}
			cursor.close();

			new SaveVideoFile().execute();

		}
	}

	public String getPath(Uri uri) {
		String selectedImagePath;
		// 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
		} else {
			selectedImagePath = null;
		}

		if (selectedImagePath == null) {
			// 2:OI FILE Manager --- call method: uri.getPath()
			selectedImagePath = uri.getPath();
		}
		return selectedImagePath;
	}

	public class SaveVideoFile extends AsyncTask<String, Void, String> {
		Dialog dialog;

		protected void onPreExecute() {
			dialog = new Dialog(applicationContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			StringBuilder responseBody = null;
			HttpClient client = new DefaultHttpClient();
			String artist1 = "";
			String album1 = "";
			String genre = "";
			String media_image = "";
			FileBody newFile = null;
			String title = "video";
			String strUrl = Constant.serverURL + "method=UplaodVideo";
			if (filePath != null && title != null) {
				artist1 = artist;
				album1 = album;
				media_image = bitmapTostring;
				genre = album1;
				System.out.println("in the block of vcideo////");
				System.out.println("file pat in background" + filePath);
				newFile = new FileBody(new File(filePath));
				if (newFile != null) {
					System.out.println("new file is not null");
				}

			}
			HttpPost post = new HttpPost(strUrl);
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {
				System.out.println("id is  " + catagory_id);

				entity.addPart("user_id", new StringBody("umesh"));
				entity.addPart("device_id", new StringBody(deviceId));
				/*
				 * entity.addPart("artist", new StringBody(artist1));
				 * entity.addPart("album", new StringBody(album1));
				 * entity.addPart("gener", new StringBody(genre));
				 * 
				 * BitmapDrawable bmpDrw =
				 * (BitmapDrawable)applicationContext.getResources
				 * ().getDrawable(R.drawable.ic_launcher); Bitmap bmp =
				 * bmpDrw.getBitmap(); entity.addPart("image", new
				 * StringBody(CommonUtility.encodeTobase64(bmp)));
				 */

				entity.addPart("video", new FileBody(new File(filePath)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			post.setEntity(entity);
			BufferedReader bs = null;
			HttpResponse response = null;
			try {
				response = client.execute(post);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("http response is " + response);
			HttpEntity hEntity = response.getEntity();
			try {
				bs = new BufferedReader(new InputStreamReader(
						hEntity.getContent()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.i("response", "Response length - " + hEntity.getContentLength());
			responseBody = new StringBuilder();
			String s = "";
			while (s != null) {
				responseBody.append(s);
				try {
					s = bs.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			System.out.println("response is : " + responseBody);
			try {
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (responseBody != null)
				return responseBody.toString();
			return null;

		}// methods

		protected void onPostExecute(String result) {
			// applicationDialog.cancel();
			if (result != null) {
				if (result.contains("1")) {

					isUpload = false;
					Utility.setSharedPreferenceBoolean(FirstWeek.this,
							"Upload", isUpload);

				} else {
					/*
					 * CommonUtility.ShowAlertWithMessage(applicationContext,
					 * "", "Failure! Please try it again");
					 */
				}
			} else {
				Utility.ShowAlertWithMessage(applicationContext, R.string.Alert,
						R.string.network_connection);
			}
			dialog.dismiss();
		}

	} // class ends here

	public class GetFilms extends AsyncTask<String, String, String> {
		
		String result;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new Dialog(applicationContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String url = "http://projects.linkites.com/nexgen//Api.php?method=GetPrograms&week_id="
					+ week_id;

			result = Utility.findJSONFromUrl(url);
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try{
				
				
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("program");

			//for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(0);
			
				JSONObject object = jsonObject2.getJSONObject("program");
				
				/*if(result==null){
					Utility.ShowAlertWithMessage(applicationContext, R.string.Alert,
							R.string.network_connection);
					dialog.dismiss();
				}else{*/
					if(week_id.equals(object.getString("week_id"))){
						parseJson(result);
						dialog.dismiss();
					}else{
						dialog.dismiss();
					}
				//}
			
			}catch (Exception e) {
				// TODO: handle exception
				dialog.dismiss();
			}
			
			
		}
		
		
	}

	/**
	 * @param result
	 */
	public void parseJson(String result) {
		
		
		try {

			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("program");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject object = jsonObject2.getJSONObject("program");
				String current = object.getString("current");
				if (current.equalsIgnoreCase("1")) {
					dialog.dismiss();
					Intent intent = new Intent(FirstWeek.this,
							PlayActivity.class);
					intent.putExtra("programId", object.getString("week_id"));
					startActivity(intent);
					finish();
				} else {
					String startDate = object.getString("start_week");
					String endDate = object.getString("end_week");
					showDialogNew(startDate, endDate);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public void showDialogNew(String sDate, String eDate) {
		mChooseImageDialog = new Dialog(FirstWeek.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.display);
		mChooseImageDialog.setCancelable(true);

		Button btn_facebook = (Button) mChooseImageDialog
				.findViewById(R.id.btn_facebook1);
		TextView textView = (TextView) mChooseImageDialog
				.findViewById(R.id.tv1);
		textView.setText("Festival will Start " + sDate + ". And ends on "
				+ eDate);
		btn_facebook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				mChooseImageDialog.dismiss();

			}
		});

		mChooseImageDialog.show();

	}


	public class GetRewards extends AsyncTask<Void, Void, String> {

		String result;
		
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new Dialog(applicationContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String strUserId=Utility.getSharedPreferences(applicationContext, "UserId");
			String url = Constant.serverURL + "method=GetReward&userid="+strUserId;
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);
			
			if (result!= null) {
				try {
					// list_petName.add("Select Pet");
					JSONObject jsonObject = new JSONObject(result);
					jsonObject = jsonObject.getJSONObject("rewards");
					
					pointReward= jsonObject.getString("reward");
					
					System.out.println("point name is   "+pointReward);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		protected void onPostExecute(String result) {

			if (result == null) {
				Utility.ShowAlertWithMessage(applicationContext,
						R.string.Alert, R.string.network_connection);

			} else {
				
				rewardLayout.setVisibility(View.VISIBLE);
				reward_value_txt.setText(pointReward);
				
				System.out.println("REWARD POINT IS  "+pointReward);
				Toast.makeText(applicationContext, "Reward Point is "+pointReward, Toast.LENGTH_SHORT).show();
			}
		
			dialog.dismiss();
		}
	}
	
}















/*
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.CommonUtility;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class FirstWeek extends Activity {

	String selectedPath;
	Dialog mChooseImageDialog;
	Button btnFirstWeek, btnSecondWeek, btnThirdWeek, btnFourthWeek, btnFwd;
	boolean isUpload = false;
	Context applicationContext;
	public static int REQ_CODE_PICK_VIDEO = 101;
	private String title, filePath;
	String album, artist;
	String bitmapTostring;
	Bitmap curThumb = null;
	String deviceId;
	String week_id;
	String catagory_id, heading;
	Dialog dialog;
	String pointReward;
	LinearLayout rewardLayout;
	TextView reward_value_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.schedule_first);
		findViewByIds();
		applicationContext = this;
		
		//int userRequeste=Utility.getSharedPreferences1(applicationContext, "requestBy");
		
		if(userRequeste==2){
			new GetRewards().execute();
		}else if(userRequeste==1){
			Toast.makeText(applicationContext, "FilmMaker", Toast.LENGTH_SHORT).show();
		}
		
		
		isUpload = Utility.getSharedPreferences2(FirstWeek.this, "Upload");
		if (!isUpload) {
						 * final Dialog dialog = new Dialog(this);
						 * dialog.setContentView(R.layout.upload_dialog);
						 * dialog.setTitle("Title...");
						 * 
						 * // set the custom dialog components - text, image and
						 * button TelephonyManager telephone_manager =
						 * (TelephonyManager)
						 * FirstWeek.this.getSystemService(Context
						 * .TELEPHONY_SERVICE); deviceId=
						 * telephone_manager.getDeviceId();
						 * 
						 * 
						 * Button btnUploadYes = (Button) dialog
						 * .findViewById(R.id.btn_upload_yes); Button
						 * btnUploadNo = (Button) dialog
						 * .findViewById(R.id.btn_upload_no); // if button is
						 * clicked, close the custom dialog
						 * btnUploadYes.setOnClickListener(new OnClickListener()
						 * {
						 * 
						 * public void onClick(View v) {
						 * 
						 * Intent intent = new Intent(); //
						 * intent.setType("video/*");
						 * intent.setAction(Intent.ACTION_GET_CONTENT);
						 * intent.setDataAndType(
						 * MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
						 * "video/*"); startActivityForResult(
						 * Intent.createChooser(intent, "Select Video"),
						 * REQ_CODE_PICK_VIDEO);
						 * 
						 * dialog.dismiss(); } });
						 * 
						 * btnUploadNo.setOnClickListener(new OnClickListener()
						 * {
						 * 
						 * public void onClick(View v) { isUpload=false;
						 * Utility.
						 * setSharedPreferenceBoolean(FirstWeek.this,"Upload"
						 * ,isUpload); dialog.dismiss(); } });
						 * 
						 * dialog.show();
						 
		}
		// addTab(5, intent_calender, R.drawable.calender_tab);
		btnFwd.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent weekIntent = new Intent(FirstWeek.this, TabOptionNew.class);
				startActivity(weekIntent);
			}
		});

		btnFirstWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "1";
				new GetFilms().execute();

			}
		});

		btnSecondWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "2";
				new GetFilms().execute();
			}
		});

		btnThirdWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "3";
				new GetFilms().execute();
			}
		});

		btnFourthWeek.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				week_id = "4";
				new GetFilms().execute();
			}
		});

		
	}

	private void findViewByIds() {
		btnFirstWeek = (Button) findViewById(R.id.btn_week1);
		btnSecondWeek = (Button) findViewById(R.id.btn_week2);
		btnThirdWeek = (Button) findViewById(R.id.btn_week3);
		btnFourthWeek = (Button) findViewById(R.id.btn_week4);
		btnFwd = (Button) findViewById(R.id.btn_fwd);
		rewardLayout=(LinearLayout) findViewById(R.id.reward_layout);
		reward_value_txt=(TextView) findViewById(R.id.reward_txt2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent mediaReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, mediaReturnedIntent);

		if (requestCode == REQ_CODE_PICK_VIDEO) {

			
			 * System.out.println("SELECT_VIDEO"); Uri selectedImageUri =
			 * data.getData(); selectedPath = getPath(selectedImageUri);
			 * System.out.println("SELECT_VIDEO Path : " + selectedPath);
			 

			Uri selectedVideo = mediaReturnedIntent.getData();

			String[] filePathColumn = { MediaStore.Video.Media.DATA,
					MediaColumns.TITLE, AudioColumns.DURATION,
					MediaColumns.DATA, };
			Cursor cursor = getContentResolver().query(selectedVideo,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			title = cursor.getString(cursor.getColumnIndex(MediaColumns.TITLE));
			System.out.println("Video Name= " + title);
			filePath = cursor.getString(columnIndex);
			System.out.println("filepath is: " + filePath);
			// for retareaving image and other things from video
			int video_column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			ContentResolver thumb = getContentResolver();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			curThumb = MediaStore.Video.Thumbnails.getThumbnail(thumb,
					video_column_index, MediaStore.Video.Thumbnails.MICRO_KIND,
					options);
			if (curThumb != null) {
				bitmapTostring = CommonUtility.encodeTobase64(curThumb);
			} else {
				bitmapTostring = "";
			}

			// for getting genre ,title and year
			String[] array_path = { MediaStore.Video.Media._ID,
					MediaStore.Video.Media.TITLE,
					MediaStore.Video.Media.ARTIST,
					MediaStore.Video.Media.ALBUM, MediaStore.Video.Media.DATA };
			cursor = getContentResolver().query(selectedVideo, array_path,
					null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				
				 * artist = getStringFromColumn(cursor,
				 * MediaStore.Video.Media.ARTIST); album =
				 * getStringFromColumn(cursor, MediaStore.Video.Media.ALBUM);
				 

				artist = "umesh";
				album = "umesh";

				System.out.println("value of artist and album" + artist
						+ "album is" + album);
				if (artist == null && album == null) {
					artist = "";
					album = "";
				}
			}
			cursor.close();

			new SaveVideoFile().execute();

		}
	}

	public String getPath(Uri uri) {
		String selectedImagePath;
		// 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
		} else {
			selectedImagePath = null;
		}

		if (selectedImagePath == null) {
			// 2:OI FILE Manager --- call method: uri.getPath()
			selectedImagePath = uri.getPath();
		}
		return selectedImagePath;
	}

	public class SaveVideoFile extends AsyncTask<String, Void, String> {
		Dialog dialog;

		protected void onPreExecute() {
			dialog = new Dialog(applicationContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			StringBuilder responseBody = null;
			HttpClient client = new DefaultHttpClient();
			String artist1 = "";
			String album1 = "";
			String genre = "";
			String media_image = "";
			FileBody newFile = null;
			String title = "video";
			String strUrl = Constant.serverURL + "method=UplaodVideo";
			if (filePath != null && title != null) {
				artist1 = artist;
				album1 = album;
				media_image = bitmapTostring;
				genre = album1;
				System.out.println("in the block of vcideo////");
				System.out.println("file pat in background" + filePath);
				newFile = new FileBody(new File(filePath));
				if (newFile != null) {
					System.out.println("new file is not null");
				}

			}
			HttpPost post = new HttpPost(strUrl);
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			try {
				System.out.println("id is  " + catagory_id);

				entity.addPart("user_id", new StringBody("umesh"));
				entity.addPart("device_id", new StringBody(deviceId));
				
				 * entity.addPart("artist", new StringBody(artist1));
				 * entity.addPart("album", new StringBody(album1));
				 * entity.addPart("gener", new StringBody(genre));
				 * 
				 * BitmapDrawable bmpDrw =
				 * (BitmapDrawable)applicationContext.getResources
				 * ().getDrawable(R.drawable.ic_launcher); Bitmap bmp =
				 * bmpDrw.getBitmap(); entity.addPart("image", new
				 * StringBody(CommonUtility.encodeTobase64(bmp)));
				 

				entity.addPart("video", new FileBody(new File(filePath)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			post.setEntity(entity);
			BufferedReader bs = null;
			HttpResponse response = null;
			try {
				response = client.execute(post);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("http response is " + response);
			HttpEntity hEntity = response.getEntity();
			try {
				bs = new BufferedReader(new InputStreamReader(
						hEntity.getContent()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.i("response", "Response length - " + hEntity.getContentLength());
			responseBody = new StringBuilder();
			String s = "";
			while (s != null) {
				responseBody.append(s);
				try {
					s = bs.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			System.out.println("response is : " + responseBody);
			try {
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (responseBody != null)
				return responseBody.toString();
			return null;

		}// methods

		protected void onPostExecute(String result) {
			// applicationDialog.cancel();
			if (result != null) {
				if (result.contains("1")) {

					isUpload = false;
					Utility.setSharedPreferenceBoolean(FirstWeek.this,
							"Upload", isUpload);

				} else {
					
					 * CommonUtility.ShowAlertWithMessage(applicationContext,
					 * "", "Failure! Please try it again");
					 
				}
			} else {
				Utility.ShowAlertWithMessage(applicationContext, R.string.Alert,
						R.string.network_connection);
			}
			dialog.dismiss();
		}

	} // class ends here

	public class GetFilms extends AsyncTask<String, String, String> {
		
		String result;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new Dialog(applicationContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String url = "http://projects.linkites.com/nexgen//Api.php?method=GetPrograms&week_id="
					+ week_id;

			result = Utility.findJSONFromUrl(url);
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try{
				
				
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("program");

			//for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(0);
			
				JSONObject object = jsonObject2.getJSONObject("program");
				
				if(result==null){
					Utility.ShowAlertWithMessage(applicationContext, R.string.Alert,
							R.string.network_connection);
					dialog.dismiss();
				}else{
					if(week_id.equals(object.getString("week_id"))){
						parseJson(result);
						dialog.dismiss();
					}else{
						dialog.dismiss();
					}
				//}
			
			}catch (Exception e) {
				// TODO: handle exception
				dialog.dismiss();
			}
			
			
		}
		
		
	}

	*//**
	 * @param result
	 *//*
	public void parseJson(String result) {
		
		
		try {

			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("program");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject object = jsonObject2.getJSONObject("program");
				String current = object.getString("current");
				if (current.equalsIgnoreCase("1")) {
					dialog.dismiss();
					Intent intent = new Intent(FirstWeek.this,
							PlayActivity.class);
					intent.putExtra("programId", object.getString("week_id"));
					startActivity(intent);
					finish();
				} else {
					String startDate = object.getString("start_week");
					String endDate = object.getString("end_week");
					showDialogNew(startDate, endDate);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public void showDialogNew(String sDate, String eDate) {
		mChooseImageDialog = new Dialog(FirstWeek.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.display);
		mChooseImageDialog.setCancelable(true);

		Button btn_facebook = (Button) mChooseImageDialog
				.findViewById(R.id.btn_facebook1);
		TextView textView = (TextView) mChooseImageDialog
				.findViewById(R.id.tv1);
		textView.setText("Festival will Start " + sDate + ". And ends on "
				+ eDate);
		btn_facebook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				mChooseImageDialog.dismiss();

			}
		});

		mChooseImageDialog.show();

	}


	public class GetRewards extends AsyncTask<Void, Void, String> {

		String result;
		
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new Dialog(applicationContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String strUserId=Utility.getSharedPreferences(applicationContext, "UserId");
			String url = Constant.serverURL + "method=GetReward&userid="+strUserId;
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);
			
			if (result!= null) {
				try {
					// list_petName.add("Select Pet");
					JSONObject jsonObject = new JSONObject(result);
					jsonObject = jsonObject.getJSONObject("rewards");
					
					pointReward= jsonObject.getString("reward");
					
					System.out.println("point name is   "+pointReward);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		protected void onPostExecute(String result) {

			if (result == null) {
				Utility.ShowAlertWithMessage(applicationContext,
						R.string.Alert, R.string.network_connection);

			} else {
				
				rewardLayout.setVisibility(View.VISIBLE);
				reward_value_txt.setText(pointReward);
				
				System.out.println("REWARD POINT IS  "+pointReward);
				Toast.makeText(applicationContext, "Reward Point is "+pointReward, Toast.LENGTH_SHORT).show();
			}
		
			dialog.dismiss();
		}
	}
	
}
*/