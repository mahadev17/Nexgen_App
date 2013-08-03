package com.linkites.nexgenfilmfestival.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

@SuppressLint({ "NewApi", "NewApi" })
public class CommonUtility {
	public static final String PREFS_NAME = "LimbsAnalysisFile";
	public static final String serverUrl = "http://projects.linkites.com/arabfun/Api.php?method=";
	public static int getMicroDegreeValuesFromLatorLng(double value){ return (int) (value*1000000);}

	
	public static void setApplicationPreferences(Context context,String name,String value)
	{
			SharedPreferences settings =  context.getSharedPreferences(PREFS_NAME, 0);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString(name, value);
		      // Commit the edits!
		      editor.commit();
	}
	public static String getApplicationPreferences(Context context,String name)
	{
		SharedPreferences settings =  context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(name, "1");
	}
	
	// to convert bitmap image into string
			public static String encodeTobase64usingByte(byte[]b) {
				

			
				String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
				// String imageEncoded = Base64Coder.encodeTobase64(image);

				// Log.d("LOOK", imageEncoded);
				return imageEncoded;
			}
			public static String encodeTobase64(Bitmap image) {
				Bitmap immagex = image;

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] b = baos.toByteArray();
				String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
				// String imageEncoded = Base64Coder.encodeTobase64(image);

				// Log.d("LOOK", imageEncoded);
				return imageEncoded;
			}
			
		// to convert string into bitmap image	
			public static Bitmap decodeBase64(String input) {
				try {
					byte[] decodedByte = Base64.decode(input.getBytes(), 0);

					return BitmapFactory.decodeByteArray(decodedByte, 0,
							decodedByte.length);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
	
	
	public static String findJSONFromUrl(String url) {
		// TODO Auto-generated method stub
		JSONObject jObj=new JSONObject();
		String result="";
		
		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			// httpGet.setURI(new URI(url));

			HttpResponse httpResponse = httpClient.execute(httpGet);
			int status = httpResponse.getStatusLine().getStatusCode();
			System.out.println("status of jsonparser is : "
					+ status);
			
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				Log.d("Builder", line);
			}

			is.close();
			result = sb.toString();
			System.out.println("string result from " + result);

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	public static String postDataWithURLAndGetJSON(Context applicationContext,String url,ArrayList<NameValuePair> listParamsWithValues) 
	{
		try
		{
	        int TIMEOUT_MILLISEC = 100000;  // = 10 seconds
	        HttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
	        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
	        DefaultHttpClient client = new DefaultHttpClient(httpParams);
	        HttpPost request = new HttpPost(url);
	       	request.setEntity(new UrlEncodedFormEntity(listParamsWithValues));
	        HttpResponse response = client.execute(request);
	        Log.d("url", "responsehttp "+response.toString());
	        String responseString = request(response);
	        System.out.println("responseString "+responseString);
	        return responseString; 
	    } catch (Exception e) {
	       //Some things goes Wrong 
	    	e.printStackTrace();
	    }finally 
	    {
	    	
	    }
		return null;
	}
	public static String request(HttpResponse response)
	{
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
            System.out.println("result "+result);
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }
	public static void ShowAlertWithMessage(Context context,String title,String msg)
	{
		//Assign the alert builder , this can not be assign in Click events 
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setTitle(title);
		// Set behavior of negative button
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
			}
		});
        AlertDialog  alert = builder.create();
		alert.show();
	}

	public static void ShowAlertWithMessageTwoButton(Context context,String title,String msg)
	{
		
		//Assign the alert builder , this can not be assign in Click events 
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setTitle(title);
		// Set behavior of negative button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
			setStatus(false);
			}
		});
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setStatus(false);
			}
		});
        AlertDialog  alert = builder.create();
		alert.show();
		
	}
