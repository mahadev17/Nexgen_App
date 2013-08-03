package com.linkites.nexgenfilmfestival;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

public class HostActivity extends Activity {
	VideoView videoView;
	protected boolean _active = true;
	protected int _splashTime = 2000;
	String userType;

	CharSequence s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Date d = new Date();
		s = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime());
		Bundle bundle = getIntent().getExtras();

		userType = bundle.getString("UserType");
		setContentView(R.layout.host_layout);
			

			Button button = (Button) findViewById(R.id.button11);

			button.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					Intent intent = new Intent();
					// intent.putExtra("RequestFor", Constant.USER_SIGN_IN);
					intent.setClass(HostActivity.this, UserCategory.class);
					// overridePendingTransition(R.anim.slide_in_right,
					// R.anim.slide_out_left);
					startActivity(intent);
					finish();
				}
			});

		
			String uri ;
				VideoView vv = (VideoView) this.findViewById(R.id.videoView111);
				if (userType.equalsIgnoreCase("FilmMaker")) {
				 uri = "android.resource://" + getPackageName() + "/"
						+ R.raw.apphost_filmmaker;
				}else{
				 uri = "android.resource://" + getPackageName() + "/"
						+ R.raw.host2;
				}
				
				vv.setVideoURI(Uri.parse(uri));
				vv.setOnCompletionListener(new OnCompletionListener() {

					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(HostActivity.this, UserCategory.class);

						startActivity(intent);
						finish();
					}
				});
				vv.start();

			}

}
