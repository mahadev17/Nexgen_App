package com.linkites.nexgenfilmfestival;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;

public class PlayActivity extends YouTubeFailureRecoveryActivity implements
		View.OnClickListener, TextView.OnEditorActionListener,
		CompoundButton.OnCheckedChangeListener,
		AdapterView.OnItemSelectedListener {

	Context mContext;

	WebView webView;
	// Context mContext;
	// VideoView view;
	String result;
	public static String videoPath;
	String deviceId;
	String videoId;
	String FilmId;
	String week_id;
	int duration;
	ImageView imageView1, imageView2, imageView3;
	LinearLayout lay1, lay2, lay3;
	ListView listView;
	FilmAdapter adapter;
	ArrayList<String> list;
	RelativeLayout list_relative_layout;
	LinearLayout player_view_layout;
	LinearLayout player_view_layout1;
	private static final int REQ_START_STANDALONE_PLAYER = 1;

	// Youtube Variable PLEN9i3w-ULroZY7k6wLTdGHO09co3KnGy   PL62AF130B8883E493
	private static final ListEntry[] ENTRIES = { new ListEntry(
			"Playlist: Google I/O 2012", "PLEN9i3w-ULroZY7k6wLTdGHO09co3KnGy",
			true) };
	
	//original
	/** private static final ListEntry[] ENTRIES = { new ListEntry(
			"Playlist: Google I/O 2012", "PLEN9i3w-ULrrJpm17RVtGWw1u38Yjto7X",
			true) };**/

	private static final String KEY_CURRENTLY_SELECTED_ID = "currentlySelectedId";

	private YouTubePlayerView youTubePlayerView;
	private YouTubePlayer player;
	private ArrayAdapter<ListEntry> videoAdapter;
	private Button playButton;
	private Button pauseButton;
	private Button fullScreenButton;
	private RadioGroup styleRadioGroup;
	private MyPlayerStateChangeListener playerStateChangeListener;
	private MyPlaybackEventListener playbackEventListener;

	private int currentlySelectedPosition;
	private String currentlySelectedId;

	private boolean fullscreen;
	boolean backFlag = true;
	private View otherViews;
	private LinearLayout baseLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.screening_room);
		mContext = this;
		list = new ArrayList<String>();
		Bundle bundle = getIntent().getExtras();
		week_id = bundle.getString("programId");

		// Youtube Code

		youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		baseLayout = (LinearLayout) findViewById(R.id.layout);
		playButton = (Button) findViewById(R.id.play_button);
		pauseButton = (Button) findViewById(R.id.pause_button);
		fullScreenButton = (Button) findViewById(R.id.full_screen_button);
		otherViews = findViewById(R.id.other_views);

		styleRadioGroup = (RadioGroup) findViewById(R.id.style_radio_group);
		((RadioButton) findViewById(R.id.style_chromeless))
				.setOnCheckedChangeListener(this);

		videoAdapter = new ArrayAdapter<ListEntry>(this,
				android.R.layout.simple_spinner_item, ENTRIES);
		videoAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		playButton.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		fullScreenButton.setOnClickListener(this);

		youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this);

		playerStateChangeListener = new MyPlayerStateChangeListener();
		playbackEventListener = new MyPlaybackEventListener();

		currentlySelectedPosition = 0;
		playVideoAtSelection();

		setControlsEnabled(true);

		list_relative_layout = (RelativeLayout) findViewById(R.id.relative_layout_list);

		new GetVideos().execute();
		listView = (ListView) findViewById(R.id.listView4);
		adapter = new FilmAdapter(list, PlayActivity.this);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, final View arg1,
					int arg2, long arg3) {

				String id;
				try {

					JSONObject jsonObject = new JSONObject(list.get(arg2));
					
					id = jsonObject.getString("id");
					arg1.setSelected(true);
					arg1.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.list_selector));
					Utility.setSharedPreference(mContext, "FilmId", id);
					Intent intent = new Intent(PlayActivity.this,
							ShowDetailsActivity.class);
					startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.video_menu, menu);
		return true;
	}

	public class GetVideos extends AsyncTask<String, String, String> {

		Dialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new Dialog(mContext);
			dialog.setContentView(R.layout.custom_dialog_view);
			dialog.setTitle("NexGen");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			String filepath = null;
			week_id = "1";
			String url = Constant.serverURL
					+ "method=GetProgramMovies&week_id=" + week_id;
			System.out.println("url is  " + url);
			result = Utility.findJSONFromUrl(url);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			if (result == null) {
				Utility.ShowAlertWithMessage(getApplicationContext(),
						R.string.Alert, R.string.network_connection);
			} else {
				parseJson(result);
			}
			dialog.dismiss();
		}
	}

	@Override
	public void onBackPressed() {

		if (!backFlag) {
			doLayout();
		} else {
			super.onBackPressed();
			Intent intent = new Intent(PlayActivity.this,
					TaboptionsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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
				String film = jsonObject2.getString("film");

				System.out.println("Film Json List is  " + film);
				list.add(film);
				adapter.notifyDataSetChanged();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void onInitializationSuccess(YouTubePlayer.Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		this.player = player;
		player.setPlayerStateChangeListener(playerStateChangeListener);
		player.setPlaybackEventListener(playbackEventListener);
		player.setPlayerStyle(PlayerStyle.CHROMELESS);
		if (!wasRestored) {
			playVideoAtSelection();
		}
		setControlsEnabled(true);
	}

	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return youTubePlayerView;
	}

	private void playVideoAtSelection() {
		ListEntry selectedEntry = videoAdapter
				.getItem(currentlySelectedPosition);
		if (selectedEntry.id != currentlySelectedId && player != null) {
			currentlySelectedId = selectedEntry.id;
			if (selectedEntry.isPlaylist) {
				player.cuePlaylist(selectedEntry.id);
			} else {
				player.cueVideo(selectedEntry.id);
			}
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Do nothing.
	}

	public void onClick(View v) {
		if (v == playButton) {
			player.play();
		} else if (v == pauseButton) {
			player.pause();
		} else if (v == fullScreenButton) {
			player.setFullscreen(!fullscreen);
		}
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		state.putString(KEY_CURRENTLY_SELECTED_ID, currentlySelectedId);
	}

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		currentlySelectedId = state.getString(KEY_CURRENTLY_SELECTED_ID);
	}

	private void updateText() {

	}

	private void setControlsEnabled(boolean enabled) {
		playButton.setEnabled(enabled);
		pauseButton.setEnabled(enabled);
	}

	private final class MyPlaybackEventListener implements
			PlaybackEventListener {
		String playbackState = "NOT_PLAYING";
		String bufferingState = "";

		public void onPlaying() {
			playbackState = "PLAYING";
			updateText();
		}

		public void onBuffering(boolean isBuffering) {
			bufferingState = isBuffering ? "(BUFFERING)" : "";
			updateText();
		}

		public void onStopped() {
			playbackState = "STOPPED";
			updateText();
		}

		public void onPaused() {
			playbackState = "PAUSED";
			updateText();
		}

		public void onSeekTo(int arg0) {
			// TODO Auto-generated method stub

		}

	}

	private final class MyPlayerStateChangeListener implements
			PlayerStateChangeListener {
		String playerState = "UNINITIALIZED";

		public void onLoading() {
			playerState = "LOADING";
			updateText();

		}

		public void onLoaded(String videoId) {
			playerState = String.format("LOADED %s", videoId);
			updateText();
		}

		public void onAdStarted() {
			playerState = "AD_STARTED";
			updateText();
		}

		public void onVideoStarted() {
			playerState = "VIDEO_STARTED";
			updateText();
		}

		public void onVideoEnded() {
			playerState = "VIDEO_ENDED";
			updateText();
		}

		public void onError(ErrorReason reason) {
			playerState = "ERROR (" + reason + ")";
			if (reason == ErrorReason.UNEXPECTED_SERVICE_DISCONNECTION) {
				// When this error occurs the player is released and can no
				// longer be used.
				player = null;
				setControlsEnabled(false);
			}
			updateText();
		}

	}

	private static final class ListEntry {

		public final String title;
		public final String id;
		public final boolean isPlaylist;

		public ListEntry(String title, String videoId, boolean isPlaylist) {
			this.title = title;
			this.id = videoId;
			this.isPlaylist = isPlaylist;
		}

		@Override
		public String toString() {
			return title;
		}

	}

	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	private void doLayout() {
		LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) youTubePlayerView
				.getLayoutParams();
		if (fullscreen) {
			// When in fullscreen, the visibility of all other views than the
			// player should be set to
			// GONE and the player should be laid out across the whole screen.
			otherViews.setVisibility(View.GONE);
			backFlag = false;
			playerParams.width = LayoutParams.MATCH_PARENT;
			playerParams.height = LayoutParams.MATCH_PARENT;

		} else {
			// This layout is up to you - this is just a simple example
			// (vertically stacked boxes in
			// portrait, horizontally stacked in landscape).
			otherViews.setVisibility(View.VISIBLE);
			ViewGroup.LayoutParams otherViewsParams = otherViews
					.getLayoutParams();
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				playerParams.width = otherViewsParams.width = 0;
				playerParams.height = WRAP_CONTENT;
				otherViewsParams.height = MATCH_PARENT;
				playerParams.weight = 1;
				baseLayout.setOrientation(LinearLayout.HORIZONTAL);
			} else {
				playerParams.width = otherViewsParams.width = MATCH_PARENT;
				playerParams.height = WRAP_CONTENT;
				playerParams.weight = 0;
				otherViewsParams.height = 0;
				baseLayout.setOrientation(LinearLayout.VERTICAL);
			}
			setControlsEnabled();
		}
	}

	private void setControlsEnabled() {

		fullScreenButton.setEnabled(player != null);
	}

	public void onFullscreen(boolean isFullscreen) {
		fullscreen = isFullscreen;
		doLayout();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		doLayout();
	}

}
