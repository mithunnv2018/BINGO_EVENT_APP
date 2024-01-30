package com.appbinggo.bingoappandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appbinggo.bingoappandroid.util.AlertDialogManager;
import com.appbinggo.bingoappandroid.util.ConnectionDetector;
import com.appbinggo.bingoappandroid.util.QuickUtilDroid;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterLoginPageActivity extends Activity {

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_twitter_login_page);
	// }

	// copied code from androidhive
	// http://www.androidhive.info/2012/09/android-twitter-oauth-connect-tutorial/#comment-864747808
	// Constants
	// Constants

	/**
	 * 
	 * Register your here app https://dev.twitter.com/apps/n... and get your
	 * 
	 * consumer key and secret
	 * 
	 * */

	static String TWITTER_CONSUMER_KEY = "AipzVDBHaTYSWqQMLoMDw";
	// "0PI1KQvbJxL8JCbDTzv5ig"; // place your

	// cosumer

	// key here

	static String TWITTER_CONSUMER_SECRET = "1IDojwF85XafYE0UuH0iZu9IlWItb354GGVHgwaMg";
	// "9K9gJVDNEMuzMB03Tg2aDERFnpyO6eDwDgVQCB9kzFA"; // place

	// your

	// consumer

	// secret

	// here

	// Preference Constants

	static String PREFERENCE_NAME = "twitter_oauth";

	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";

	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";

	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

	// Twitter oauth urls

	static final String URL_TWITTER_AUTH = "auth_url";

	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";

	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

	// Login button

	Button btnLoginTwitter;
	//
	// // Update status button
	//
	// Button btnUpdateStatus;
	//
	// // Logout button
	//
	// Button btnLogoutTwitter;
	//
	// // EditText for update
	//
	// EditText txtUpdate;
	//
	// // lbl update
	//
	// TextView lblUpdate;
	//
	// TextView lblUserName;

	// Progress dialog

	ProgressDialog pDialog;

	// Twitter

	private static Twitter twitter;

	private static RequestToken requestToken;

	// Shared Preferences

	private static SharedPreferences mSharedPreferences;

	// Internet Connection detector

	private ConnectionDetector cd;

	private Handler handler;

	// Alert Dialog Manager

	AlertDialogManager alert = new AlertDialogManager();

//	private ProgressDialog viewshowprogress;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_twitter_login_page);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		handler = new Handler();

		cd = new ConnectionDetector(getApplicationContext());
