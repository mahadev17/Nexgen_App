package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class VideoFullScreenActivity extends Activity {
	
	WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.video_fullscreen);

		
	/*	 web = (WebView) findViewById(R.id.webView1);
	        web.setWebChromeClient(new myWebClient());
	        web.getSettings().setJavaScriptEnabled(true);
*/
		
	        final WebView browser = (WebView)findViewById(R.id.webView1);  
	        /* JavaScript must be enabled if you want it to work, obviously */   
	        browser.getSettings().setJavaScriptEnabled(true);  
	          
	        final Context myApp = this;  
	          
	        /* WebChromeClient must be set BEFORE calling loadUrl! */  
	        browser.setWebChromeClient(new WebChromeClient() {  
	            @Override  
	            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)   
	            {  
	                new AlertDialog.Builder(myApp)  
	                    .setTitle("javaScript dialog")  
	                    .setMessage(message)  
	                    .setPositiveButton(android.R.string.ok,  
	                            new AlertDialog.OnClickListener()   
	                            {  
	                                public void onClick(DialogInterface dialog, int which)   
	                                {  
	                                    result.confirm();  
	                                }  
	                            })  
	                    .setCancelable(false)  
	                    .create()  
	                    .show();  
	                  
	                return true;  
	            };  
	        });  
	          
	        /* load a web page which uses the alert() function */  
	        browser.loadUrl("http://projects.linkites.com/nexgen/video.php");  
		
		
		
	}
}
		
		
		// VideoView videoView=(VideoView)findViewById(R.id.myvideoview);

		//CustomVideo videoView = (CustomVideo) findViewById(R.id.custom_videoview);
		/*Bundle bundle = getIntent().getExtras();
		String VideoURL = bundle.getString("VideoURL");
		int duration = bundle.getInt("duration");
		Uri uri = Uri.parse(VideoURL);
		
		
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://projects.linkites.com/nexgen/video.php");
		
		//videoView.setVideoURI(uri);

		videoView.setPlayPauseListener(new CustomVideo.PlayPauseListener() {

			public void onPlay() {
				System.out.println("Play!");
			}

			public void onPause() {
				System.out.println("Pause!");
			}
		});

		videoView.setMediaController(new MediaController(this));
		videoView.seekTo(duration);
		videoView.start();
*/
	


