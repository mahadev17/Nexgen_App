package com.linkites.nexgenfilmfestival.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.linkites.nexgenfilmfestival.NextRegistrationActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.TypedValue;


public class Utility {

	
	 public static Context appContext;
     private static String PREFERENCE;
     
     // for setting string preferences
     public static void setSharedPreference(Context context,String name,String value)
     {
		appContext = context;
		SharedPreferences settings = context
				.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		// editor.clear();
		editor.putString(name, value);
		editor.commit();
     }
     
     public static void setSharedPreference3(Context context,String name,String value)
     {
		appContext = context;
		SharedPreferences settings = context
				.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.putString(name, value);
		editor.commit();
     }
     
     public static void setSharedPreferenceBoolean(Context context,String name,Boolean value)
     {
		appContext = context;
		SharedPreferences settings = context
				.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(name, value);
		editor.commit();
     }
     
     
     public static void setSharedPreference(Context context,String name,int id)
     {
		appContext = context;
		SharedPreferences settings = context
				.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(name, id);
		editor.commit();
     }
     
     public static String getSharedPreferences(Context context,String name)
     {
             SharedPreferences settings=context.getSharedPreferences(PREFERENCE, 0);
             return settings.getString(name, null);        
     }
     
     public static int getSharedPreferences1(Context context,String name)
     {
             SharedPreferences settings=context.getSharedPreferences(PREFERENCE, 0);
             return settings.getInt(name,0);        
     }
     public static Boolean getSharedPreferences2(Context context,String signin_status)
     {
             SharedPreferences settings=context.getSharedPreferences(PREFERENCE, 0);
             return settings.getBoolean(signin_status,false);        
     }
	
	// this method convert the integer to Boolean
	public static boolean intToBoolean(int value) {
		if (value > 1)
			return false;
		return (value != 0);
	}

	// this method will convert boolean value to integer
	public static int booleanToInt(boolean value) {
		return ((value) ? 1 : 0);
	}

