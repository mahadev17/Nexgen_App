package com.linkites.nexgenfilmfestival;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class TaboptionsActivity extends TabActivity {


	private TabHost mMenuTabHost;
	private Context appContext = this;
	private Intent intent_wizards;
	private Intent intent_survey;
	private Intent intent_testimonial;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setContentView(R.layout.tab_view);

		appContext=this;
		

		
		
		mMenuTabHost = getTabHost();

		View view = new View(appContext);
		view.setBackgroundResource(R.drawable.divider);

		intent_wizards = new Intent(appContext, FirstWeek.class);
		//new Intent(appContext, PlayActivity.class);
		intent_survey = new Intent(appContext, TweetActivity.class);

		intent_testimonial = new Intent(appContext, AboutUs.class);

		mMenuTabHost.getTabWidget().setDividerDrawable(R.drawable.divider);

		addTab(1, intent_wizards, R.drawable.schedule_tab);
		//addTab(2, intent_forum_finder, R.drawable.screening_room_tab);
		addTab(3, intent_survey, R.drawable.news_feed_tab);
		addTab(4, intent_testimonial, R.drawable.about_tab);
		mMenuTabHost.setCurrentTab(0);
		
		
		mMenuTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				if(arg0.equalsIgnoreCase("tabid2")){
					
					Intent intent=new Intent(TaboptionsActivity.this,PlayActivity.class);
					startActivity(intent);
				}
				
			}
		});

	}

	private void addTab(int labelId, Intent activityIntent, int resId) {

		TabHost.TabSpec spec = mMenuTabHost.newTabSpec("tabid" + labelId);
		View indicator11 = LayoutInflater.from(this)
				.inflate(R.layout.tab, null);
		ImageView img = (ImageView) indicator11.findViewById(R.id.tab_icon);
		img.setImageResource(resId);
		spec.setContent(activityIntent);
		spec.setIndicator(indicator11);
		mMenuTabHost.addTab(spec);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	       
	    	Toast.makeText(appContext," text",1111).show();
	    	
	    	/* YourParentActivity parentActivity;
	        parentActivity = (YourParentActivity) this.getParent();
	        parentActivity.switchTab(indexTabToSwitchTo);*/
	        return true;
	    }
	    return super.onKeyDown(keyCode, event); 
	}
}