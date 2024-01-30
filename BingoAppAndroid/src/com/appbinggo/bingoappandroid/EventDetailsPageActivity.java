package com.appbinggo.bingoappandroid;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appbinggo.bingoappandroid.util.AlertDialogManager;
import com.appbinggo.bingoappandroid.util.ConnectionDetector;
import com.appbinggo.bingoappandroid.util.QuickUtilDroid;
import com.appbinggo.bingoappandroid.util.URLImageParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailsPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details_page);
		String response = this.getIntent().getStringExtra(
				QuickUtilDroid.KEY_INTENT_EVENTDETAILS);
		String typeofac = this.getIntent().getStringExtra(
				QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT);

		doLoadEventDetails(response, typeofac);

	}

	private void doLoadEventDetails(String response, String typeofac) {
		System.out.println("EventDetailsPageActivity.doLoadEventDetails()");
		try {
			if (response == null || response.isEmpty() == true) {
				System.err.println("No value in response of event details");
				return;
			}
			JSONObject obj = new JSONObject(response);
			if (obj.length() == 0) {
				System.err
						.println("No value in json response of event details");
				return;
			}

			String desc = obj.optString("description");
			String pic = obj.optString("pic");// _big");
			String loc = obj.optString("location");
			String eid = obj.optString("eid");
			ImageView img = (ImageView) findViewById(R.id.imageViewEvent);
			// TextView tv = (TextView)
			// findViewById(R.id.textView1EventDetails);
			// TextView tvplace = (TextView)
			// findViewById(R.id.textView1EventPlace);

			QuickUtilDroid.doDownloadImage(pic, img);
			// tv.setText(desc);
			// tvplace.setText("Location:"+loc);

			new TaskRetrieveCommentsofEvents().execute(QuickUtilDroid
					.doGetFromPreferences(this,
							QuickUtilDroid.KEY_INTENT_FACEBOOK_ACCESSTOKEN),
					QuickUtilDroid.doGetFromPreferences(this,
							QuickUtilDroid.KEY_INTENT_FACEBOOK_USERID), eid,
					desc, loc, QuickUtilDroid.doGetFromPreferences(this,
							QuickUtilDroid.KEY_INTENT_USERROOTID), typeofac);

		} catch (Exception e) {
			System.out
					.println("Sorry error EventDetailsPageActivity.doLoadEventDetails()"
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details_page, menu);
		return true;
	}

	class TaskRetrieveCommentsofEvents extends
			AsyncTask<Object, Boolean, String> {
		Context paramcontext;
		String tokenaccess;
		String userid;
		String eventid;
		private String desc;
		private String loc;
		String userrootid;
		String eventype = null;
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

		@Override
		protected String doInBackground(Object... params) {

			publishProgress(true);

			tokenaccess = (String) params[0];
			userid = (String) params[1];
			eventid = (String) params[2];
			desc = (String) params[3];
			loc = (String) params[4];
			userrootid=(String) params[5];
			eventype=(String) params[6];
			if (!cd.isConnectingToInternet()) {
				
				return null;
			}
			String response ="";
			
			if(eventype.equals("facebooklatilongi"))
			{
				response=MyWebService.callwebservicefordoRetrieveFacebookeventcommentsListWithLatiLongi(tokenaccess,userid, eventid, userrootid);
			}
			else
			{
				response = MyWebService
					.callwebserviceforeventcommentsList(eventid,userrootid,eventype);
			}
			// tokenaccess, userid, eventid);
			return response;
			
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {

			if (values[0] == true) {
				// viewshowprogress.setVisibility(View.VISIBLE);
				// MainActivity.this.doLoadProgressBar2.show();

				// viewshowprogress=QuickUtilDroid.doLoadProgressBar2(this);

				// Check if Internet present
				AlertDialogManager alert = new AlertDialogManager();
				if (!cd.isConnectingToInternet()) {

					// Internet Connection is not present

					alert.showAlertDialog(EventDetailsPageActivity.this,

					"Internet Connection Error",

					"Please connect to working Internet connection", false);

					// stop executing code by return

					return;

				}

			}
			if (values[0] == false) {
				// viewshowprogress.setVisibility(View.INVISIBLE);
				// if (MainActivity.this.doLoadProgressBar2.isShowing()) {
				// MainActivity.this.doLoadProgressBar2.dismiss();
			}

		}

		@Override
		protected void onCancelled(String result) {

		}

		@Override
		protected void onPostExecute(String response) {

			System.out.println("Got response of download  rows=" + response);
			doLoadEventComments(response, desc, loc);
			publishProgress(false);

		}
	}

	public void doLoadEventComments(String response, String desc, String loc) {
		System.out.println("EventDetailsPageActivity.doLoadEventComments()");
		// TextView comments = (TextView)
		// findViewById(R.id.textView1EventComments);
		try {
			if (response == null || response.isEmpty()) {
				return;
			}

			JSONObject root = new JSONObject(response);
			if (root.length() == 0) {
				System.err.println("No content in event comments json");
				return;
			}
			String htmltext = "<html><body>";
			htmltext += "<p>" + desc + "</p>";
			htmltext += "<p>Location:" + loc + "</p>";

			htmltext += "<h3>List of Comments..</h3>";
			// htmltext+="<table border='1'>";
			JSONArray arr = root.getJSONArray("fbeventcomments");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject row = arr.getJSONObject(i);
				
				// JSONObject userprofile = row.getJSONObject("userprofile");
				String postedby = row.optString("username");// userprofile.getString("username");
				String picofpostedby = row.optString("pic");// userprofile.getString("pic");
				String socialtype = row.optString("socialtype");
				String localimage = "";
				localimage = QuickUtilDroid.doGetSocialTypeImage(socialtype);

				if(picofpostedby.trim().isEmpty() || localimage.trim().isEmpty())
				{
					continue;
				}
				// htmltext+="<tr ><td><img  src='"+picofpostedby+"' /></td></tr>";
				// htmltext+="<tr><td><font color='red'>"+postedby+"</font></td></tr>";
				// htmltext+="<tr><td>";
				htmltext += "<div style='float: left'><img height='42' width='42' src='"
						+ picofpostedby
						+ "' /><font color='red'>"
						+ postedby
						+ "</font></div> <div style='float: right'><img height='42' width='42'  src='"
						+ localimage + "' /></div><br/><br/>";
				/*
				 * htmltext += "<p><font color='red'>" + postedby +
				 * "</font></p>";
				 */// htmltext+="<p align='right'><img  src='"+localimage+"' /></p>";

				String msg = row.getString("message");
				htmltext += "<p align='left'>";
				htmltext += "" + msg + "</p>";
				htmltext += "<hr/>";
				// htmltext+="</td></tr>";

			}
			// htmltext+="</table>";
			// URLImageParser parser=new URLImageParser(comments,this);
			htmltext += "</body></html>";
			System.out.println("Comment=" + htmltext);
			// Spanned htmlSpan = Html.fromHtml(htmltext, parser, null);

			// comments.setText(htmlSpan);

			WebView wv = (WebView) findViewById(R.id.webView1);
			// wv.loadData(htmltext, "text/html", "UTF-8");
			wv.loadDataWithBaseURL("file:///android_res/drawable/", htmltext,
					"text/html", "UTF-8", null);

		} catch (Exception e) {
			System.out
					.println("Sorry error here EventDetailsPageActivity.doLoadEventComments()"
							+ e.getMessage());
			e.printStackTrace();
		}
	}
}
