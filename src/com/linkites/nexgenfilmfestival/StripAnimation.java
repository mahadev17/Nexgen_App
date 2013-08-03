package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class StripAnimation extends Activity{
	ReelGIFView reelGIFView;
	
	protected boolean _active = true;
	protected int _splashTime = 6000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.strip_animation);
		
		 reelGIFView=(ReelGIFView)findViewById(R.id.gifview_reel);
		 
		 

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
	 			//layout_btn.setVisibility(View.VISIBLE);
	 			//gifView.setVisibility(View.INVISIBLE);
	 			
	 			Intent intent=new Intent(StripAnimation.this,TaboptionsActivity.class);
	 			startActivity(intent);
	 		}
	 	};

	}


