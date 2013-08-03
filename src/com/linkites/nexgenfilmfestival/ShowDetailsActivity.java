package com.linkites.nexgenfilmfestival;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.FirstWeek.GetRewards;
import com.linkites.nexgenfilmfestival.utility.Utility;
import com.linkites.nextgen.sharetweeter.TwitterApp;
import com.linkites.nextgen.sharetweeter.TwitterApp.TwDialogListener;
import com.linkites.nextgen.sharetweeter.TwitterSession;

public class ShowDetailsActivity extends Activity {
	Button btnSynopsis, btnMeetFilmMaker, btnRate, btnShare;
	EditText edComment;
	Dialog mChooseImageDialog;
	TextView tvDirector, tv_cinemato, tv_producer, tv_editor, tv_description,
			jackpot_txt;
	TextView film_title, film_time;
	String image_path;
	String FilmId;
	String Value;
	int userRequeste;
	Bitmap bitmap_image;
	ImageView film_image;
	ProgressDialog mProgressDialog;
	String description, screening, cinemato, producer, director, editor, title,
			time;
	Context applicationContext = this;
	public static final String twitter_consumer_key = "hu1zy5pN7bUT3wWxRhoJOA";
	public static final String twitter_secret_key = "qTmwTQAk35BKM1QxgNTAmtG430w1HjJ2W9aFiwMK0Q";
	private com.linkites.nextgen.sharetweeter.TwitterApp mTwitter;
	private SharedPreferences prefs;
	private final Handler mTwitterHandler = new Handler();
	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(), "Tweet sent !", Toast.LENGTH_LONG)
					.show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movie_detail_view);

		findViewByIds();

		FilmId = Utility.getSharedPreferences(ShowDetailsActivity.this,
				"FilmId");
		System.out.println("Film Id is  " + FilmId);

		new GetSynopsis().execute();

		/*
		 * btnSynopsis.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View arg0) { // TODO Auto-generated method stub
		 * Intent synopsisIntent = new Intent(ShowDetailsActivity.this,
		 * ShowSynopsis.class); startActivity(synopsisIntent); } });
		 */

		btnMeetFilmMaker.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent meetIntent = new Intent(ShowDetailsActivity.this,
						MeetFilmMaker.class);
				// meetIntent.putExtra("film_id", "1");
				startActivity(meetIntent);
			}
		});

		btnRate.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent voteIntent = new Intent(ShowDetailsActivity.this,
						VotingBooth.class);
				startActivity(voteIntent);
			}
		});

		btnShare.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				chooseSocial();
			}
		});
	}

	private void findViewByIds() {
		// TODO Auto-generated method stub
		// btnSynopsis = (Button) findViewById(R.id.btn_show_synopsis);
		btnMeetFilmMaker = (Button) findViewById(R.id.btn_meet_filmmaker);
		// edComment = (EditText) findViewById(R.id.edit_comment);
		btnRate = (Button) findViewById(R.id.btn_rate);
		btnShare = (Button) findViewById(R.id.btn_share);
		tvDirector = (TextView) findViewById(R.id.tv_director);

		tv_cinemato = (TextView) findViewById(R.id.tv_cinemato);
		tv_producer = (TextView) findViewById(R.id.tv_producer);
		tv_editor = (TextView) findViewById(R.id.tv_editor);
		tv_description = (TextView) findViewById(R.id.tv_desc);
		jackpot_txt = (TextView) findViewById(R.id.doller_value_txt);
		film_time = (TextView) findViewById(R.id.time_txt);
		film_title = (TextView) findViewById(R.id.title_txt);
		film_image = (ImageView) findViewById(R.id.film_image);
		// tv_screenings = (TextView) findViewById(R.id.text_cinema);

	}

	protected void chooseSocial() {
		// TODO Auto-generated method stub
		Context appContext = this.getApplicationContext();
		mChooseImageDialog = new Dialog(ShowDetailsActivity.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.share);
		mChooseImageDialog.setCancelable(true);

		Button btn_facebook = (Button) mChooseImageDialog
				.findViewById(R.id.btn_facebook);
		Button btn_twitter = (Button) mChooseImageDialog
				.findViewById(R.id.btn_twitter);
		Button btn_email = (Button) mChooseImageDialog
				.findViewById(R.id.btn_email);

		btn_facebook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/**
				 * create Intent to take a picture and return control to the
				 * calling application
				 **/

				Intent fbIntent = new Intent(ShowDetailsActivity.this,
						ShareOnFacebook.class);
				fbIntent.putExtra(
						"facebookMessage",
						"To friends and family My film has been nominated to be screened for this years selection with Nexgen Mobile Film Festival 2013 and would like to envite you to participate in on the screening and voting of my film, to do so please download app and join in on the festival..");

				startActivity(fbIntent);
				finish();
				mChooseImageDialog.dismiss();

			}
		});
		mTwitter = new TwitterApp(ShowDetailsActivity.this,
				twitter_consumer_key, twitter_secret_key);
		mTwitter.setListener(mTwLoginDialogListener);

		btn_twitter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				onTwitterClick();
				mChooseImageDialog.dismiss();

			}
		});

		btn_email.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				// emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new
				// String[]{"mahadev.linkites@gmail.com"});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"NextGen Film Festival");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						"Details About App");
				ShowDetailsActivity.this.startActivity(Intent.createChooser(
						emailIntent, "Send mail..."));
				mChooseImageDialog.dismiss();

				//new PostRewards().execute();
			}
		});
		mChooseImageDialog.show();

	}

	private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {
		public void onComplete(String value) {
			System.out.println("value " + value);
			String username = mTwitter.getUsername();
			if (username != null)
				username = (username.equals("")) ? "No Name" : username;

			if (mTwitter.hasAccessToken()) {
				new TwitterSender().execute();
			} else {
				Toast.makeText(ShowDetailsActivity.this, "Please Login First",
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onError(String value) {
			// TODO Auto-generated method stub

		}
	};

	private class TwitterSender extends AsyncTask<URL, Integer, Long> {
		private String url;

		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(ShowDetailsActivity.this, "",
					"Please Wait...", true);

			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		protected Long doInBackground(URL... urls) {
			long result = 0;

			TwitterSession twitterSession = new TwitterSession(
					ShowDetailsActivity.this);
			AccessToken accessToken = twitterSession.getAccessToken();

			Configuration conf = new ConfigurationBuilder()
					.setOAuthConsumerKey(twitter_consumer_key)
					.setOAuthConsumerSecret(twitter_secret_key)
					.setOAuthAccessToken(accessToken.getToken())
					.setOAuthAccessTokenSecret(accessToken.getTokenSecret())
					.build();

			try {

				result = 1;

				Twitter tt = new TwitterFactory(conf).getInstance();
				String currentDateTimeString = DateFormat.getDateTimeInstance()
						.format(new Date());
				
				twitter4j.Status response = tt
						.updateStatus("My film has been nominated to be screened @ Nexgen Mobile FilmFest 2013. Help me win best film, to do so download app and vote."
								+(int)(System.currentTimeMillis()/10000)); // posting

				System.out.println("status is : " + response);

			} catch (Exception e) {
				Log.e("", "Failed to send status");
				result = 0;
				e.printStackTrace();
			}

			return result;
		}

		protected void onPostExecute(Long result) {

			if (result == 1) {
				//new PostRewards().execute();
			} else {
				Toast.makeText(applicationContext, "Failed...",
						Toast.LENGTH_LONG).show();
			}

			/*
			 * if (mProgressDialog != null) { mProgressDialog.cancel();
			 * //finish(); }
			 */
		}
	}

	private void onTwitterClick() {
		if (mTwitter.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					ShowDetailsActivity.this);

			builder.setMessage("Delete current Twitter connection?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									mTwitter.resetAccessToken();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									// twitterBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.twitter_check_on));
									// mTwitterBtn.setChecked(true);
								}
							});
			final AlertDialog alert = builder.create();

			alert.show();
		} else {
			// tweetButton.setChecked(false);

			mTwitter.authorize();
		}
	}

	public class GetSynopsis extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mChooseImageDialog = new Dialog(applicationContext);
			mChooseImageDialog.setContentView(R.layout.custom_dialog_view);
			mChooseImageDialog.setTitle("NexGen");
			mChooseImageDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String url = Constant.serverURL + "method=GetSynopsis&film_id="
					+ FilmId;
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);

			if (result != null) {

				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject
							.getJSONObject("synopsis");

					producer = jsonObject2.getString("producer");
					director = jsonObject2.getString("director");
					editor = jsonObject2.getString("editor");
					cinemato = jsonObject2.getString("cinematographer");
					screening = jsonObject2.getString("screenings");
					description = jsonObject2.getString("film_desc");
					title = jsonObject2.getString("title");
					time = jsonObject2.getString("running_time");
					image_path = jsonObject2.getString("image");

					bitmap_image = Utility.getBitmap(Constant.imageURL+image_path);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
				Utility.ShowAlertWithMessage(applicationContext,
						R.string.Alert, R.string.network_connection);
			} else {
				tv_cinemato.setText("Cinemato.. :" + cinemato);
				tv_description.setText(description);
				tv_producer.setText("Producer :" + producer);
				tvDirector.setText("Director :" + director);
				tv_editor.setText("Editor :" + editor);
				film_title.setText(title);
				film_time.setText(time);

				film_image.setImageBitmap(bitmap_image);

			}

			mChooseImageDialog.dismiss();

		}

	}

	public class GetJackPot extends AsyncTask<Void, Void, String> {

		String result;

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String url = Constant.serverURL + "method=GetJackpotPrice";
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);

			if (result != null) {
				try {
					// list_petName.add("Select Pet");
					JSONObject jsonObject = new JSONObject(result);
					jsonObject = jsonObject.getJSONObject("jackpot");

					String jackPotName = jsonObject.getString("name");
					Value = jsonObject.getString("value");

					System.out.println("Jacpot name is   " + jackPotName
							+ "  >><<< " + Value);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		protected void onPostExecute(String result) {

			if (result == null) {
				Utility.ShowAlertWithMessage(applicationContext,
						R.string.Alert, R.string.network_connection);

			} else {
				mChooseImageDialog.dismiss();
				jackpot_txt.setText(Value);
			}

		}
	}

	public class PostRewards extends AsyncTask<Void, Void, String> {

		String result;
		Dialog dialog;

		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			String strUserId = Utility.getSharedPreferences(applicationContext,
					"UserId");
			System.out.println("reward user id is  " + strUserId);

			ArrayList<NameValuePair> listNameValuePairs = new ArrayList<NameValuePair>();

			if (userRequeste == 2) {
				listNameValuePairs.add(new BasicNameValuePair("userid",
						strUserId));
			} else if (userRequeste == 1) {
				listNameValuePairs.add(new BasicNameValuePair("user_id",
						strUserId));
			}

			String url = Constant.serverURL + "method=AddShare_Reword";

			System.out.println("URL REWARD is  " + url);
			String result = Utility.postParamsAndfindJSON(url,
					listNameValuePairs);
			listNameValuePairs = null;
			System.out.println("result in petProfile" + result);

			return result;

		}

		protected void onPostExecute(String result) {

			if (result == null) {
				Utility.ShowAlertWithMessage(applicationContext,
						R.string.Alert, R.string.network_connection);
				mProgressDialog = null;
			} else {
				if (result.contains("1")) {
					Utility.ShowAlertWithMessage(applicationContext,
							R.string.Alert, "Rewarded");
				} else {
					Utility.ShowAlertWithMessage(applicationContext,
							R.string.Alert, "Not Rewarded");
				}
			}

		}
	}
}
