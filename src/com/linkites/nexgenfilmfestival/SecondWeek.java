package com.linkites.nexgenfilmfestival;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.linkites.nexgenfilmfestival.FirstWeek.GetFilms;
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
import android.widget.TextView;

public class SecondWeek extends Activity{
	
	String week_id;
	
	Dialog mChooseImageDialog;
	Context appContext=this;
	Button btnFifthWeek,btnSixthWeek,btnSeventhWeek,btnEigthWeek,btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.schedule_second);
	findViewByIds();
	
	
	btnBack.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent weekIntent=new Intent(SecondWeek.this,TaboptionsActivity.class);
			startActivity(weekIntent);	
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

	
	private void findViewByIds() {
		btnFifthWeek=(Button)findViewById(R.id.btn_week5);
		btnSixthWeek=(Button)findViewById(R.id.btn_week6);
		btnSeventhWeek=(Button)findViewById(R.id.btn_week7);
		btnEigthWeek=(Button)findViewById(R.id.btn_week8);
		btnBack=(Button)findViewById(R.id.btn_back);
	}
	
	
	
	public class GetFilms extends AsyncTask<String, String, String> {

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

		

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = "http://projects.linkites.com/nexgen//Api.php?method=GetPrograms&week_id="
					+week_id;

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
						parseJson(object.toString());
						mChooseImageDialog.dismiss();
					}else{
						mChooseImageDialog.dismiss();
					}
				//}
			
			}catch (Exception e) {
				// TODO: handle exception
				mChooseImageDialog.dismiss();
			}
			
			
		
			
			/*
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			mChooseImageDialog.dismiss();
			parseJson(result);
		*/}
	}

	/**
	 * @param result
	 */
	public void parseJson(String result) {
		// TODO Auto-generated method stub
		try {
			
			JSONObject jsonObject=new JSONObject(result);
			String current=jsonObject.getString("current");
			if(current.equalsIgnoreCase("1")){
				Intent intent=new Intent(SecondWeek.this,PlayActivity.class);
				intent.putExtra("programId", jsonObject.getString("week_id"));
				startActivity(intent);
			}else{
				String startDate=jsonObject.getString("start_week");
				String endDate=jsonObject.getString("end_week");
				showDialogNew(startDate,endDate);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void showDialogNew(String sDate,String eDate) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Context appContext = this.getApplicationContext();
		mChooseImageDialog = new Dialog(SecondWeek.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.display);
		mChooseImageDialog.setCancelable(true);

		Button btn_facebook = (Button) mChooseImageDialog
				.findViewById(R.id.btn_facebook1);
		TextView textView=(TextView)mChooseImageDialog.findViewById(R.id.tv1);
		textView.setText("This Program will Start "+sDate+". And ends on "+eDate);
		btn_facebook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				
				mChooseImageDialog.dismiss();

			}
		});
		
		
		mChooseImageDialog.show();

	
	}
}
