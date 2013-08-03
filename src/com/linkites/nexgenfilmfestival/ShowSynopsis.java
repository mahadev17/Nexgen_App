package com.linkites.nexgenfilmfestival;

import org.json.JSONException;
import org.json.JSONObject;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.utility.Utility;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ShowSynopsis extends Activity {
	TextView tv_screenings, tv_description, tv_director, tv_producer,
			tv_editor, tv_cinemato;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.synopsis_show);
		findViewByIds();

		new GetSynopsis().execute();
	}

	/**
	 * 
	 */
	private void findViewByIds() {
		// TODO Auto-generated method stub
		tv_cinemato = (TextView) findViewById(R.id.text_cinemato1);
		tv_director = (TextView) findViewById(R.id.text_dir1);
		tv_producer = (TextView) findViewById(R.id.text_producer1);
		tv_editor = (TextView) findViewById(R.id.text_editor1);
		tv_description = (TextView) findViewById(R.id.text_desc);
		tv_screenings = (TextView) findViewById(R.id.text_cinema);
	}

	public class GetSynopsis extends AsyncTask<String, String, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONObject jsonObject2 = jsonObject.getJSONObject("synopsis");
				String producer = jsonObject2.getString("producer");
				String director = jsonObject2.getString("director");
				String editor = jsonObject2.getString("editor");
				String cinemato = jsonObject2.getString("cinematographer");
				String screening = jsonObject2.getString("screenings");
				String description = jsonObject2.getString("film_desc");
				

				tv_cinemato.setText(cinemato);
				tv_description.setText(description);
				tv_producer.setText(producer);
				tv_director.setText(director);
				tv_editor.setText(editor);
				tv_screenings.setText(screening);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String result;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			String url = Constant.serverURL + "method=GetSynopsis&film_id=" + 3;
			System.out.println("set url is   " + url);
			result = Utility.findJSONFromUrl(url);
			return result;
		}

	}
}
