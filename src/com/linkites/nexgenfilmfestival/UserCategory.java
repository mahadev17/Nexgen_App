package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class UserCategory extends Activity   {

	Button btnNewFilmMaker, btnFestivalViewer, btnFilmMakerLogin,btnViewerLogin;
	protected boolean _active = true;
	protected int _splashTime = 6000;
	//GIFView gifView;
	LinearLayout layout_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.user_category);
		findViewByIds();
		//gifView=(GIFView)findViewById(R.id.gifview1);
		btnFestivalViewer.setOnClickListener(mBtnClickListner);
		btnFilmMakerLogin.setOnClickListener(mBtnClickListner);
		btnNewFilmMaker.setOnClickListener(mBtnClickListner);
		btnViewerLogin.setOnClickListener(mBtnClickListner);
		
		
		
		Thread splashTread = new Thread() {
 			@Override
 			public void run() {
 				try {
 					int waited = 0;
 					while (_active && (waited < _splashTime)) {
 						sleep(100);
 						if (_active) {
 							waited += 100;
 						}
 					}
 				} catch (InterruptedException e) {
 					// do nothing
 				} finally {

 				}
 				runOnUiThread(endSplashThread);
 			}
 		};
 		splashTread.start();
 	}

 	private Runnable endSplashThread = new Runnable() {
 		public void run() {
 			layout_btn.setVisibility(View.VISIBLE);
 			//gifView.setVisibility(View.INVISIBLE);
 		}
 	};


	private void findViewByIds() {
		// TODO Auto-generated method stub
		layout_btn=(LinearLayout)findViewById(R.id.layout_btns);
		btnFestivalViewer = (Button) findViewById(R.id.btn_viewer);
		btnFilmMakerLogin = (Button) findViewById(R.id.btn_login_film);
		btnNewFilmMaker = (Button) findViewById(R.id.btn_filmmaker);
		btnViewerLogin=(Button)findViewById(R.id.btn_viewer_login);
		//gifView=(GIFView)findViewById(R.id.gifview1);
	}

	View.OnClickListener mBtnClickListner = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_filmmaker:
				Intent newFilmIntent = new Intent(UserCategory.this,RegisterFilmMaker.class);
				startActivity(newFilmIntent);
				finish();
				break;
				
			case R.id.btn_login_film:
				Intent loginIntent = new Intent(UserCategory.this,LoginActivity.class);
				startActivity(loginIntent);
				finish();
				break;
				
			case R.id.btn_viewer:
				Intent viwerIntent = new Intent(UserCategory.this,ViewerSignUp.class);
				startActivity(viwerIntent);
				finish();
				break;
				
			case R.id.btn_viewer_login:
				Intent viewerLoginIntent=new Intent(UserCategory.this,FestivalViewer.class);
				startActivity(viewerLoginIntent);
				finish();
			}
		}
	};

}
