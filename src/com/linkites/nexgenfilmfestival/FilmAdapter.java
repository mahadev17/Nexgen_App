/**
 * 
 */
package com.linkites.nexgenfilmfestival;

import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.linkites.Constant.Constant;
import com.linkites.nexgenfilmfestival.FirstWeek.GetRewards;
import com.linkites.nexgenfilmfestival.utility.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author ${Umesh I speak my mind. I never mind what I speak. }
 *
 * ${Changes are in your Hands. Do not get Inspired By other}
 */
public class FilmAdapter extends ArrayAdapter<String>{
	
	private List<String> ratingList;
	private Context context;
	private Bitmap bitmap_img;
	String image_path;
	ImageView thambneil_img;
	JSONObject ratingData;
	
	public FilmAdapter(List<String> planetList, Context ctx) {
	    super(ctx, R.layout.film_list, planetList);
	    this.ratingList = planetList;
	    this.context = ctx;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.film_list, parent, false);
	    }
	        // Now we can fill the layout with the right values
	        TextView tv = (TextView) convertView.findViewById(R.id.title);
	        TextView distView = (TextView) convertView.findViewById(R.id.text_id);
	        thambneil_img=(ImageView) convertView.findViewById(R.id.thumbnail);
	        
	 
	        String data=ratingList.get(position);
	        try{
	        ratingData=new JSONObject(data);
	        String name=ratingData.getString("title");
	        String id=ratingData.getString("id");
	      
	        image_path= ratingData.getString("movie_image");
	      
	        new GetImage().execute();
	        
	        
	        System.out.println("Bitmap Image  "+bitmap_img);
	        
	        System.out.println("List Item Id is  "+id);
	        System.out.println("List Item Name is  "+name);
	        
	        tv.setText(name); 
	        
	   
	        distView.setText(id);
	        }catch (Exception e) {
				// TODO: handle exception
			}
	     
	    return convertView;
	}
	
	public class GetImage extends AsyncTask<String, String, String> {
		String result=null; 
		
		@Override
		protected String doInBackground(String... arg0) {
			 bitmap_img=Utility.getBitmap(image_path);
		return result;
			
		}

		@Override
		protected void onPostExecute(String result) {
			if(bitmap_img!=null){
			thambneil_img.setImageBitmap(bitmap_img);
			}else{
//				thambneil_img.setImageDrawable(R.drawable.image);
			}
		}
	}

}
