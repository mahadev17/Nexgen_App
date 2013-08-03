/**
 * 
 */
package com.linkites.nexgenfilmfestival;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author ${Umesh I speak my mind. I never mind what I speak. }
 *
 * ${Changes are in your Hands. Do not get Inspired By other}
 */
public class CommentAdapter extends ArrayAdapter<String>{
	
	private List<String> ratingList;
	private Context context;
	public CommentAdapter(List<String> planetList, Context ctx) {
	    super(ctx, R.layout.comment_item, planetList);
	    this.ratingList = planetList;
	    this.context = ctx;
	}
	 
	public View getView(int position, View convertView, ViewGroup parent) {
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.comment_item, parent, false);
	    }
	        // Now we can fill the layout with the right values
	        TextView tv = (TextView) convertView.findViewById(R.id.u_name);
	        TextView distView = (TextView) convertView.findViewById(R.id.tv_comment);
	        //Planet p = planetList.get(position);
	 
	        String data=ratingList.get(position);
	        try{
	        JSONObject ratingData=new JSONObject(data);
	        
	        String name=ratingData.getString("useremail");
	        String comment=ratingData.getString("comment_decs");
	        tv.setText(name);
	        distView.setText(comment);
	        }catch (Exception e) {
				// TODO: handle exception
			}
	        
	     
	     
	    return convertView;
	}

}
