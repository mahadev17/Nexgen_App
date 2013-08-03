package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 2000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        
        
     // thread for displaying the SplashScreen
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
     			finish();
     			Intent intent = new Intent();
     			//intent.putExtra("RequestFor", Constant.USER_SIGN_IN);
     			intent.setClass(SplashActivity.this,WelCome.class);
     			//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
     			startActivity(intent);
     			
     		}
     	};

     	@Override
     	public boolean onTouchEvent(MotionEvent event) {
     		if (event.getAction() == MotionEvent.ACTION_DOWN) {
     			_active = false;
     		}
     		return true;
     		
     	}
    

}
