package com.linkites.nexgenfilmfestival;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.linkites.Constant.Constant;

public class TabOptionNewActivity extends TabActivity {

	private TabHost mMenuTabHost;
	private Context appContext = this;
	private Intent intent_wizards;
	private Intent intent_browse;
	private Intent intent_setting;
	private Intent intent_testimonial;
	private Intent intent_calender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tab_view);

		mMenuTabHost = getTabHost();

		View view = new View(appContext);
		view.setBackgroundResource(R.drawable.divider);

		intent_wizards = new Intent(appContext, PlayActivity.class);
		intent_browse = new Intent(appContext, VideoFullScreenActivity.class);
		//intent_setting = new Intent(appContext, PlayActivity.class);
		mMenuTabHost.getTabWidget().setDividerDrawable(R.drawable.divider);

		addTab(1, intent_wizards, R.drawable.browse_tab);
		addTab(2, intent_browse, R.drawable.play_tab);
		//addTab(3, intent_setting, R.drawable.settings_tab);
		//addTab(4, intent_testimonial, R.drawable.about_tab);
		mMenuTabHost.setCurrentTab(0);

		
		
	mMenuTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				if(arg0.equalsIgnoreCase("tabid2")){
					
					Intent intent=new Intent(TabOptionNewActivity.this,VideoFullScreenActivity.class);
					intent.putExtra("VideoURL", Constant.imageURL + PlayActivity.videoPath);
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
}