//		viewshowprogress=QuickUtilDroid.doLoadProgressBar2(this);

		// Check if Internet present

		if (!cd.isConnectingToInternet()) {

			// Internet Connection is not present

			alert.showAlertDialog(TwitterLoginPageActivity.this,

			"Internet Connection Error",

			"Please connect to working Internet connection", false);

			// stop executing code by return

			return;

		}

		// Check if twitter keys are set

		if (TWITTER_CONSUMER_KEY.trim().length() == 0

		|| TWITTER_CONSUMER_SECRET.trim().length() == 0) {

			// Internet Connection is not present

			alert.showAlertDialog(TwitterLoginPageActivity.this,
					"Twitter oAuth tokens",

					"Please set your twitter oauth tokens first!", false);

			// stop executing code by return

			return;

		}

		// All UI elements

		btnLoginTwitter = (Button) findViewById(R.id.buttonTwitterLogin);

		/*
		 * done btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
		 * 
		 * btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
		 * 
		 * txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
		 * 
		 * lblUpdate = (TextView) findViewById(R.id.lblUpdate);
		 * 
		 * lblUserName = (TextView) findViewById(R.id.lblUserName);
		 */
		// Shared Preferences

		mSharedPreferences = getApplicationContext().getSharedPreferences(

		"MyPref", 0);

		/**
		 * 
		 * Twitter login button click event will call loginToTwitter() function
		 * 
		 * */

		btnLoginTwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Call login twitter function

				loginToTwitter();

			}

		});

		/**
		 * 
		 * Button click event to Update Status, will call updateTwitterStatus()
		 * 
		 * function
		 * 
		 * */

		/*
		 * done btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override
		 * 
		 * public void onClick(View v) {
		 * 
		 * // Call update status function
		 * 
		 * // Get the status from EditText
		 * 
		 * String status = txtUpdate.getText().toString();
		 * 
		 * // Check for blank text
		 * 
		 * if (status.trim().length() > 0) {
		 * 
		 * // update status
		 * 
		 * new updateTwitterStatus().execute(status);
		 * 
		 * } else {
		 * 
		 * // EditText is empty
		 * 
		 * Toast.makeText(getApplicationContext(),
		 * 
		 * "Please enter status message", Toast.LENGTH_SHORT)
		 * 
		 * .show();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * });
		 */

		/**
		 * 
		 * Button click event for logout from twitter
		 * 
		 * */

		/*
		 * done btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override
		 * 
		 * public void onClick(View arg0) {
		 * 
		 * // Call logout twitter function
		 * 
		 * logoutFromTwitter();
		 * 
		 * }
		 * 
		 * });
		 */

		/**
		 * 
		 * This if conditions is tested once is redirected from twitter page.
		 * 
		 * Parse the uri to get oAuth Verifier
		 * 
		 * */

		if (!isTwitterLoggedInAlready()) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					Uri uri = getIntent().getData();

					if (uri != null

					&& uri.toString().startsWith(TWITTER_CALLBACK_URL)) {

						// oAuth verifier

						String verifier = uri

						.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

						try {

							// Get the access token

							final AccessToken accessToken = twitter

							.getOAuthAccessToken(requestToken, verifier);

							// Shared Preferences

							Editor e = mSharedPreferences.edit();

							// After getting access token, access token secret

							// store them in application preferences

							e.putString(PREF_KEY_OAUTH_TOKEN,

							accessToken.getToken());

							e.putString(PREF_KEY_OAUTH_SECRET,

							accessToken.getTokenSecret());

							// Store login status - true

							e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);

							e.commit(); // save changes

							Log.e("Twitter OAuth Token",

							"> " + accessToken.getToken() + "secret token> "
									+ accessToken.getTokenSecret());

							handler.post(new Runnable() {

								@Override
								public void run() {

									Toast.makeText(
											TwitterLoginPageActivity.this,
											"token=" + accessToken.getToken(),
											Toast.LENGTH_LONG).show();
									// Hide login button

									// btnLoginTwitter.setVisibility(View.GONE);

									/*
									 * done // Show Update Twitter
									 * 
									 * lblUpdate.setVisibility(View.VISIBLE);
									 * 
									 * txtUpdate.setVisibility(View.VISIBLE);
									 * 
									 * btnUpdateStatus.setVisibility(View.VISIBLE
									 * );
									 * 
									 * btnLogoutTwitter.setVisibility(View.VISIBLE
									 * );
									 */

								}

							});

							// Getting user details from twitter

							// For now i am getting his name only

							long userID = accessToken.getUserId();

							User user = twitter.showUser(userID);

							final String username = user.getName();

							handler.post(new Runnable() {

								@Override
								public void run() {

									// Displaying in xml ui

									btnLoginTwitter.setText(Html
											.fromHtml("Welcome "

											+ username + ""));

								}

							});

						} catch (Exception e) {

							// Check log for login errors

							Log.e("Twitter Login Error", "> " + e.getMessage());

						}

					}

				}

			}).start();

		}

		// done COMMENT BELOW else wil keep calling login page
		// loginToTwitter();

	}

	/**
	 * 
	 * Function to login twitter
	 * 
	 * */

	private void loginToTwitter() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				// Check if already logged in

				if (!isTwitterLoggedInAlready()) {

					ConfigurationBuilder builder = new ConfigurationBuilder();

					builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);

					builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

					Configuration configuration = builder.build();

					TwitterFactory factory = new TwitterFactory(configuration);

					twitter = factory.getInstance();

					try {

						requestToken = twitter
								.getOAuthRequestToken(TWITTER_CALLBACK_URL);

						handler.post(new Runnable() {

							@Override
							public void run() {

								TwitterLoginPageActivity.this
										.startActivityForResult(

										new Intent(

										Intent.ACTION_VIEW,

										Uri.parse(requestToken

										.getAuthenticationURL())),

										101);

							}

						});

					} catch (TwitterException e) {

						e.printStackTrace();

					}

				} else {

					handler.post(new Runnable() {

						@Override
						public void run() {

							// user already logged into twitter

							Toast.makeText(getApplicationContext(),

							"Already Logged into twitter",

							Toast.LENGTH_LONG).show();
							doNavigateToEventsActivity2();

						}

					});

				}

			}

		}).start();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);

	}

	private void doNavigateToEventsActivity2() {

		String token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
		String secrettoken = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET,"");
		String userootid = QuickUtilDroid.doGetFromPreferences(this, QuickUtilDroid.KEY_INTENT_USERROOTID);
		new TaskCallwebserviceForRetrieveTwitterEvents().execute(token,secrettoken,userootid);
		Intent in = new Intent(getApplicationContext(),
				HomePage_Activity.class);
