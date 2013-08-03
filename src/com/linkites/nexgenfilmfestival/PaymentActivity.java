package com.linkites.nexgenfilmfestival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PaymentActivity extends Activity{
	
	public static int PAYMENT_SUCCESS=102;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.payment_layout);
	
	
	
	//EditText editText1 = (EditText) findViewById(R.id.editText1);
		
	//String returnString = editText1.getText().toString();
	//data.putExtra("returnData", returnString);
	
	
	Button btn_continue=(Button)findViewById(R.id.btn_payment_continue);
	
	btn_continue.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Intent paymentIntent=new Intent(PaymentActivity.this,PayPalActivity.class);
		startActivity(paymentIntent);
	
			
			
			
			/*	Intent data = new Intent();
			setResult(PAYMENT_SUCCESS, data);
			finish();*/
			//super.finish();
		}
	});
	/*Intent  termsIntent=new Intent(PaymentActivity.this,TermsAndCondition.class);
	startActivity(termsIntent);*/
	
	}

}