static int id=0;
	public static void setLength(int _id){
		id = _id;
	}
	public static int getLength(){
		System.out.println("common "+id);
		return id;
	}
	static boolean status=false;
	public static void setStatus(boolean state){
		status = state;
	}
	public static boolean getStatus(){
		
		return status;
	}
	public static String postedDate;
	public static String getDated;
	public static Date date;
	public static int mMonth, mYear, mDate, mHour, mMinute, mSecond;
	public static int curDate,curMonth,curYear,curHour,curMinute,curSecond;
	public static String secretImageInfo(String dateSend) {
		
					
						date = convertdate(dateSend);
						mMonth = date.getMonth();
						mMonth = mMonth + 1;
						mYear = date.getYear();
						mYear = mYear + 1900;
						mDate = date.getDate();
						mHour = date.getHours();
						mMinute = date.getMinutes();
						mSecond = date.getSeconds();

						Calendar c = Calendar.getInstance();
						curMonth = c.get(Calendar.MONTH);
						curMonth = curMonth + 1;
						curYear = c.get(Calendar.YEAR);
						curDate = c.get(Calendar.DATE);
						curHour = c.get(Calendar.HOUR_OF_DAY);
						curMinute = c.get(Calendar.MINUTE);
						curSecond = c.get(Calendar.SECOND);
						System.out.println("current " + curMonth + " post Month " + mMonth);
						System.out.println("current " + curYear + " post year  " + mYear);

						int pMonth = 0, pYear = 0, pDate = 0, pHour = 0, pMinute = 0, pSecond = 0;
						pMonth = (curMonth >= mMonth) ? curMonth - mMonth : mMonth
								- curMonth;
						System.out.println("pMonth " + pMonth);
						pDate = (curDate >= mDate) ? (curDate - mDate) : (mDate - curDate);
						pYear = (curYear >= mYear) ? (curYear - mYear) : (mYear - curYear);
						Log.v("pYear", " " + pYear);
						pHour = (curHour >= mHour) ? (curHour - mHour) : (mHour - curHour);
						pMinute = (curMinute >= mMinute) ? (curMinute - mMinute)
								: (mMinute - curMinute);
						pSecond = (curSecond >= mSecond) ? (curSecond - mSecond)
								: (mSecond - curSecond);
						getDated = imagePostedOn(pMonth, pDate, 0, pHour, pMinute, pSecond)
								+ " ago";
					
					return getDated;

				}
			
			public static Date convertdate(String stringDate) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date convertDate;
				try {
					convertDate = sdf.parse(stringDate);
					return convertDate;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;

			}

			public static String imagePostedOn(int month, int date, int year, int hour,
					int minute, int second) {
				String orignalTime;
				if (year > 0) {
					if (month == 1) {
						if (curDate >= mDate) {
							orignalTime = "" + year + " Year";
						} else {
							orignalTime = "" + (31 - date) + " Day";
						}
						return orignalTime;
					}

				} else if (month > 0) {
					switch (mMonth) {
					case 1: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;

						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 2: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (28 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 3: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 4: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (30 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 5: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 6: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (30 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 7: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 8: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 9: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (30 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 10: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}
					case 11: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (30 - date) + " Day";
							}
							return orignalTime;
						}
						break;
					}
					case 12: {
						if (month == 1) {
							if (curDate >= mDate) {
								orignalTime = "" + month + " Month";
							} else {
								orignalTime = "" + (31 - date) + " Day";
							}
							return orignalTime;
						} else {
							orignalTime = "" + month + " Month";
						}
						return orignalTime;
					}

					}

				} else if (date > 0) {
					orignalTime = "" + date + " Day";
					return orignalTime;
				} else if (hour > 0) {
					orignalTime = "" + hour + " Hours";
					return orignalTime;
				} else if (minute > 0) {
					orignalTime = "" + minute + " Minutes";
					return orignalTime;
				} else if (second >= 0) {
					orignalTime = "" + second + " Seconds";
					return orignalTime;
				}

				return null;

			}
			public static Bitmap getBitmap(String url) {
				Bitmap bm = null;
				HttpClient httpclient=null;
				try {
					URL aURL = new URL(url);
					URLConnection conn = aURL.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					try {
					    Bitmap myBitmap;
						BitmapFactory.Options options = new BitmapFactory.Options(); 
						options.inPurgeable = true;
						options.outHeight = 50;
						options.outWidth = 50;
						options.inSampleSize = 4;
						bm = BitmapFactory.decodeStream(bis, null, options);	//DecodeFile(is, options);
				bm = Bitmap.createScaledBitmap(bm, 143, 158, true);
		//				bm = BitmapFactory.decodeStream(new FlushedInputStream(is));
					} catch (OutOfMemoryError error) {
						error.printStackTrace();
						System.out.println("exception in get bitma putility");
					}

					bis.close();
					is.close();
				} catch (Exception e) {
					System.out.println("exception in get bitma putility");
					e.printStackTrace();
				} finally {
					
				}
				return 	bm ;
			}

			static class FlushedInputStream extends FilterInputStream {
				public FlushedInputStream(InputStream inputStream) {
					super(inputStream);
				}
			}

}// class ends here 
