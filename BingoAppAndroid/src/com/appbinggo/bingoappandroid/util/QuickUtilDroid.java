package com.appbinggo.bingoappandroid.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appbinggo.bingoappandroid.EventListPageActivity;
import com.appbinggo.bingoappandroid.FaceBookLoginPageActivity;
import com.appbinggo.bingoappandroid.MyWebService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuickUtilDroid {

	public static final String KEY_INTENT_EVENTRESPONSE = "KEY_INTENT_EVENTRESPONSE";

	public static final String KEY_INTENT_EVENTDETAILS = "KEY_INTENT_EVENTDETAILS";

	public static final String KEY_INTENT_FACEBOOK_ACCESSTOKEN = "KEY_INTENT_ACCESSTOKEN";

	public static final String KEY_INTENT_FACEBOOK_USERID = "KEY_INTENT_USERID";

	public static final String KEY_INTENT_TYPEOFACCOUNT = "KEY_INTENT_TYPEOFACCOUNT";

	public static final String KEY_INTENT_USERROOTID = "KEY_INTENT_USERROOTID";

	public static final String locAction = "com.appbinggo.bingoappandroid.util.LOCATION_UP_RECEIVED";

	private static Context context;
	private static SharedPreferences defaultSharedPreferences;

	/**
	 * To retrieve screen size of device
	 * 
	 * @param act
	 * @return
	 */
	public static int[] doGetScreenSize(Activity act) {
		Display defaultDisplay = act.getWindowManager().getDefaultDisplay();
		int width = defaultDisplay.getWidth();
		int height = defaultDisplay.getHeight();
		return new int[] { width, height };
	}

	public static Float convertMetersToYards(Float param) {
		float constant = 1.09361f;
		float result = 0f;
		result = param * constant;
		return result;
	}

	/**
	 * Retireve style from styles.xml
	 * 
	 * @param resid
	 * @param attrs
	 * @param a
	 * @return
	 */
	public static TypedArray doGetStyle(int resid, int[] attrs, Activity a) {
		return a.obtainStyledAttributes(resid, attrs);
	}

	/**
	 * remove lati , longi from string eg(19,-19)
	 * 
	 * @param cood
	 * @return
	 */
	public static Location getParseLocationFromString(String cood) {
		if (cood != null && cood.contains(",")) {
			String[] split = cood.split(",");
			Double[] splitdouble = new Double[2];
			splitdouble[0] = Location.convert(split[0]);
			splitdouble[1] = Location.convert(split[1]);
			Location loc = new Location("dummylocation");
			loc.setLatitude(splitdouble[0]);
			loc.setLongitude(splitdouble[1]);
			return loc;
		}
		return null;
	}

	public static void doAddToPreferences(Context context, String key,
			String value) {
		if (defaultSharedPreferences == null) {
			defaultSharedPreferences = getGCMPreferences(context);
		}
		// SharedPreferences
		// defaultSharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
		// context.getSharedPreferences(MainActivity.class.getName(),
		// Context.MODE_PRIVATE);
		// SharedPreferences defaultSharedPreferences = PreferenceManager
		// .getDefaultSharedPreferences(new
		// MainActivity().getApplicationContext());
		Editor edit = defaultSharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();

	}

	public static String doGetFromPreferences(Context context, String key) {
		if (defaultSharedPreferences == null) {
			defaultSharedPreferences = getGCMPreferences(context);
		}
		// SharedPreferences defaultSharedPreferences = PreferenceManager
		// .getDefaultSharedPreferences(new
		// MainActivity().getApplicationContext());
		// SharedPreferences
		// defaultSharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
		// context.getSharedPreferences(MainActivity.class.getName(),
		// Context.MODE_PRIVATE);
		String value = defaultSharedPreferences.getString(key, "");
		return value;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private static SharedPreferences getGCMPreferences(Context context) {
		if (defaultSharedPreferences == null) {
			defaultSharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(context);
		}
		return defaultSharedPreferences;
		// return PreferenceManager.getDefaultSharedPreferences(context);
		// context.getSharedPreferences(MainActivity.class.getName(),
		// Context.MODE_PRIVATE);
		// PreferenceManager.getDefaultSharedPreferences(new
		// MainActivity().getApplicationContext());
		// return
		// context.getSharedPreferences(MainActivity.class.getSimpleName(),
		// Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public static ProgressDialog doLoadProgressBar2(Context context) {

		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Retrieving data...");
		progressDialog.setTitle("Please wait");
		progressDialog.setIndeterminate(true);

		return progressDialog;
	}

	public static void doClearTableLayout(TableLayout tl) {
		if (tl.getChildCount() > 0) {
			int childCount = tl.getChildCount();
			tl.removeViews(0, childCount);

		}
	}

	public static void doClearLinearLayout(LinearLayout tl) {
		if (tl.getChildCount() > 0) {
			int childCount = tl.getChildCount();
			tl.removeViews(0, childCount);

		}
	}

	public static void doClearRelativeLayout(RelativeLayout tl) {
		if (tl.getChildCount() > 0) {
			int childCount = tl.getChildCount();
			tl.removeViews(0, childCount);

		}
	}

	public static void doDownloadImage(String url, ImageView img) {
		new TaskCallDownloadImage().execute(url, img);
	}

	/**
	 * To download all images and save it in bitmap to HAshmap
	 * 
	 * @author mithun
	 * 
	 */
	public static class TaskCallDownloadImage extends
			AsyncTask<Object, Boolean, Bitmap> {
		String param1;
		Context paramcontext;
		View parent;
		String url;
		ImageView img;

		@Override
		protected Bitmap doInBackground(Object... params) {

			publishProgress(true);

			String url = "";// mapsurlscoregamecoursedetails.get(key);

			url = (String) params[0];
			img = (ImageView) params[1];
			Bitmap downloadFullFromUrl = QuickUtilDroid
					.downloadFullFromUrl(url);

			//
			return downloadFullFromUrl;
			// downloadFullFromUrl(url);
			// return "done";
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {

			if (values[0] == true) {
				// viewshowprogress.setVisibility(View.VISIBLE);
				// MainActivity.this.doLoadProgressBar2.show();
				// }
				if (values[0] == false) {
					// viewshowprogress.setVisibility(View.INVISIBLE);
					// if (MainActivity.this.doLoadProgressBar2.isShowing()) {
					// MainActivity.this.doLoadProgressBar2.dismiss();
				}
			}
		}

		@Override
		protected void onCancelled(Bitmap result) {

		}

		@Override
		protected void onPostExecute(Bitmap response) {

			System.out.println("Got response of download  rows=" + response);
			img.setImageBitmap(response);
			publishProgress(false);

		}
	}

	@Deprecated
	/*
	 * public static void setImageFromURL(String url, ImageView img) { if (url
	 * == null || url.isEmpty() || img == null) { return;
	 * 
	 * } try { new TaskCallDownloadImage().execute(url,img); //
	 * img.setImageBitmap(downloadFullFromUrl(url)); //
	 * img.setImageDrawable(Drawable.createFromStream((InputStream)new //
	 * URL(url).getContent(), "src")); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */
	public static Bitmap downloadFullFromUrl(String imageFullURL) {
		Bitmap bm = null;
		try {
			if (imageFullURL == null || imageFullURL.isEmpty()) {
				return null;
			}
			imageFullURL = imageFullURL.replace("\\", "");
			if (imageFullURL.contains("http:") == false
					&& imageFullURL.contains("https:") == false) {
				imageFullURL = "https://" + imageFullURL;
			}
			System.out.println("Hi MITHUNNNN url is " + imageFullURL);
			URL url = new URL(imageFullURL);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			bm = BitmapFactory.decodeByteArray(baf.toByteArray(), 0,
					baf.toByteArray().length);

		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
		}
		return bm;
	}

	public static void doClearALLPreferences(Context context2) {
		System.out.println("QuickUtilDroid.doClearALlPreferences()");

	}
	
	/**
	 * TO redirect to Event detaisl page
	 * @param ctx
	 * @param tok
	 * @param userid
	 * @param userrootid 
	 * @param eventtype 
	 */
	public static void doOpenFaceBookEventPage(Context ctx,String tok,String userid, String userrootid, String eventtype)
	{
		AsyncTask<Object, Boolean, String> execute = new QuickUtilDroid() .new TaskCallwebserviceForRetrievefacebookEvents();
		execute.execute(ctx,tok,userid,userrootid,eventtype);
	}

	/**
	 * Call to redirect to event detail as per latitude and longitude
	 * @param ctx
	 * @param tok
	 * @param userid
	 * @param userrootid
	 * @param eventtype
	 * @param location
	 */
	public static void doOpenFaceBookLatiLongiEventPage(Context ctx,String tok,String userid, String userrootid, String eventtype,Location location)
	{
		AsyncTask<Object, Boolean, String> execute = new QuickUtilDroid() .new TaskCallwebserviceForRetrievefacebookEvents();
		execute.execute(ctx,tok,userid,userrootid,eventtype,location);
	}

	
	class TaskCallwebserviceForRetrievefacebookEvents extends
			AsyncTask<Object, Boolean, String> {
		String accesstoken2, userid2;
		String userrootid=null;
		Context ctx;
		String eventype="";
		String latitude="",longitude="";
		@Override
		protected String doInBackground(Object... params) {

			publishProgress(true);

			/*
			 * TableLayout tl = (TableLayout) findViewById(R.id.maintable); if
			 * (tl.getChildCount() > 0) { tl.removeAllViewsInLayout(); }
			 */
			ctx=(Context) params[0];
			accesstoken2 = (String) params[1];
			userid2 = (String) params[2];
			userrootid=(String) params[3];
			eventype=(String) params[4];
			if(params.length==6)
			{
				//todo uncomment below line for production use as on 09 oct 2013
				Location p=(Location) params[5];
				latitude=String.valueOf(p.getLatitude());
				longitude=String.valueOf(p.getLongitude());
//				latitude="18.991403";
//				longitude="72.814379";
				
			}
			String event="";
			if(eventype.equals("facebook"))
			{
				event = MyWebService.callwebserviceforeventList(userrootid);
			}
			else if(eventype.equals("admin"))
			{
				event=MyWebService.callwebserviceforadmineventList();
			}
			else if(eventype.equals("facebooklatilongi"))
			{
				event=MyWebService.callwebservicefordoRetrieveFacebookeventsWithLatiLongi(accesstoken2, userid2, latitude, longitude);
			}
//					accesstoken2, userid2);

			// CallWebservicecaddy.callwebserviceforCreateGameId(userid,
			// courseid)
			// .callwebserviceforCheckingUserInvitation(param1);

			return event;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {

			if (values[0] == true) {
				/*if (viewshowprogress != null) {
					viewshowprogress.show();
				}

				Toast.makeText(FaceBookLoginPageActivity.this,
						"Loading Wbservice", Toast.LENGTH_LONG).show();
		*/
				}
			if (values[0] == false) {
				/*if (viewshowprogress != null && viewshowprogress.isShowing()) {
					viewshowprogress.dismiss();
				}

				Toast.makeText(FaceBookLoginPageActivity.this,
						"Done Loading Webservice", Toast.LENGTH_LONG).show();*/
			}
		}

		@Override
		protected void onCancelled(String result) {
			// viewshowprogress.setVisibility(View.INVISIBLE);
			System.out
					.println("FaceBookLoginPageActivity.TaskCallwebserviceForRetrievefacebookEvents.onCancelled()");
			System.err.println("Hi mith TaskSync is canelled");
/*			Toast.makeText(
					FaceBookLoginPageActivity.this.getApplicationContext(),
					"Hi mith TaskSync is canelled", Toast.LENGTH_LONG).show();*/
		}

		@Override
		protected void onPostExecute(String response) {

			System.out.println("Got response of facebook events rows="
					+ response);

			// Boolean didload = doLoadJSONSearchResult(response, "", "");//
			// param1,
			// Boolean didload = doLoadInviteScreen(response); // param2);

			if (response != null) {

				try {
					JSONObject obj = new JSONObject(response);
					JSONArray jsonArray = obj.getJSONArray("fbevent");
					String result = "";
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject row = (JSONObject) jsonArray.get(i);

						result += "name=" + row.optString("name", "NULL")
								+ " , location="
								+ row.optString("location", "no loc");
						result += "\n";
					}
					System.out.println("No of facebook events="
							+ jsonArray.length());
					// EditText welcome = (EditText)
					// findViewById(R.id.editTextFBUserName);
					// welcome.setText("facebook events ->" + result);
					// "No of facebook events="+jsonArray.length());

				} catch (JSONException e) {
					System.out
							.println("FaceBookLoginPageActivity.TaskCallwebserviceForRetrievefacebookEvents.onPostExecute()");
					System.err.println("Sorry error " + e.getMessage());
					e.printStackTrace();
				}
				publishProgress(false);
				Intent in = new Intent(ctx,
						EventListPageActivity.class);
				in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE, response);
				in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT, eventype);
				ctx.startActivity(in);

			}

			Toast.makeText(ctx,
					"done facebook events result", Toast.LENGTH_LONG).show();
			System.out.println("hi mith done facebook events result didload from homepage");

		}
	}

	public static void doPrintAllUserDetails(Context registration) {

		// System.out.println("FIRST_NAME"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_FIRST_NAME)
		// + ""
		// + "LAST_NAME"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_LAST_NAME)
		// + ""
		// + "CURRENT_EMAILID"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_CURRENT_EMAILID)
		// + ""
		// + "CURRENT_HANDICAP"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_CURRENT_HANDICAP)
		// + ""
		// + "USERNAME"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_USERNAME)
		// + ""
		// + "CURRENTGENDER"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_CURRENTGENDER)
		// + ""
		// + "USERID"
		// + QuickUtilDroid.doGetFromPreferences(registration,
		// QuickUtilDroid.KEY_INTENT_USERID));

	}

	public static String doGetSocialTypeImage(String socialtype) {
		System.out.println("QuickUtilDroid.doGetSocialTypeImage()");
		try {
			String url="file:///android_res/drawable/";
			if(socialtype==null || socialtype.isEmpty())
			{
				url+="intro_facebook.png";
				return url;
			}
			if(socialtype.equals("facebook"))
			{
				url+="intro_facebook.png";
			}
			else if(socialtype.equals("twitter"))
			{
				url+="intro_twitter.png";
			}
			else if(socialtype.equals("instagram"))
			{
				url+="intro_instagram.png";
			}
			return url;
		} catch (Exception e) {
			System.err.println("Sorrry error QuickUtilDroid.doGetSocialTypeImage()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