//		in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE, response);
		in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT, "twitter");
		startActivity(in);
	}

	class TaskCallwebserviceForRetrieveTwitterEvents extends
			AsyncTask<String, Boolean, String> {

		String accesstoken2="";
		String secretaccesstoken2="";
		Integer userootid=null;
		@Override
		protected String doInBackground(String... params) {

			publishProgress(true);

			/*
			 * TableLayout tl = (TableLayout) findViewById(R.id.maintable); if
			 * (tl.getChildCount() > 0) { tl.removeAllViewsInLayout(); }
			 */
			accesstoken2 = params[0];
			secretaccesstoken2 = params[1];
			userootid=Integer.parseInt(params[2]);
			if(accesstoken2.isEmpty() || secretaccesstoken2.isEmpty())
			{
				Toast.makeText(TwitterLoginPageActivity.this,
						"Token or user id is null", Toast.LENGTH_LONG).show();
				System.err.println("Token or user id is null");
				return null;
			}
			String event = MyWebService.
					callwebserviceforupdatetwittereventList(
					accesstoken2, secretaccesstoken2,userootid);

			// CallWebservicecaddy.callwebserviceforCreateGameId(userid,
			// courseid)
			// .callwebserviceforCheckingUserInvitation(param1);

			return event;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {

			if (values[0] == true) {
//				if (viewshowprogress != null) {
//					viewshowprogress.show();
//				}

				Toast.makeText(TwitterLoginPageActivity.this,
						"Loading Wbservice", Toast.LENGTH_LONG).show();
			}
			if (values[0] == false) {
//				if (viewshowprogress != null && viewshowprogress.isShowing()) {
//					viewshowprogress.dismiss();
//				}

				Toast.makeText(TwitterLoginPageActivity.this,
						"Done Loading Webservice", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled(String result) {
			// viewshowprogress.setVisibility(View.INVISIBLE);
			System.out
					.println("FaceBookLoginPageActivity.TaskCallwebserviceForRetrievefacebookEvents.onCancelled()");
			System.err.println("Hi mith TaskSync is canelled");
			Toast.makeText(
					TwitterLoginPageActivity.this.getApplicationContext(),
					"Hi mith TaskSync is canelled", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(String response) {

			System.out.println("Got response of facebook events rows="
					+ response);

			// Boolean didload = doLoadJSONSearchResult(response, "", "");//
			// param1,
			// Boolean didload = doLoadInviteScreen(response); // param2);

			if (response != null) {

//				try {
//					JSONObject obj = new JSONObject(response);
					
					//done comment as on 12 sept 2013 because it is already saved in facebooklogin
/*					String userrootid=obj.optString("userrootid");
					QuickUtilDroid.doAddToPreferences(TwitterLoginPageActivity.this, QuickUtilDroid.KEY_INTENT_USERROOTID, userrootid);
*/					
					//done comment below is redundant so delete it later
/*					JSONArray jsonArray = obj.getJSONArray("twitterevents");
					String result = "";
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject row = (JSONObject) jsonArray.get(i);

						result += "name=" + row.optString("eventname", "NULL")
								+ " , location="
								+ row.optString("locationname", "no loc");
						result += "\n";
					}
					System.out.println("No of twitter events="
							+ jsonArray.length());
					// EditText welcome = (EditText)
					// findViewById(R.id.editTextFBUserName);
					// welcome.setText("facebook events ->" + result);
					// "No of facebook events="+jsonArray.length());
*/
//				} catch (JSONException e) {
//					System.out
//							.println("TwitterLoginPageActivity.TaskCallwebserviceForRetrieveTwitterEvents.onPostExecute()");
//					System.err.println("Sorry error " + e.getMessage());
//					e.printStackTrace();
//				}
				publishProgress(false);
				/*Intent in = new Intent(getApplicationContext(),
						HomePage_Activity.class);
				in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE, response);
				in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT, "twitter");
				startActivity(in);*/

			}

			Toast.makeText(getApplicationContext(),
					"done twitter events result", Toast.LENGTH_LONG).show();
			System.out.println("hi mith done twitter events result didload");

		}
	}

	/**
	 * 
	 * Function to update status
	 * 
	 * */

	class updateTwitterStatus extends AsyncTask<String, String, String> {

		/**
		 * 
		 * Before starting background thread Show Progress Dialog
		 * 
		 * */

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			pDialog = new ProgressDialog(TwitterLoginPageActivity.this);

			pDialog.setMessage("Updating to twitter...");

			pDialog.setIndeterminate(false);

			pDialog.setCancelable(false);

			pDialog.show();

		}

		/**
		 * 
		 * getting Places JSON
		 * 
		 * */

		protected String doInBackground(String... args) {

			Log.d("Tweet Text", "> " + args[0]);

			String status = args[0];

			try {

				ConfigurationBuilder builder = new ConfigurationBuilder();

				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);

				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

				// Access Token

				String access_token = mSharedPreferences.getString(

				PREF_KEY_OAUTH_TOKEN, "");

				// Access Token Secret

				String access_token_secret = mSharedPreferences.getString(

				PREF_KEY_OAUTH_SECRET, "");

				AccessToken accessToken = new AccessToken(access_token,

				access_token_secret);

				Twitter twitter = new TwitterFactory(builder.build())

				.getInstance(accessToken);

				// Update status

				twitter4j.Status response = twitter.updateStatus(status);

				Log.d("Status", "> " + response.getText());

			} catch (TwitterException e) {

				// Error in updating status

				Log.d("Twitter Update Error", e.getMessage());

			}

			return null;

		}

		/**
		 * 
		 * After completing background task Dismiss the progress dialog and show
		 * 
		 * the data in UI Always use runOnUiThread(new Runnable()) to update UI
		 * 
		 * from background thread, otherwise you will get error
		 * 
		 * **/

		protected void onPostExecute(String file_url) {

			// dismiss the dialog after getting all products

			pDialog.dismiss();

			// updating UI from Background Thread

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					Toast.makeText(getApplicationContext(),

					"Status tweeted successfully", Toast.LENGTH_SHORT)

					.show();

					// Clearing EditText field

					/*
					 * done txtUpdate.setText("");
					 */

				}

			});

		}

	}

	/**
	 * 
	 * Function to logout from twitter It will just clear the application shared
	 * 
	 * preferences
	 * 
	 * */

	private void logoutFromTwitter() {

		// Clear the shared preferences

		Editor e = mSharedPreferences.edit();

		e.remove(PREF_KEY_OAUTH_TOKEN);

		e.remove(PREF_KEY_OAUTH_SECRET);

		e.remove(PREF_KEY_TWITTER_LOGIN);

		e.commit();

		// After this take the appropriate action

		// I am showing the hiding/showing buttons again

		// You might not needed this code

		/*
		 * done btnLogoutTwitter.setVisibility(View.GONE);
		 * 
		 * btnUpdateStatus.setVisibility(View.GONE);
		 * 
		 * txtUpdate.setVisibility(View.GONE);
		 * 
		 * lblUpdate.setVisibility(View.GONE);
		 * 
		 * lblUserName.setText("");
		 * 
		 * lblUserName.setVisibility(View.GONE);
		 */
		btnLoginTwitter.setVisibility(View.VISIBLE);

	}

	/**
	 * 
	 * Check user already logged in your application using twitter Login flag is
	 * 
	 * fetched from Shared Preferences
	 * 
	 * */

	private boolean isTwitterLoggedInAlready() {

		// return twitter login status from Shared Preferences

		// return false;
		return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

	}

	protected void onResume() {

		super.onResume();

	}

}