	// this method will convert the px to dp
	public static int GetPixelToDp(int value, Context context) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, r.getDisplayMetrics());
		return (int) px;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	// checkin whtheer user has entered all data
	public static boolean checkValiationForData(String txt) {
		return (txt.trim().length() == 0) ? true : false;
	}

	public static boolean isInternetConnectionAvailable(
			Context applicationContext) {
		try {
			// ConnectivityManager cm = (ConnectivityManager)
			// applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			// if (cm.getActiveNetworkInfo().isConnectedOrConnecting())
			// {
			// applicationContext.getSystemService(applicationContext.CONNECTIVITY_SERVICE).requestRouteToHost(T,
			// int hostAddress)
			URL url = new URL("http://www.google.com");
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			// urlc.setRequestProperty("User-Agent", "My Android Demo");
			// urlc.setRequestProperty("User-Agent", "Android Application:2");
			urlc.setRequestProperty("Connection", "close");
			urlc.setConnectTimeout(60); // mTimeout is in seconds
			urlc.connect();
			if (urlc.getResponseCode() == 200) {
				return true;
			} else {
				return false;
			}
			// return true;
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isInternetConnectionAvailableUsingXMLParsing(
			Context applicationContext) {
		try {
			// ConnectivityManager cm = (ConnectivityManager)
			// applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			// if (cm.getActiveNetworkInfo().isConnectedOrConnecting())
			// {
			// applicationContext.getSystemService(applicationContext.CONNECTIVITY_SERVICE).requestRouteToHost(T,
			// int hostAddress)
			URL url = new URL("http://m.google.com");
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setReadTimeout(30 * 60);

			int responseCode = httpConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK
					|| responseCode == 200) {
				return true;
			} else {
				return false;
			}
			// return true;
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		// return false;
		/*
		 * try { String urlString =
		 * "http://api.geoffiti.com/api/default.aspx?method=GetCategories&subdomain=dlba&RequestType=Business"
		 * ; URL rssUrl = new URL(urlString); SAXParserFactory
		 * mySAXParserFactory = SAXParserFactory.newInstance(); SAXParser
		 * mySAXParser = mySAXParserFactory.newSAXParser(); XMLReader
		 * myXMLReader = mySAXParser.getXMLReader(); RSSHandler myRSSHandler =
		 * new RSSHandler(); myRSSHandler.setRootElement("Category");
		 * myRSSHandler.setTrimString(true);
		 * myXMLReader.setContentHandler(myRSSHandler); InputSource
		 * myInputSource = new InputSource(rssUrl.openStream());
		 * myXMLReader.parse(myInputSource); RSSFeed myRssFeed =
		 * myRSSHandler.getFeed(); if (myRssFeed !=null) { return true; }else {
		 * return false; }
		 * 
		 * } catch (MalformedURLException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (ParserConfigurationException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (SAXException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } return false;
		 */
	}

	// this method validate the Null String and return the Blank String
	public static String validateAndSetString(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	// this method will convert the lat and lng double value in Micro degree
	// values
	/*
	 * public static int getMicroDegreeValuesFromLatorLng(double value){ return
	 * (int) (value*1000000);}
	 * 
	 * //this method will convert the lat and lng int Micro degree values in
	 * double value public static double getDoubleValuesFromMicroDgree(int
	 * value){ return (double) (value/1000000);}
	 */

	/*
	 * public static GeoPoint getMapCenterRegion(ArrayList<GeoPoint>
	 * coordinates) { int minLatitude = (int)(+90 * 1E6); int maxLatitude =
	 * (int)(-90 * 1E6);
	 * 
	 * int minLongitude = (int)(+181 * 1E6); int maxLongitude = (int)(-181 *
	 * 1E6);
	 * 
	 * for (int i = 0; i < coordinates.size(); i++) { GeoPoint points =
	 * (GeoPoint) coordinates.get(i); if (points.getLatitudeE6() != 0 &&
	 * points.getLongitudeE6() !=0) { minLatitude = (minLatitude >
	 * points.getLatitudeE6()) ? points.getLatitudeE6() : minLatitude;
	 * maxLatitude = (maxLatitude < points.getLatitudeE6()) ?
	 * points.getLatitudeE6() : maxLatitude; // Sets the minimum and maximum
	 * latitude so we can //set the min and max longitude to span and zoom
	 * minLongitude = (minLongitude > points.getLongitudeE6()) ?
	 * points.getLongitudeE6() : minLongitude; maxLongitude = (maxLongitude <
	 * points.getLongitudeE6()) ? points.getLongitudeE6() : maxLongitude;
	 * 
	 * } } GeoPoint regionPoints = new GeoPoint((maxLatitude +
	 * minLatitude)/2,(maxLongitude + minLongitude)/2); return regionPoints; }
	 */

	// This All below methods used to set the Application preferences

	public static final String PREFS_NAME = "PETPrefsFile";

	// this method will set the Application preferences as String value
	public static void setApplicationPreferences(Context context, String name,
			String value) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(name, value);
		// Commit the edits!
		editor.commit();
	}

	// this method will set the Application preferences as Boolean value
	public static void setApplicationPreferences(Context context, String name,
			Boolean value) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(name, value);
		// Commit the edits!
		editor.commit();
	}

	public static String getApplicationPreferences(Context context, String name) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(name, "nil");
	}

	public static Boolean getApplicationPreferencesBooleanValues(
			Context context, String name) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean(name, false);
	}

	// This Method will store the All detail of a Member in Shared Preferences
	public static final String MEMBER_PREFS_NAME = "MemberPrefsFile";

	public static void setMemberInfoInApplicationPreferences(
			Context appContext, HashMap<String, String> memberInfo) {
		SharedPreferences memberInfoPref = appContext.getSharedPreferences(
				MEMBER_PREFS_NAME, 0);
		SharedPreferences.Editor editor = memberInfoPref.edit();
		// First clear All Old data values of Member Info
		editor.clear();
		// fetch all keys from the HashMap and get the iterator and set their
		// values to MEMBER_PREFS_NAME file
		Iterator<String> memberInfoIterator = memberInfo.keySet().iterator();
		while (memberInfoIterator.hasNext()) {
			String key = (String) memberInfoIterator.next(); // get key
			String value = (String) memberInfo.get(key); // get value using the
															// key
			// System.out.println("key="+key+"  value="+value);
			editor.putString(key, value); // set to editor with key and value
		}
		editor.commit();
	}// set Method End

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getMemberInfoInApplicationPreferences(
			Context appContext) {
		HashMap<String, String> memberInfo = new HashMap<String, String>();
		// get Shared Preferences of Member Info Profile
		SharedPreferences memberInfoPref = appContext.getSharedPreferences(
				MEMBER_PREFS_NAME, 0);
		Map<String, String> allKeyValues = (Map<String, String>) memberInfoPref
				.getAll();
		// Now Put all values to hashMap key and return to caller class
		memberInfo.putAll(allKeyValues);
		return memberInfo;
	}

	// This Method will store the All detail of a Member in Shared Preferences
	public static final String MEMBER_COOKIES_PREFS_NAME = "MemberCookiesPrefsFile";

	public static void setMemberSesstionCookiesInApplicationPreferences(
			Context appContext, String name, String cookies) {
		SharedPreferences memberInfoPref = appContext.getSharedPreferences(
				MEMBER_COOKIES_PREFS_NAME, 0);
		SharedPreferences.Editor editor = memberInfoPref.edit();
		editor.putString(name, cookies);
		editor.commit();
	}// set Method End

	public static HashMap<String, String> getMemberSesstionCookiesFromApplicationPreferences(
			Context appContext) {
		HashMap<String, String> cookies = new HashMap<String, String>();
		// List<Cookie> listCookies = new List<Cookie>();
		// get Shared Preferences of Member Info Profile
		SharedPreferences memberInfoPref = appContext.getSharedPreferences(
				MEMBER_COOKIES_PREFS_NAME, 0);
		// Map<String, String> allKeyValues = (Map<String, String>)
		// memberInfoPref.getAll();
		Map<String, String> allKeyValues = (Map<String, String>) memberInfoPref
				.getAll();
		// Now Put all values to hashMap cookies variable and return to caller
		// class
		cookies.putAll(allKeyValues);
		return cookies;
	}

	// This method will show a Alert View to User About all Validations
	public static void ShowAlertWithMessage(Context context, int alerttitle,
			int locationvalidation) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(locationvalidation);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void ShowAlertInString(Context context, String alerttitle,
			String msg) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// This method will show a Alert View to User About all Validations
	public static void ShowAlertWithMessage(final Context context,
			int alerttitle, int locationvalidation,
			final boolean isFinishActivity) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(locationvalidation);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
				if (isFinishActivity == true) {
					if (context.getClass() == NextRegistrationActivity.class) {
						//((NextRegistrationActivity) context).popActivity();
					}

				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void ShowAlertandFinish(final Context context,
			int alerttitle, int locationvalidation,
			final boolean isFinishActivity) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(locationvalidation);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
				if (isFinishActivity == true) {

					/*if (context.getClass() == PetProfileActivity.class) {
						((PetProfileActivity) context).popActivity();
					} else if (context.getClass() == PetCareActivity.class) {
						((PetCareActivity) context).popActivity();
					}
					else if (context.getClass() ==SettingsActivity.class) {
						((SettingsActivity) context).popActivity();
					}
					else if (context.getClass() ==MedicalRecordsActivity.class) {
						((MedicalRecordsActivity) context).popActivity();
					}
*/
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// This method will show a Alert View to User and move to next activity on
	// ok button click
	public static void ShowAlertndMoveToNextActivity(final Context context,
			int alerttitle, int locationvalidation,
			final boolean isFinishActivity) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(locationvalidation);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
				if (isFinishActivity == true) {
					/*if (context.getClass()==UserSignInActivity.class) {
						((UserSignInActivity)context).movetonextActivity();
					}*/

				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void ShowAlertWithMessage(final Context context,
			String alerttitle, String locationvalidation,final boolean isFinishActivity) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(locationvalidation);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
				if (isFinishActivity == true) {
					/*if (context.getClass() == UserCreateAccountActivity.class) {
						((UserCreateAccountActivity) context).popActivity();
					}*/

				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// This method will show a Alert View to User About all Validations and uses
	// the string Message for Alert
	public static void ShowAlertWithMessage(final Context context,
			int alerttitle, String Message, final boolean isFinishActivity) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(Message);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
				if (isFinishActivity == true) {
					// if (context.getClass() ==
					// MemberSignInAndSignUpActivity.class) {
					// ((MemberSignInAndSignUpActivity)context).popActivity();
					// }

				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// This method will show a Alert View to User About all Validations as
	// String format
	public static void ShowAlertWithMessage(Context context, int alerttitle,
			String locationvalidation) {
		// Assign the alert builder , this can not be assign in Click events
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setMessage(locationvalidation);
		builder.setTitle(alerttitle);
		// Set behavior of negative button
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Cancel the dialog
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// Basic login method
	public static String BasicPostMethod(Context applicationContext,
			String url, ArrayList<NameValuePair> listParamsWithValues) {
		String response = "";
		DefaultHttpClient hc = new DefaultHttpClient();
		ResponseHandler<String> res = new BasicResponseHandler();
		HttpPost postMethod = new HttpPost(url);
		try {
			postMethod
					.setEntity(new UrlEncodedFormEntity(listParamsWithValues));
			response = hc.execute(postMethod, res);
			// System.out.println("response string "+response);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	// this method will simple call the service and get the result in simple
	// response string
	public static String GetServerDataFromURL(String url) {
		String results = "ERROR";
		try {
			HttpClient hc = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			HttpResponse rp = hc.execute(post);

			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				results = EntityUtils.toString(rp.getEntity());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	// Post Method to send data to server With Any URL and return the status
	// code
	public static HashMap<String, String> postDataWithURL(
			Context applicationContext, String url,
			ArrayList<NameValuePair> listParamsWithValues) {
		int resultCode = 0;

		HashMap<String, String> resultValue = new HashMap<String, String>();
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			DefaultHttpClient client = new DefaultHttpClient(httpParams);
			HttpPost request = new HttpPost(url);
			request.setEntity(new UrlEncodedFormEntity(listParamsWithValues));
			HttpResponse response = client.execute(request);
			String responseString = request(response);
			List<Cookie> cookies = client.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
				// System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					resultValue.put(cookies.get(i).getName(), cookies.get(i)
							.getValue());
				}
			}
			// System.out.println(responseString);
			if (responseString.toLowerCase().contains(
					"<successful>0</successful>")) {
				resultCode = 0;
			}
			if (responseString.toLowerCase().contains(
					"<successful>1</successful>")) {
				resultCode = 1;
			}
			if (responseString.toLowerCase().contains("<result>1</result>")) {
				resultCode = 2;
			}
			resultValue.put("resultCode", String.valueOf(resultCode));
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			// client.getConnectionManager().shutdown();
		} catch (Exception e) {
			// Some things goes Wrong
			e.printStackTrace();
		} finally {

		}
		return resultValue;
	}

	// Post Method to send data to server With Any URL
	public static Boolean postDataWithURL(String url, String fileUrl,
			ArrayList<NameValuePair> listParamsWithValues) {
		boolean result = false;
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost request = new HttpPost(url);

			if (fileUrl.equalsIgnoreCase("")) {
				request.setEntity(new UrlEncodedFormEntity(listParamsWithValues));
			} else {
				// System.out.println("file path "+fileUrl+" with actual path "+file);
			}
			// request.setEntity(new
			// ByteArrayEntity(listParamsWithValues.toString().getBytes("UTF8")));
			HttpResponse response = client.execute(request);
			String responseString = request(response);
			// System.out.println(responseString);
			if (responseString.toLowerCase().contains("1")) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			// Some things goes Wrong
			e.printStackTrace();
		}
		return result;
	}

	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

	// This method will check the email address
	public static boolean isValidEmailAddress(String aEmailAddress) {
		if (aEmailAddress == null)
			return false;
		boolean result = true;
		try {
			Pattern p = Pattern
					.compile("^([0-9a-zA-Z]+[-._+&amp;])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
			Matcher m = p.matcher(aEmailAddress);
			if (m.matches()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	// this method will return the absolute path of uri path
	public static String getRealPathFromUri(Activity activity, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(contentUri, proj, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	// this method will Download the Images one by one. and can be used in
	// Background Thread
	public static Drawable getImageFromURL(String photoDomain) {
		Drawable drawable = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(photoDomain.trim());
			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();
			drawable = Drawable.createFromStream(is, "src");
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return drawable;
	}

	public static void GetDataWithCookiesStore(String Url) {
		HttpClient httpclient = new DefaultHttpClient();

		// Create a local instance of cookie store
		CookieStore cookieStore = new BasicCookieStore();

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpGet httpget = new HttpGet(Url);

		// System.out.println("executing request " + httpget.getURI());

		// Pass local context as a parameter
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget, localContext);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();

		// System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: "
					+ entity.getContentLength());
		}
		List<Cookie> cookies = cookieStore.getCookies();
		// for (int i = 0; i < cookies.size(); i++) {
		// System.out.println("Local cookie: " + cookies.get(i));
		// }

		// Consume response content
		if (entity != null) {
			try {
				entity.consumeContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String responseString = request(response);
		// System.out.println(responseString);

		// System.out.println("----------------------------------------");

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
	}

	// This method will convert the String to InputStream
	public static InputStream getInputStreamFromString(String convertingString) {
		InputStream in = null;
		try {
			// Convert the String into InputStream
			in = new ByteArrayInputStream(convertingString.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}

	// This method will return the default stored Cookie Store
	public static CookieStore getSavedMemberCookieStored(
			Context applicationContext) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// Create a local instance of cookie store
		CookieStore cookiestore = httpclient.getCookieStore();
		HashMap<String, String> cookeisHm = Utility
				.getMemberSesstionCookiesFromApplicationPreferences(applicationContext);
		String cookieDate = cookeisHm.get("CookieExpiryDate");
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy");
		Date parsedCookieExpiryDate = null;
		try {
			parsedCookieExpiryDate = format.parse(cookieDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator<String> memberInfoIterator = cookeisHm.keySet().iterator();
		while (memberInfoIterator.hasNext()) {
			String key = (String) memberInfoIterator.next(); // get key
			String value = (String) cookeisHm.get(key); // get value using the
														// key
			// System.out.println("image "+key + " "+ value );
			BasicClientCookie cookie = new BasicClientCookie(key, value);
			cookie.setDomain("www.metaflavor.com");
			cookie.setVersion(0);
			cookie.setPath("/");
			cookie.setExpiryDate(parsedCookieExpiryDate);
			cookiestore.addCookie(cookie);
		}
		// Thu Jan 13 14:01:38 GMT+00:00 2011
		return cookiestore;
	}

	public static String postParamsAndfindJSON(String url,
			ArrayList<NameValuePair> params) {
		// TODO Auto-generated method stub
		JSONObject jObj = new JSONObject();
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// httpGet.setURI(new URI(url));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			int status = httpResponse.getStatusLine().getStatusCode();

			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}

			is.close();
			result = sb.toString();

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
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

	public static String findJSONFromUrl(String url) {
		// TODO Auto-generated method stub
		JSONObject jObj = new JSONObject();
		String result = "";

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

			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}

			is.close();
			result = sb.toString();
			System.out.println("result  in jsonparser class ........" + result);

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
	}


	public static Bitmap getBitmap(String url) {
		Bitmap b = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			try {
				b = BitmapFactory.decodeStream(new FlushedInputStream(is));
			} catch (OutOfMemoryError error) {
				error.printStackTrace();
				System.out.println("exception in get bitma putility");
			}

			bis.close();
			is.close();
			final int IMAGE_MAX_SIZE = 50;
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			int scale = 1;
			while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
				scale++;
			}
			if (scale > 1) {
				scale--;
				// scale to max possible inSampleSize that still yields an image
				// larger than target
				o = new BitmapFactory.Options();
				o.inSampleSize = scale;
				// b = BitmapFactory.decodeStream(in, null, o);

				// resize to desired dimensions
				int height = b.getHeight();
				int width = b.getWidth();

				double y = Math.sqrt(IMAGE_MAX_SIZE
						/ (((double) width) / height));
				double x = (y / height) * width;

				Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
						(int) y, true);
				b.recycle();
				b = scaledBitmap;

				System.gc();
			} else {
				// b = BitmapFactory.decodeStream(in);
			}

		} catch (OutOfMemoryError error) {
			error.printStackTrace();
			System.out.println("exception in get bitma putility");
		} catch (Exception e) {
			System.out.println("exception in get bitma putility");
			e.printStackTrace();
		}

		return b;
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}
	}
	public static Bitmap getbitmapInSmallSize(String imageurl) {
		String serverUrl1 =  imageurl;

		Bitmap myBitmap = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(serverUrl1);
		HttpResponse response;
		try {
			response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.outHeight = 30;
			options.outWidth = 30;
			options.inSampleSize = 2;
			myBitmap = BitmapFactory.decodeStream(is, null, options); // DecodeFile(is,
																		// options);
		myBitmap = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return myBitmap;
	}

	public static Bitmap getScaledBitmap(Context context, Bitmap bitmap) {

		// Uri uri = Uri.parse("file://"+path);
		InputStream in = null;
		try {
			final int IMAGE_MAX_SIZE = 50; // 1200000; // 1.2MP
			// in = context.getContentResolver().openInputStream(uri);

			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			// BitmapFactory.decodeStream(in, null, o);
			// in.close();

			int scale = 1;
			while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
				scale++;
			}
			// Log.d(TAG, "scale = " + scale + ", orig-width: " + o.outWidth +
			// ",orig-height: " + o.outHeight);

			Bitmap b = bitmap;
			// in = context.getContentResolver().openInputStream(uri);
			if (scale > 1) {
				scale--;
				// scale to max possible inSampleSize that still yields an image
				// larger than target
				o = new BitmapFactory.Options();
				o.inSampleSize = scale;
				// b = BitmapFactory.decodeStream(in, null, o);

				// resize to desired dimensions
				int height = b.getHeight();
				int width = b.getWidth();

				double y = Math.sqrt(IMAGE_MAX_SIZE
						/ (((double) width) / height));
				double x = (y / height) * width;

				Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
						(int) y, true);
				b.recycle();
				b = scaledBitmap;

				System.gc();
			} else {
				// b = BitmapFactory.decodeStream(in);
			}
			// in.close();

			// Log.d(TAG, "bitmap size - width: " +b.getWidth() + ", height: " +
			// b.getHeight());
			return b;
		} catch (OutOfMemoryError e) {
			// Log.e(TAG, e.getMessage(),e);
			return null;
		}
	}
}// final class ends here

