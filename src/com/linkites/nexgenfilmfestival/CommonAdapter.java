/**
 * 
 */
package com.linkites.nexgenfilmfestival;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint.Join;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author ${Umesh I speak my mind. I never mind what I speak. }
 * 
 *         ${Changes are in your Do not get Inspired By other}
 */
public class CommonAdapter extends ArrayAdapter<String> {

	private List<String> ratingList;
	private Context context;

	public CommonAdapter(List<String> planetList, Context ctx) {
		super(ctx, R.layout.item_list, planetList);
		this.ratingList = planetList;
		this.context = ctx;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list, parent, false);
		}
		// Now we can fill the layout with the right values
		TextView tv = (TextView) convertView.findViewById(R.id.name);
		TextView distView = (TextView) convertView.findViewById(R.id.dist);
		// Planet p = planetList.get(position);

		String data = ratingList.get(position);
		try {
			JSONObject ratingData = new JSONObject(data);

			String name = ratingData.getString("name");
			String id = ratingData.getString("id");
			tv.setText(name);
			distView.setText(id);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return convertView;
	}

}