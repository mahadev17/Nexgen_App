package com.linkites.nexgenfilmfestival;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFilmMaker extends Activity{
	Button btn_next;
	EditText distributor,number,address,screening,telephone,email,title,originalTitle,genreOfFilm,runningTime,age,aboutMe,name,description,password;
	String distributorName,strScreening,FDescription,Fname,AboutMe,Age,distributor_num,dis_address,dis_telephone,dis_email,dis_password,filmTitle,filmOriginalTitle,strGenreOfFilm,strRunningTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		   this.requestWindowFeature(Window.FEATURE_NO_TITLE);

					getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setContentView(R.layout.register_film_maker);
		findViewByIds();
		 
		
		
		
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//email.setText("umesh432545243525@gmail.com");
				
				distributorName=distributor.getText().toString().trim();
				distributor_num=number.getText().toString().trim();
				dis_address=address.getText().toString().trim();
				dis_telephone=telephone.getText().toString().trim();
					dis_email=email.getText().toString().trim();
					dis_password=password.getText().toString().trim();
					filmTitle=title.getText().toString().trim();
					filmOriginalTitle=originalTitle.getText().toString().trim();
					strGenreOfFilm=genreOfFilm.getText().toString().trim();
					strRunningTime=runningTime.getText().toString().trim();
				Fname=name.getText().toString().trim();
				FDescription=description.getText().toString().trim();
				Age=age.getText().toString().trim();
				AboutMe=aboutMe.getText().toString().trim();
					strScreening=screening.getText().toString().trim();
					
					
					
					Intent registrationIntent=new Intent(RegisterFilmMaker.this,NextRegistrationActivity.class);
					
					registrationIntent.putExtra("DistributorNumber", distributor_num);
					registrationIntent.putExtra("DistributorName", distributorName);
					registrationIntent.putExtra("DistributorTelephone",dis_telephone);
					registrationIntent.putExtra("DistributorEmail",dis_email);
					registrationIntent.putExtra("DistributorPassword",dis_password);
					registrationIntent.putExtra("FilmTitle",filmTitle);
					registrationIntent.putExtra("FilmOriginalTitle",filmOriginalTitle);
					registrationIntent.putExtra("GenreOfFilm", strGenreOfFilm);
					registrationIntent.putExtra("RunningTime", strRunningTime);
					registrationIntent.putExtra("Fname", Fname);
					registrationIntent.putExtra("DistributorAddress", dis_address);
					registrationIntent.putExtra("FDescription", FDescription);
					registrationIntent.putExtra("Age", Age);
					registrationIntent.putExtra("AboutMe", AboutMe);
					registrationIntent.putExtra("strScreening", strScreening);
					
					
					
					
					if(checkValiationForData(distributor_num)&&checkValiationForData(distributorName)&&checkValiationForData(dis_telephone)&&checkValiationForData(dis_address)&&checkValiationForData(dis_password)&&checkValiationForData(filmOriginalTitle)&&checkValiationForData(filmTitle)&&checkValiationForData(strRunningTime)&&checkValiationForData(strGenreOfFilm)){
						if(isEmailValid(dis_email)){
							startActivity(registrationIntent);
							//finish();
						}else{
							Toast.makeText(getBaseContext(), "Please Enter Correct Email Address",Toast.LENGTH_SHORT).show();
						}
						
					}else{
						Toast.makeText(getBaseContext(), "Please Enter All Details",Toast.LENGTH_SHORT).show();
					}
					
					/*if(isEmailValid(dis_email)){
						startActivity(registrationIntent);
						finish();
					}else{
						Toast.makeText(getBaseContext(), "Please Enter Correct Email Address",Toast.LENGTH_SHORT).show();
					}*/
					//startActivity(registrationIntent);
				
				//intent.putStringArrayListExtra(name, value)tExtra(name, value);
				
				
				
				
				
				
				
			}
		});
	}
	private void findViewByIds() {
		btn_next=(Button)findViewById(R.id.btn_next_reg);
		distributor=(EditText)findViewById(R.id.edit_distributor);
		number=(EditText)findViewById(R.id.edit_number);
		address=(EditText)findViewById(R.id.edit_address);
		telephone=(EditText)findViewById(R.id.edit_telephone);
		email=(EditText)findViewById(R.id.edit_email_reg);
		title=(EditText)findViewById(R.id.edit_film_title);
		originalTitle=(EditText)findViewById(R.id.edit_film_original);
		genreOfFilm=(EditText)findViewById(R.id.edit_genre);
		runningTime=(EditText)findViewById(R.id.edit_running);
		password=(EditText)findViewById(R.id.edit_password_reg);
		screening=(EditText)findViewById(R.id.edit_screening);
		description=(EditText)findViewById(R.id.edit_description);
		age=(EditText)findViewById(R.id.edit_age);
		aboutMe=(EditText)findViewById(R.id.edit_about_me);
		name=(EditText)findViewById(R.id.edit_name);
	}
	
	
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}

	
	public static boolean checkValiationForData(String txt) {
		return (txt.trim().length() == 0) ? false : true;
	}

}





