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
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;
import com.linkites.nextgen.sharetweeter.TwitterApp;
import com.linkites.nextgen.sharetweeter.TwitterApp.TwDialogListener;
import com.linkites.nextgen.sharetweeter.TwitterSession;

public class VotingBooth extends Activity {

	String film_id;
	String image_path;
	TextView jackpot_txt;
	private ArrayList<NameValuePair> listNameValuePairs;
	RatingBar rat_story, rat_cinematography, rat_director, rat_actor,
			rat_animation, rat_actress;
	Context mContext;
	String Value;
	int userRequeste;
	ImageView film_image;
	Bitmap image_bitmap;
	String cinematographer,strEditor,strStory,strActor,strActress;
	ArrayList<String> listTitle, listTitleId;
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

	CommonAdapter adapter;
	Dialog mChooseImageDialog, mChooseImageDialog1;
	boolean actor1, actress1, cinemato1, story1, director1;
	Dialog dialog;

	String name, id;
	ListView listView;
	Button btnVote, btnShare;
	TextView tv_story, tv_cinemato, tv_actor, tv_actress, tv_director,
			tv_animation, tvFilmTitle, tvActorId, tvActressId, tvStoryId,
			tvCinematoId, tvDirectorId, tvAnimation;
	float story_rating, cinemato_rating, director_rating, actor_rating,
			actress_rating;
	String result, result1;
	ArrayList<String> story, actor, actress, cinematography, directorStrArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.voting_booth);

		findViewByIds();

		mChooseImageDialog1 = new Dialog(VotingBooth.this);
		mChooseImageDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog1.setContentView(R.layout.vote_instruction);
		mChooseImageDialog1.setCancelable(false);
		mChooseImageDialog1.show();
		
		//userRequeste=Utility.getSharedPreferences1(mContext, "requestBy");

		Button button = (Button) mChooseImageDialog1
				.findViewById(R.id.buttonVote);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				mChooseImageDialog1.dismiss();
			}
		});
		story = new ArrayList<String>();
		actor = new ArrayList<String>();
		actress = new ArrayList<String>();
		cinematography = new ArrayList<String>();
		directorStrArray = new ArrayList<String>();
		listTitle = new ArrayList<String>();
		// story = new ArrayList<String>();
		listTitleId = new ArrayList<String>();
		mContext = this;
		listNameValuePairs = new ArrayList<NameValuePair>();

		new GetJackPot().execute();
		new GetMovies().execute();
		tv_actor.setOnClickListener(mBtnClickListner);
		// tv_director.setOnClickListener(mBtnClickListner);
		// tv_story.setOnClickListener(mBtnClickListner);
		// tv_cinemato.setOnClickListener(mBtnClickListner);
		tv_actress.setOnClickListener(mBtnClickListner);
		btnVote.setOnClickListener(mBtnClickListner);
		btnShare.setOnClickListener(mBtnClickListner);
		tvFilmTitle.setOnClickListener(mBtnClickListner);

	}

	View.OnClickListener mBtnClickListner = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.text_actor:
				actor1 = true;
				actress1 = false;
				story1 = false;
				cinemato1 = false;
				director1 = false;

				showListDialog(actor);

				break;

			case R.id.text_actress:
				actor1 = false;
				actress1 = true;
				story1 = false;
				cinemato1 = false;
				director1 = false;
				showListDialog(actress);

				break;

			case R.id.text_cinemato:
				actor1 = false;
				actress1 = false;
				story1 = false;
				cinemato1 = true;
				director1 = false;
				showListDialog(cinematography);
				break;

			case R.id.text_story:
				actor1 = false;
				actress1 = false;
				story1 = true;
				cinemato1 = false;
				director1 = false;
				showListDialog(story);
				break;

			case R.id.text_director:
				actor1 = false;
				actress1 = false;
				story1 = false;
				cinemato1 = false;
				director1 = true;
				showListDialog(directorStrArray);
				break;

			case R.id.btn_submit_rating:
				submitRating();
				break;
			case R.id.btn_share2:
				chooseSocial();
				break;
			case R.id.text_film:
				showListDialogTitle(listTitle);
				break;
			}
		}

	};

	private void showListDialogTitle(ArrayList<String> list) {
		mChooseImageDialog = new Dialog(VotingBooth.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.list_dialog);
		mChooseImageDialog.setCancelable(true);

		listView = (ListView) mChooseImageDialog.findViewById(R.id.listView1);

		mChooseImageDialog.show();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				listTitle);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				tvFilmTitle.setText(listTitle.get(arg2));
				film_id = listTitleId.get(arg2);
				actor.clear();
				actress.clear();

				new GetRatings().execute();
				mChooseImageDialog.dismiss();
			}
		});

	}

	private void showListDialog(ArrayList<String> list) {
		mChooseImageDialog = new Dialog(VotingBooth.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.list_dialog);
		mChooseImageDialog.setCancelable(true);

		listView = (ListView) mChooseImageDialog.findViewById(R.id.listView1);
		adapter = new CommonAdapter(list, this);
		listView.setAdapter(adapter);

		mChooseImageDialog.show();

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {

				if (actor1) {
					try {
						JSONObject jsonObject = new JSONObject(actor.get(pos));
						name = jsonObject.getString("name");
						id = jsonObject.getString("id");
						tv_actor.setText(" "+name);
						tvActorId.setText(id);
					} catch (Exception e) {
					}
					Toast.makeText(getApplicationContext(), name + "   " + id,
							Toast.LENGTH_SHORT).show();
				} else if (story1) {
					try {
						JSONObject jsonObject = new JSONObject(story.get(pos));
						name = jsonObject.getString("name");
						id = jsonObject.getString("id");
						tv_story.setText(name);
						tvStoryId.setText(id);
					} catch (Exception e) {
					}
					Toast.makeText(getApplicationContext(), name + "   " + id,
							Toast.LENGTH_SHORT).show();
				} else if (cinemato1) {
					try {
						JSONObject jsonObject = new JSONObject(cinematography
								.get(pos));
						name = jsonObject.getString("name");
						id = jsonObject.getString("id");
						tv_cinemato.setText(name);
						tvCinematoId.setText(id);
					} catch (Exception e) {
					}
					Toast.makeText(getApplicationContext(), name + "   " + id,
							Toast.LENGTH_SHORT).show();
				} else if (actress1) {
					try {
						JSONObject jsonObject = new JSONObject(actress.get(pos));
						name = jsonObject.getString("name");
						id = jsonObject.getString("id");
						tv_actress.setText(" "+name);
						tvActressId.setText(id);

					} catch (Exception e) {
					}
					Toast.makeText(getApplicationContext(), name + "   " + id,
							Toast.LENGTH_SHORT).show();
				} else if (director1) {
					try {
						JSONObject jsonObject = new JSONObject(directorStrArray
								.get(pos));
						name = jsonObject.getString("name");
						id = jsonObject.getString("id");
						tv_director.setText(name);
						tvDirectorId.setText(id);

					} catch (Exception e) {
					}
					Toast.makeText(getApplicationContext(), name + "   " + id,
							Toast.LENGTH_SHORT).show();
				}

				mChooseImageDialog.dismiss();
			}
		});
	}

	/**
	 * 
	 */
	protected void submitRating() {

		story_rating = rat_story.getRating();
		cinemato_rating = rat_cinematography.getRating();
		actress_rating = rat_actress.getRating();
		actor_rating = rat_actor.getRating();
		director_rating = rat_director.getRating();

		String strStory = Float.toString(story_rating);
		String strAnimation = Float.toString(rat_animation.getRating());
		String strCinemato = Float.toString(cinemato_rating);
		String strActor = Float.toString(actor_rating);
		String strActress = Float.toString(actress_rating);
		String strDirector = Float.toString(director_rating);

		String actorId = tvActorId.getText().toString().trim();
		String actressId = tvActressId.getText().toString().trim();
		String storyId = tvStoryId.getText().toString().trim();
		String directorId = tvDirectorId.getText().toString().trim();
		String cinematoId = tvCinematoId.getText().toString().trim();

		String weekId = "1";
		String userId = Utility
				.getSharedPreferences(VotingBooth.this, "UserId");
		userId = "umesh1231";
		listNameValuePairs.add(new BasicNameValuePair("moviid", "3"));

		listNameValuePairs.add(new BasicNameValuePair("actorid", actorId));
		listNameValuePairs.add(new BasicNameValuePair("actressid", actressId));
		listNameValuePairs.add(new BasicNameValuePair("cinematogaraphyid",
				cinematoId));
		listNameValuePairs.add(new BasicNameValuePair("storyid", storyId));
		listNameValuePairs.add(new BasicNameValuePair("userid", userId));

		listNameValuePairs.add(new BasicNameValuePair("movi_ratings",
				strDirector));
		listNameValuePairs
				.add(new BasicNameValuePair("actor_ratings", strActor));
		listNameValuePairs.add(new BasicNameValuePair("actress_ratings",
				strActress));
		listNameValuePairs.add(new BasicNameValuePair(
				"cinematogaraphy_ratings", strCinemato));
		/*
		 * listNameValuePairs.add(new BasicNameValuePair( "animation",
		 * strAnimation));
		 */
		listNameValuePairs
				.add(new BasicNameValuePair("story_ratings", strStory));
		listNameValuePairs.add(new BasicNameValuePair("week_id", weekId));

		new SendRatings().execute();
	}

	public class SendRatings extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new Dialog(VotingBooth.this);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			String url = Constant.serverURL + "method=ratings";
			System.out.println("set url is   " + url);
			result = Utility.postParamsAndfindJSON(url, listNameValuePairs);
			listNameValuePairs.clear();

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result.contains("1")) {

				rat_actor.setRating(0);
				rat_actress.setRating(0);
				rat_cinematography.setRating(0);
				rat_story.setRating(0);
				rat_director.setRating(0);

				tv_actor.setText("Actor");
				tv_actress.setText("Actress");
				tv_cinemato.setText("Cinematography");
				tv_director.setText("Director");
				tv_story.setText("Story");

				showDialogNew("Thanks For Voting!");

			} else if (result.contains("0")) {

				rat_actor.setRating(0);
				rat_actress.setRating(0);
				rat_cinematography.setRating(0);
				rat_story.setRating(0);
				rat_director.setRating(0);

				tv_actor.setText("Actor");
				tv_actress.setText("Actress");
				tv_cinemato.setText("Cinematography");
				tv_director.setText("Director");
				tv_story.setText("Story");

				showDialogNew("You have Already Voted on this Program");
			}

		}
	}

	public class GetMovies extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// String filepath = null;
			String week_id = "1";
			String url = Constant.serverURL
					+ "method=GetProgramMovies&week_id=" + week_id;
			result1 = Utility.findJSONFromUrl(url);
			return result1;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			parseJson(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private void findViewByIds() {

		rat_animation = (RatingBar) findViewById(R.id.ratingbar_animation);
		rat_director = (RatingBar) findViewById(R.id.ratingbar_acting);
		rat_story = (RatingBar) findViewById(R.id.ratingbar_story);
		rat_cinematography = (RatingBar) findViewById(R.id.ratingbar_cinemato);
		rat_actress = (RatingBar) findViewById(R.id.ratingbar_actress);
		rat_actor = (RatingBar) findViewById(R.id.ratingbar_actor);
		btnVote = (Button) findViewById(R.id.btn_submit_rating);
		btnShare = (Button) findViewById(R.id.btn_share2);
		tv_actor = (TextView) findViewById(R.id.text_actor);
		tv_cinemato = (TextView) findViewById(R.id.text_cinemato);
		tv_story = (TextView) findViewById(R.id.text_story);
		tv_actress = (TextView) findViewById(R.id.text_actress);
		tv_director = (TextView) findViewById(R.id.text_director);
		tvActorId = (TextView) findViewById(R.id.textactor_id);
		tvActressId = (TextView) findViewById(R.id.textactressid);
		tvStoryId = (TextView) findViewById(R.id.textstory_id);
		tvCinematoId = (TextView) findViewById(R.id.textcinemato_id);
		tvDirectorId = (TextView) findViewById(R.id.textdirector_id);
		tvFilmTitle = (TextView) findViewById(R.id.text_film);
		jackpot_txt = (TextView) findViewById(R.id.doller_value_txt);
		film_image = (ImageView) findViewById(R.id.film_image);

	}

	/**
	 * 
	 */
	public void showDialogNew(String msg) {

		mChooseImageDialog = new Dialog(VotingBooth.this);
		mChooseImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChooseImageDialog.setContentView(R.layout.display);
		mChooseImageDialog.setCancelable(true);

		Button btn_facebook = (Button) mChooseImageDialog
				.findViewById(R.id.btn_facebook1);
		TextView textView = (TextView) mChooseImageDialog
				.findViewById(R.id.tv1);
		textView.setText(msg);
		btn_facebook.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				mChooseImageDialog.dismiss();

			}
		});

		mChooseImageDialog.show();

	}

	public class GetRatings extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			

		}

		@Override
		protected String doInBackground(String... params) {
			String url = Constant.serverURL + "method=GetRatingData&film_id="+ film_id;
			result = Utility.findJSONFromUrl(url);
			if(result!=null){
				
				try {
					JSONObject ratingJsonObject = new JSONObject(result);
					JSONObject ratingArray = ratingJsonObject.getJSONObject("ratings");
					JSONArray actorsArray = ratingArray.getJSONArray("actors");
					JSONArray actressArray = ratingArray.getJSONArray("actress");
					JSONObject filmObject = ratingArray.getJSONObject("film");

					cinematographer = filmObject.getString("cinematographer");
					strEditor = filmObject.getString("editor");
					strStory = filmObject.getString("story");
					image_path=filmObject.getString("movie_image");
					image_bitmap=Utility.getBitmap(image_path);
					
					for (int i = 0; i < actorsArray.length(); i++) {
						strActor = actorsArray.getString(i);
						actor.add(strActor);
					}
					for (int i = 0; i < actressArray.length(); i++) {
						strActress = actressArray.getString(i);
						actress.add(strActress);
					}

					Log.d("Check", "Check");

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		
			System.out.println("result in voting rating  "+result);
			
			/*
			tv_cinemato.setText(" "+cinematographer);
			tv_director.setText(" "+strEditor);
			tv_story.setText(" "+strStory);*/
			film_image.setImageBitmap(image_bitmap);
		}
	}

	/**
	 * @param result2
	 */
/*	public void parseRatings(String result2) {
		
	}
*/
	protected void chooseSocial() {
		mChooseImageDialog = new Dialog(VotingBooth.this);
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

				Intent fbIntent = new Intent(VotingBooth.this,
						ShareOnFacebook.class);
				fbIntent.putExtra("facebookMessage", "Test From Application");

				startActivity(fbIntent);
				finish();
				mChooseImageDialog.dismiss();

			}
		});
		mTwitter = new TwitterApp(VotingBooth.this, twitter_consumer_key,
				twitter_secret_key);
		mTwitter.setListener(mTwLoginDialogListener);
		/*
		 * btn_twitter.setOnClickListener(new OnClickListener() {
		 * 
		 * 
		 * public void onClick(View v) {
		 * 
		 * if (mTwitter.hasAccessToken()) {
		 * 
		 * String username = mTwitter.getUsername();
		 * 
		 * if (username != null) username = (username.equals("")) ? "Unknown" :
		 * username; new TwitterSender().execute();
		 * 
		 * } else { onTwitterClick(); }
		 * 
		 * mChooseImageDialog.dismiss(); } });
		 */

		btn_twitter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				onTwitterClick();
				mChooseImageDialog.dismiss();
			}
		});

		btn_email.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				final Intent emailIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				// emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new
				// String[]{"mahadev.linkites@gmail.com"});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"NextGen Film Festival");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						"Details About App");
				VotingBooth.this.startActivity(Intent.createChooser(
						emailIntent, "Send mail..."));
				mChooseImageDialog.dismiss();
				
				new PostRewards().execute();
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
			/*
			 * try { mTwitter.updateStatus(verse); Toast.makeText(appContext,
			 * "Prayer Posted to Twitter for " + username, handle exception
			 * Utility.ShowAlertWithMessage(appContext, "Failed",
			 * "Error Occur while posting this prayer"); e.printStackTrace(); }
			 */
			if (mTwitter.hasAccessToken()) {
				new TwitterSender().execute();
			} else {
				Toast.makeText(VotingBooth.this, "Please Login First",
						Toast.LENGTH_SHORT).show();
			}
		}

		public void onError(String value) {

		}
	};

	private class TwitterSender extends AsyncTask<URL, Integer, Long> {
		private String url;
		ProgressDialog mProgressDialog;

		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(VotingBooth.this, "",
					"Posting Details...", true);

			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		protected Long doInBackground(URL... urls) {
			long result = 0;

			TwitterSession twitterSession = new TwitterSession(VotingBooth.this);
			AccessToken accessToken = twitterSession.getAccessToken();

			Configuration conf = new ConfigurationBuilder()
					.setOAuthConsumerKey(twitter_consumer_key)
					.setOAuthConsumerSecret(twitter_secret_key)
					.setOAuthAccessToken(accessToken.getToken())
					.setOAuthAccessTokenSecret(accessToken.getTokenSecret())
					.build();

			/*
			 * OAuthAuthorization auth = new OAuthAuthorization(conf,
			 * conf.getOAuthConsumerKey(), conf.getOAuthConsumerSecret(), new
			 * AccessToken(conf.getOAuthAccessToken(),
			 * conf.getOAuthAccessTokenSecret()));
			 * 
			 * ImageUpload upload = ImageUpload.getTwitpicUploader(
			 * twitpic_api_key, auth);
			 * 
			 * Log.d("", "Start sending image...");
			 */
			try {
				/*
				 * String path = Environment.getExternalStorageDirectory()
				 * .toString(); url = upload.upload(new File(path,
				 * "emoticon.png"));
				 */
				result = 1;

				Twitter tt = new TwitterFactory(conf).getInstance();
				String currentDateTimeString = DateFormat.getDateTimeInstance()
						.format(new Date());
				twitter4j.Status response = tt
						.updateStatus("My film has been nominated to be screened @ Nexgen Mobile FilmFest 2013.Help me win best film,to do so download app and vote."
								+ (int)(System.currentTimeMillis()/10000)); // posting
				// status
				// to
				// twitter
				System.out.println("status is : " + response);

			} catch (Exception e) {
				Log.e("", "Failed to send status");
				result = 0;
				e.printStackTrace();
			}

			return result;
		}

		protected void onPostExecute(Long result) {

			if(result==1){
				new PostRewards().execute();
			}else{
				Toast.makeText(mContext,"Failed..." , Toast.LENGTH_LONG).show();
			}

			/*if (mProgressDialog != null) {
				mProgressDialog.cancel();
				finish();
			}*/
		}
	}

	private void onTwitterClick() {
		if (mTwitter.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					VotingBooth.this);

			builder.setMessage("Delete current Twitter connection?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									mTwitter.resetAccessToken();
									/*
									 * twitterBtn
									 * .setBackgroundDrawable(getResources()
									 * .getDrawable(
									 * R.drawable.twitter_check_off));
									 */// mTwitterBtn.setChecked(false);
										// mTwitterBtn.setText("  Twitter (Not connected)");
										// mTwitterBtn.setTextColor(Color.GRAY);
									/*
									 * mText.setText("No Login");
									 * mUserPic.setImageDrawable(getResources()
									 * .getDrawable(
									 * android.R.drawable.alert_dark_frame));
									 */}
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

	/**
	 * @param result2
	 */
	public void parseJson(String result2) {
		try {
			JSONObject jsonObject = new JSONObject(result2);
			JSONArray jsonArray = jsonObject.getJSONArray("program");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject film = jsonObject2.getJSONObject("film");
				String title = film.getString("title");
				listTitle.add(title);
				// adapter.notifyDataSetChanged();
				String id = film.getString("id");
				listTitleId.add(id);
			}

		} catch (JSONException e) {
			e.printStackTrace();
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
			
			if (result!= null) {
				try {
					// list_petName.add("Select Pet");
					JSONObject jsonObject = new JSONObject(result);
					jsonObject = jsonObject.getJSONObject("jackpot");
					
					String jackPotName = jsonObject.getString("name");
					Value = jsonObject.getString("value");
											
					System.out.println("Jacpot name is   "+jackPotName+"  >><<< "+Value);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		protected void onPostExecute(String result) {
			if (result!= null) {
		
				jackpot_txt.setText(Value);
			}else{
				Toast.makeText(getApplicationContext(), "Please Check Network", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
	public class PostRewards extends AsyncTask<Void, Void, String>{

		String result;
		Dialog dialog;
		
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String strUserId=Utility.getSharedPreferences(mContext, "UserId");
			System.out.println("reward user id is  "+strUserId);
			
			ArrayList<NameValuePair> listNameValuePairs = new ArrayList<NameValuePair>();
			
			if(userRequeste==2){
				listNameValuePairs.add(new BasicNameValuePair("userid", strUserId));
			}else if(userRequeste==1){
				listNameValuePairs.add(new BasicNameValuePair("user_id", strUserId));
			}
			
			String url = Constant.serverURL + "method=AddShare_Reword";
			
			System.out.println("URL REWARD is  "+url);
			String result = Utility.postParamsAndfindJSON(url,
					listNameValuePairs);
			listNameValuePairs = null;
			System.out.println("result in petProfile" + result);
			
			return result;
			
		}
		
		protected void onPostExecute(String result) {
			
			if (result == null) {
				Utility.ShowAlertWithMessage(mContext, R.string.Alert,
						R.string.network_connection);
			} else {
				if (result.contains("1")) {
					Utility.ShowAlertWithMessage(mContext,
							R.string.Alert, "Rewarded");
				} else {
					Utility.ShowAlertWithMessage(mContext,
							R.string.Alert, "Not Rewarded");
				}
			}

		}
	}
}
