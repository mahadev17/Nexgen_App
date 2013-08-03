package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TermsAndCondition extends Activity {
	public static int PAYMENT_SUCCESS = 102;
	public static int RESULT_TERM = 103;

	// private ArrayList<NameValuePair> listNameValuePairs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.terms_and_condition);
		// listNameValuePairs = new ArrayList<NameValuePair>();
		Button button = (Button) findViewById(R.id.btn_countinue_term);

		// Bundle bundle=getIntent().getExtras();

		// listNameValuePairs=bundle.getParcelableArrayList("Information");

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(TermsAndCondition.this,
						PayPalActivity.class);

				startActivityForResult(intent, PAYMENT_SUCCESS);*/
				
				setResult(RESULT_TERM);
				finish();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("Correct", "Correct");

		// startActivityForResult(intent, RESULT_TERM);
		setResult(RESULT_TERM);
		finish();
	}
}
