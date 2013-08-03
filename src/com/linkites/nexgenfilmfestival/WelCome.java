package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class WelCome extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 6000;
	GIFView gifView;
	LinearLayout layout_btn;
	Button btnFilmMaker, btnViewer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);

		btnFilmMaker = (Button) findViewById(R.id.btn_filmMaker1);
		btnViewer = (Button) findViewById(R.id.btn_viewer);
		//gifView = (GIFView) findViewById(R.id.gifview1);
		layout_btn = (LinearLayout) findViewById(R.id.layout1234);

		btnFilmMaker.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelCome.this, HostActivity.class);
				intent.putExtra("UserType", "FilmMaker");
				startActivity(intent);
			}
		});

		btnViewer.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelCome.this, HostActivity.class);
				intent.putExtra("UserType", "FestivalViewer");
				startActivity(intent);
			}
		});

	/*	Thread splashTread = new Thread() {
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
			gifView.setVisibility(View.INVISIBLE);
		}
	};*/
	}
}