package com.linkites.nexgenfilmfestival;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.CommonUtility;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class NextRegistrationActivity extends Activity {

	String categoryid_Images, imageFilePath, encodedSelectedImage;
	Button btn_Use_Cam, btn_Use_Lib;
	Dialog mChooseImageDialog;
	private static final int MEMORYCARD = 3;
	private static final int CAMERA = 4;
	Context appContext;
	ImageView imageView;
	ProgressDialog appProgressDialog;
	// private ProgressDialog dialog;
	Button btn_countinue, btnImage;
	// private ProgressDialog applicationDialog;
	String distributorName, distributor_num, dis_address, dis_telephone,
			dis_email, dis_password, filmTitle, filmOriginalTitle,
			strGenreOfFilm, strRunningTime, strScreening, FDescription, Fname,
			AboutMe, Age;
	String strDirector, strCinematographer, strEditor, strProducer,
			strStarCast1, strStarCast2, strStarCast3, strStarCast4,
			strStarCast5, strStarCast6, strScreenWriter, strFilmCountry,
			strLanguages;
	EditText edDirector, edProducer, edEditor, edCinematographer,
			edScreenWriter, edStarCast1, edStarCast2, edStarCast3, edStarCast4,
			edStarCast5, edStarCast6, edFilmCountry, edFilmLanguges;
	Bundle distributorBundle;
	public static int RESULT_TERM = 103;
	private ArrayList<NameValuePair> listNameValuePairs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.registration_next);
		findViewByIds();
		appContext = this;
		listNameValuePairs = new ArrayList<NameValuePair>();
		distributorBundle = getIntent().getExtras();

		// dialog = new ProgressDialog(NextRegistrationActivity.this);

		btn_countinue.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				distributorName = distributorBundle
						.getString("DistributorName").toString().trim();
				distributor_num = distributorBundle
						.getString("DistributorNumber").toString().trim();
				dis_address = distributorBundle.getString("DistributorAddress")
						.toString().trim();
				dis_telephone = distributorBundle
						.getString("DistributorTelephone").toString().trim();
				dis_email = distributorBundle.getString("DistributorEmail")
						.toString().trim();
				dis_password = distributorBundle
						.getString("DistributorPassword").toString().trim();
				filmTitle = distributorBundle.getString("FilmTitle").toString()
						.trim();
				filmOriginalTitle = distributorBundle
						.getString("FilmOriginalTitle").toString().trim();
				strGenreOfFilm = distributorBundle.getString("GenreOfFilm")
						.toString().trim();
				strRunningTime = distributorBundle.getString("RunningTime")
						.toString().trim();

				strScreening = distributorBundle.getString("strScreening");
				FDescription = distributorBundle.getString("FDescription");
				Fname = distributorBundle.getString("Fname");
				AboutMe = distributorBundle.getString("AboutMe");
				Age = distributorBundle.getString("Age");

				strCinematographer = edCinematographer.getText().toString()
						.trim();
				strEditor = edEditor.getText().toString().trim();
				strDirector = edDirector.getText().toString().trim();
				strProducer = edProducer.getText().toString().trim();
				strScreenWriter = edScreenWriter.getText().toString().trim();
				strStarCast1 = edStarCast1.getText().toString().trim();
				strStarCast2 = edStarCast2.getText().toString().trim();
				strStarCast3 = edStarCast3.getText().toString().trim();
				strStarCast4 = edStarCast4.getText().toString().trim();
				strStarCast5 = edStarCast5.getText().toString().trim();
				strStarCast6 = edStarCast6.getText().toString().trim();
				strFilmCountry = edFilmCountry.getText().toString().trim();
				strLanguages = edFilmLanguges.getText().toString().trim();

				TelephonyManager telephone_manager = (TelephonyManager) NextRegistrationActivity.this
						.getSystemService(Context.TELEPHONY_SERVICE);
				String device_id = telephone_manager.getDeviceId();

				listNameValuePairs.add(new BasicNameValuePair("distirbutor",
						distributorName));
				listNameValuePairs.add(new BasicNameValuePair("number",
						distributor_num));
				listNameValuePairs.add(new BasicNameValuePair("address",
						dis_address));
				listNameValuePairs.add(new BasicNameValuePair("telephone",
						dis_telephone));
				listNameValuePairs.add(new BasicNameValuePair("token_id", "0"));
				listNameValuePairs.add(new BasicNameValuePair("device_id",
						device_id));
				listNameValuePairs.add(new BasicNameValuePair("email",
						dis_email));

				listNameValuePairs.add(new BasicNameValuePair("password",
						dis_password));
				listNameValuePairs.add(new BasicNameValuePair("title",
						filmTitle));
				listNameValuePairs.add(new BasicNameValuePair("org_title",
						filmOriginalTitle));
				listNameValuePairs.add(new BasicNameValuePair("genre_film",
						strGenreOfFilm));
				listNameValuePairs.add(new BasicNameValuePair("runing_time",
						strRunningTime));
				listNameValuePairs.add(new BasicNameValuePair("director",
						strDirector));
				listNameValuePairs.add(new BasicNameValuePair("scountry",
						strFilmCountry));
				listNameValuePairs.add(new BasicNameValuePair("language",
						strLanguages));
				listNameValuePairs.add(new BasicNameValuePair("scast1",
						strStarCast1));
				listNameValuePairs.add(new BasicNameValuePair("scast2",
						strStarCast2));
				listNameValuePairs.add(new BasicNameValuePair("scast3",
						strStarCast3));
				listNameValuePairs.add(new BasicNameValuePair("scast4",
						strStarCast4));
				listNameValuePairs.add(new BasicNameValuePair("scast5",
						strStarCast5));
				listNameValuePairs.add(new BasicNameValuePair("scast6",
						strStarCast6));
				listNameValuePairs.add(new BasicNameValuePair("produser",
						strProducer));
				listNameValuePairs.add(new BasicNameValuePair("screen_writer",
						strScreenWriter));
				listNameValuePairs.add(new BasicNameValuePair(
						"cinematographer", strCinematographer));
				listNameValuePairs.add(new BasicNameValuePair("editor",
						strEditor));

				listNameValuePairs.add(new BasicNameValuePair("screeing",
						strScreening));

				listNameValuePairs.add(new BasicNameValuePair("film_desc",
						FDescription));

				listNameValuePairs.add(new BasicNameValuePair("name", Fname));

				listNameValuePairs.add(new BasicNameValuePair("age", Age));

				listNameValuePairs.add(new BasicNameValuePair("about_me",
						AboutMe));

				Intent intent = new Intent(NextRegistrationActivity.this,
						TermsAndCondition.class);
				// intent.putExtra("Information", listNameValuePairs);
				// intent.putStringArrayListExtra("Information",
				// listNameValuePairs);
				// intent.putExtra("Information", listNameValuePairs);

				if (checkValiationForData(strDirector)
						&& checkValiationForData(strFilmCountry)
						&& checkValiationForData(strProducer)
						&& checkValiationForData(strLanguages)
						&& checkValiationForData(strScreenWriter)
						&& checkValiationForData(strStarCast1)
						&& checkValiationForData(strStarCast2)
						&& checkValiationForData(strStarCast3)
						&& checkValiationForData(strStarCast4)
						&& checkValiationForData(strStarCast5)) {
					startActivityForResult(intent, RESULT_TERM);
				} else {
					Toast.makeText(getBaseContext(),
							"Please Enter All Details", Toast.LENGTH_SHORT)
							.show();
				}

				// startActivityForResult(intent, RESULT_TERM);
				// new PostDatatoServer().execute();
			}
		});

		btnImage.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				ChooseImageDialog();
			}
		});

	}

	protected void ChooseImageDialog() {

		mChooseImageDialog = new Dialog(NextRegistrationActivity.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.choose_photo);
		mChooseImageDialog.setCancelable(true);

		btn_Use_Cam = (Button) mChooseImageDialog
				.findViewById(R.id.btn_restart);
		btn_Use_Lib = (Button) mChooseImageDialog
				.findViewById(R.id.btn_no_restart);

		btn_Use_Cam.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/**
				 * create Intent to take a picture and return control to the
				 * calling application
				 **/

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				startActivityForResult(intent, 1338);
				mChooseImageDialog.dismiss();

			}
		});

		btn_Use_Lib.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				try {
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"),
							1337);

				} catch (ActivityNotFoundException ane) {
					ane.printStackTrace();
				}
				mChooseImageDialog.dismiss();
			}
		});
		mChooseImageDialog.show();
	}

	private void findViewByIds() {
		btn_countinue = (Button) findViewById(R.id.btn_continue);
		edProducer = (EditText) findViewById(R.id.edit_producer);
		edDirector = (EditText) findViewById(R.id.edit_director);
		edScreenWriter = (EditText) findViewById(R.id.edit_screen_writer);
		edStarCast1 = (EditText) findViewById(R.id.edit_star1);
		edStarCast2 = (EditText) findViewById(R.id.edit_star2);
		edStarCast3 = (EditText) findViewById(R.id.edit_star3);
		edStarCast4 = (EditText) findViewById(R.id.edit_star4);
		edStarCast5 = (EditText) findViewById(R.id.edit_star5);
		edFilmCountry = (EditText) findViewById(R.id.edit_film_country);
		edFilmLanguges = (EditText) findViewById(R.id.edit_languages);
		edCinematographer = (EditText) findViewById(R.id.edit_cinemato);
		edEditor = (EditText) findViewById(R.id.edit_editor);
		edStarCast6 = (EditText) findViewById(R.id.edit_star6);
		btnImage = (Button) findViewById(R.id.btn_image);
		imageView = (ImageView) findViewById(R.id.imageView123);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// public static int RESULT_TERM=103;
		if (resultCode == RESULT_TERM) {
			new PostDatatoServer().execute();

			Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
			finish();
		} else if (requestCode == 1337) {
			Uri _uri = data.getData();
			Bitmap bmp;
			if (_uri != null) {
				Cursor cursor = getContentResolver()
						.query(_uri,
								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
								null, null, null);
				cursor.moveToFirst();
				InputStream stream = null;
				try {
					stream = getContentResolver().openInputStream(_uri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bmp = BitmapFactory.decodeStream(stream);
				imageView.setImageBitmap(bmp);
				imageFilePath = cursor.getString(0);
				File photo = new File(imageFilePath);
				if (photo.exists()) {
					// bmp = BitmapFactory.decodeFile(photo
					// .getAbsolutePath());
					if (bmp != null) {
						encodedSelectedImage = CommonUtility
								.encodeTobase64(bmp);
						System.out.println("encoded image is : "
								+ encodedSelectedImage);

					}

					listNameValuePairs.add(new BasicNameValuePair("image",
							encodedSelectedImage));
				}

			}
		} else if (requestCode == 1338) {

			if (data != null) {
				Bitmap bmp = (Bitmap) data.getExtras().get("data");
				Uri _uri = data.getData();

				bmp = Bitmap.createScaledBitmap(bmp, 80, 80, false);

				if (_uri != null) {
					Cursor cursor = getContentResolver()
							.query(_uri,
									new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
									null, null, null);
					cursor.moveToFirst();
					try {
						InputStream stream = getContentResolver()
								.openInputStream(_uri);
						bmp = BitmapFactory.decodeStream(stream);
						imageView.setImageBitmap(bmp);
					} catch (Exception e) {
						// TODO: handle exception
					}

					imageFilePath = cursor.getString(0);
					File photo = new File(imageFilePath);
					if (photo.exists()) {
						// bmp = BitmapFactory.decodeFile(photo
						// .getAbsolutePath());
						if (bmp != null) {
							encodedSelectedImage = CommonUtility
									.encodeTobase64(bmp);

							System.out.println("encoded image is : "
									+ encodedSelectedImage);

							/*
							 * File file = new File(imageFilePath); boolean
							 * deleted = file.delete();
							 */
						}

						listNameValuePairs.add(new BasicNameValuePair("image",
								encodedSelectedImage));
					}
				}
			}

		}

	}

	public class PostDatatoServer extends AsyncTask<Void, Void, String> {
		String result;
	//	Dialog mShowDialog;
		 
		protected void onPreExecute() {
			super.onPreExecute();
			/*
			 * appProgressDialog = new ProgressDialog(getParent());
			 * appProgressDialog.setTitle("Please Wait");
			 * appProgressDialog.setMessage("Saving Your Profile..");
			 * appProgressDialog.setCancelable(true); appProgressDialog.show();
			 */
			/* mShowDialog = showDialogWithText("Loading...");
			 mShowDialog.setCancelable(false);
			 mShowDialog.show();*/

		}

		@Override
		protected String doInBackground(Void... params) {

			String url = Constant.serverURL + "method=SignUp";
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			listNameValuePairs.clear();
		Log.d("Insert","Insert"+ result);
			return result;
		}

		protected void onPostExecute(String result) {
			//mShowDialog.dismiss();
			if (result.contains("1")) {
				edProducer.setText("");
				edDirector.setText("");
				edScreenWriter.setText("");
				edStarCast1.setText("");
				edStarCast2.setText("");
				edStarCast3.setText("");
				edStarCast4.setText("");
				edStarCast5.setText("");
				edFilmCountry.setText("");
				edFilmLanguges.setText("");
				Toast.makeText(getBaseContext(),
						"Congrats!!! Sign Up Successfull", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(NextRegistrationActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();

			} else if (result.contains("0")) {
				Toast.makeText(getBaseContext(),
						"Oops!!! Something went Wrong", Toast.LENGTH_SHORT)
						.show();
			} else if (result.contains("-1")) {
				Toast.makeText(getBaseContext(), "Email Id Already Exists",
						Toast.LENGTH_SHORT).show();
			}

		}

		public void popActivity() {
			listNameValuePairs = null;
			finish();
		}

	}

	public static boolean checkValiationForData(String txt) {
		return (txt.trim().length() == 0) ? false : true;
	}

	private Dialog showDialogWithText(String message) {

		Dialog mDialog = new Dialog(getParent());
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(R.layout.custom_dialog_view);
		TextView mtextview = (TextView) mDialog
				.findViewById(R.id.custom_dialog_textview);
		mtextview.setText(message);
		return mDialog;
	}
}