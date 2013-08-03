package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AboutUs extends Activity {

	Context appContext = this;
	public static final int ANSWERS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.about_us);

		Button button = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		Button contact_btn = (Button) findViewById(R.id.contact_btn);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutUs.this, NextAboutUs.class);
				startActivity(intent);
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutUs.this, QuestionAnswer.class);
				startActivity(intent);
			}
		});

		button3.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutUs.this,
						GeneralRuleActivity.class);
				startActivity(intent);
			}
		});

		contact_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "questions@nexgenmobilefilmfest.com" });
				i.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
				i.putExtra(android.content.Intent.EXTRA_TEXT,
						"Please Send Mail...."); // posting the message
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(appContext,
							"There are no email clients installed.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			final Dialog mChooseImageDialog;

			mChooseImageDialog = new Dialog(AboutUs.this);
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

					Intent intent = new Intent(AboutUs.this, UserCategory.class);
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

}
