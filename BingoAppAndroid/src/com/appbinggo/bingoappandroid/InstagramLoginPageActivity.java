package com.appbinggo.bingoappandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appbinggo.bingoappandroid.instagram.InstaImpl;
import com.appbinggo.bingoappandroid.util.QuickUtilDroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class InstagramLoginPageActivity extends Activity implements
		OnClickListener {

	private Button mButton;
	private InstaImpl mInstaImpl;
	private Context mContext;
	private ResponseListener mResponseListener;
	private String tokenisthis;
	private String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intagram_login_page);
		mContext = this;
		mButton = (Button) findViewById(R.id.buttonInstagramLogin);
		mButton.setOnClickListener(this);
		mResponseListener = new ResponseListener();
		tokenisthis = this.getIntent().getStringExtra(
				ResponseListener.EXTRA_ACCESS_TOKEN);
		userid = this.getIntent().getStringExtra(
				ResponseListener.EXTRA_ACCESS_USERID);

		if (tokenisthis != null) {
			String name = this.getIntent().getStringExtra(
					ResponseListener.EXTRA_NAME);

			mButton.setText("See Events for " + name);
			System.out.println("HI mith tokenid " + tokenisthis + " name="
					+ userid);

			String userrootid = QuickUtilDroid.doGetFromPreferences(this, QuickUtilDroid.KEY_INTENT_USERROOTID);
			new TaskCallwebserviceForRetrieveInstagramEvents().execute(
					tokenisthis, userid,userrootid);
			Intent in = new Intent(getApplicationContext(),
					HomePage_Activity.class);
			// in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE, response);
			in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT, "instagram");
			startActivity(in);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intagram_login_page, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {

		try {
			if (tokenisthis == null) {
				mInstaImpl = new InstaImpl(this);
				mInstaImpl.setAuthAuthenticationListener(new AuthListener());
			}
		} catch (Exception e) {
			System.out
					.println("Sorry eror InstagramLoginPageActivity.onClick()"
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.varundroid.instademo.responselistener");
		filter.addCategory("com.varundroid.instademo");
		registerReceiver(mResponseListener, filter);
	}

	@Override
	protected void onPause() {
		unregisterReceiver(mResponseListener);
		super.onPause();
	}

	class TaskCallwebserviceForRetrieveInstagramEvents extends
			AsyncTask<String, Boolean, String> {

		String token = "";
		String userid = "";
		Integer userrootid=null;
		@Override
		protected String doInBackground(String... params) {

			

			if (params[2]==null) {
				publishProgress(true);
				return null;
			}
			/*
			 * TableLayout tl = (TableLayout) findViewById(R.id.maintable); if
			 * (tl.getChildCount() > 0) { tl.removeAllViewsInLayout(); }
			 */
			token = params[0];
			userid = params[1];
			userrootid=Integer.parseInt(params[2]);
			if (token.isEmpty() || userid.isEmpty()) {
//				Toast.makeText(InstagramLoginPageActivity.this,
//						"Token or user id is null", Toast.LENGTH_LONG).show();
				System.err.println("Token or user id is null");
				return null;
			}
			
			String event = MyWebService
					.callwebserviceforupdateinstagrameventList(token, userid,userrootid);

			// CallWebservicecaddy.callwebserviceforCreateGameId(userid,
			// courseid)
			// .callwebserviceforCheckingUserInvitation(param1);

			return event;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {

			if (values[0] == true) {
				Toast.makeText(InstagramLoginPageActivity.this,
						"Please first login from facebook", Toast.LENGTH_LONG).show();
				System.err.println("userrotid id is null");
						}
			if (values[0] == false) {
				// if (viewshowprogress != null && viewshowprogress.isShowing())
				// {
				// viewshowprogress.dismiss();
				// }
				//
				// Toast.makeText(TwitterLoginPageActivity.this,
				// "Done Loading Webservice", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled(String result) {
			// viewshowprogress.setVisibility(View.INVISIBLE);
			System.out
					.println("InstagramLoginPageActivity.TaskCallwebserviceForRetrieveInstagramEvents.onCancelled()");
			System.err.println("Hi mith TaskSync is canelled");
			// Toast.makeText(
			// TwitterLoginPageActivity.this.getApplicationContext(),
			// "Hi mith TaskSync is canelled", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(String response) {

			System.out.println("Got response of instagram events rows="
					+ response);

			// Boolean didload = doLoadJSONSearchResult(response, "", "");//
			// param1,
			// Boolean didload = doLoadInviteScreen(response); // param2);

			if (response != null) {

				
				/*try {
					JSONObject obj;
					obj = new JSONObject(response);

					//done comment as on 12 sept 2013 because it is already saved in facebooklogin
					String userrootid = obj.optString("userrootid");
					QuickUtilDroid.doAddToPreferences(
							InstagramLoginPageActivity.this,
							QuickUtilDroid.KEY_INTENT_USERROOTID, userrootid);
				} catch (JSONException e) {
					System.out
							.println("Sorry error InstagramLoginPageActivity.TaskCallwebserviceForRetrieveInstagramEvents.onPostExecute()"+e.getMessage());
					e.printStackTrace();
				}*/
				publishProgress(false);
				/*
				 * Intent in = new Intent(getApplicationContext(),
				 * HomePage_Activity.class);
				 * in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE,
				 * response);
				 * in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT,
				 * "instagram"); startActivity(in);
				 */

			}

			Toast.makeText(getApplicationContext(),
					"done instagram events result", Toast.LENGTH_LONG).show();
			System.out.println("hi mith done instagram events result didload");

		}
	}

	public class AuthListener implements InstaImpl.AuthAuthenticationListener {
		@Override
		public void onSuccess() {
			Toast.makeText(InstagramLoginPageActivity.this,
					"Instagram Authorization Successful", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onFail(String error) {
			Toast.makeText(InstagramLoginPageActivity.this,
					"Authorization Failed", Toast.LENGTH_SHORT).show();
		}
	}

	public class ResponseListener extends BroadcastReceiver {

		public static final String ACTION_RESPONSE = "com.varundroid.instademo.responselistener";
		public static final String EXTRA_NAME = "90293d69-2eae-4ccd-b36c-a8d0c4c1bec6";
		public static final String EXTRA_ACCESS_TOKEN = "bed6838a-65b0-44c9-ab91-ea404aa9eefc";
		public static final String EXTRA_ACCESS_USERID = "EXTRA_ACCESS_USERID";

		@Override
		public void onReceive(Context context, Intent intent) {
			mInstaImpl.dismissDialog();
			mInstaImpl.instaLoginDialog.dismiss();
			final Bundle extras = intent.getExtras();
			String name = extras.getString(EXTRA_NAME);
			String accessToken = extras.getString(EXTRA_ACCESS_TOKEN);
			final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					mContext);
			alertDialog.setTitle("Your Details");
			alertDialog.setMessage("Name - " + name + ", Access Token - "
					+ accessToken);
			alertDialog.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

							Intent in = new Intent(getApplicationContext(),
									InstagramLoginPageActivity.class);
							in.putExtras(extras);
							finish();
							startActivity(in);
						}
					});

			alertDialog.show();
		}
	}
}
