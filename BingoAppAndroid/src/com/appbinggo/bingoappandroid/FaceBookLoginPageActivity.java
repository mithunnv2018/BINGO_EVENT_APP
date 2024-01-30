package com.appbinggo.bingoappandroid;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appbinggo.bingoappandroid.util.QuickUtilDroid;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Session.OpenRequest;
import com.facebook.model.GraphUser;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FaceBookLoginPageActivity extends Activity {

	private ProgressDialog viewshowprogress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_book_login_page);
		viewshowprogress=QuickUtilDroid.doLoadProgressBar2(this);
		Button login = (Button) findViewById(R.id.buttonFacebookLogin);

		login.setOnClickListener(new MyLoginListener());
		doLoginDefault();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.face_book_login_page, menu);
		return true;
	}

	@Override
	public void onResume() {
		Session session = Session.getActiveSession();
		if (session != null)
			if (session.isOpened()) {
				Toast.makeText(this, session.getAccessToken(),
						Toast.LENGTH_LONG).show();
				// accessToken = (TextView) findViewById(R.id.accessToken);
				// accessToken.setText(session.getAccessToken());
				System.out.println("----------------------"
						+ session.getAccessToken() + "---------------------");

			}
		super.onResume();
	}

	private void doLoginDefault() {
		System.out.println("FaceBookLoginPageActivity.doLoginDefault()");

		Session currentSession = Session.getActiveSession();
		
		// start Facebook Login
		if (currentSession == null || currentSession.getState().isClosed()) {
			Session session = new Session.Builder(
					FaceBookLoginPageActivity.this).build();
			Session.setActiveSession(session);
			currentSession = session;
		}
		if (!currentSession.isOpened()) {
			// Ask for username and password
			OpenRequest op = new Session.OpenRequest(
					FaceBookLoginPageActivity.this);

			op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
			op.setCallback(null);

			List<String> permissions = new ArrayList<String>();
			permissions.add("publish_stream");
			permissions.add("user_likes");
			permissions.add("email");
			permissions.add("user_birthday");
			permissions.add("user_events");
			permissions.add("read_stream");
			permissions.add("publish_actions");
			permissions.add("user_photos");
			permissions.add("create_note");
			permissions.add("photo_upload");
			permissions.add("video_upload");
			permissions.add("status_update");
			permissions.add("share_item");			
			
			

			op.setPermissions(permissions);

			Session session = new Session.Builder(
					FaceBookLoginPageActivity.this).build();
			Session.setActiveSession(session);
			session.openForPublish(op);
			Button welcome = (Button) findViewById(R.id.buttonFacebookLogin);
			System.err
					.println("Hi mith session is " + session.getAccessToken());
			welcome.setText("See events");
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	private class MyLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			System.out
					.println("FaceBookLoginPageActivity.MyLoginListener.onClick()");
			Session activeSession = Session.getActiveSession();

			System.out.println("Active session part 1"
					+ activeSession.toString());

			Session openActiveSession = Session.openActiveSession(
					FaceBookLoginPageActivity.this, true,
					new Session.StatusCallback() {

						// callback when session changes state
						@Override
						public void call(final Session session,
								SessionState state, Exception exception) {
							if (session.isOpened()) {

								// make request to the /me API
								Request.executeMeRequestAsync(session,
										new Request.GraphUserCallback() {

											// callback after Graph API response
											// with user
											// object
											@Override
											public void onCompleted(
													GraphUser user,
													Response response) {
												if (user != null) {
//													EditText welcome = (EditText) findViewById(R.id.editTextFBUserName);
//													welcome.setText(user.getName()+ "!");
													System.out.println("Session token "
															+ session
																	.getAccessToken());
													String userid = user
															.getId();
													QuickUtilDroid.doAddToPreferences(FaceBookLoginPageActivity.this, QuickUtilDroid.KEY_INTENT_FACEBOOK_ACCESSTOKEN, session.getAccessToken());
													QuickUtilDroid.doAddToPreferences(FaceBookLoginPageActivity.this, QuickUtilDroid.KEY_INTENT_FACEBOOK_USERID, userid);
													
													new TaskCallwebserviceForRetrievefacebookEvents().execute(
															session.getAccessToken(),
															userid);
													Intent in = new Intent(getApplicationContext(),
															HomePage_Activity.class);
//													in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE,response);
													in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT, "facebook");
													
													startActivity(in);
													
												}
											}
										});
							}
						}
					});
			System.out.println("Hi mith after login events line"
					+ openActiveSession.toString());

		}
	}

	class TaskCallwebserviceForRetrievefacebookEvents extends
			AsyncTask<String, Boolean, String> {
		String accesstoken2, userid2;

		@Override
		protected String doInBackground(String... params) {

			publishProgress(true);

			/*
			 * TableLayout tl = (TableLayout) findViewById(R.id.maintable); if
			 * (tl.getChildCount() > 0) { tl.removeAllViewsInLayout(); }
			 */
			accesstoken2 = params[0];
			userid2 = params[1];
			if(accesstoken2.isEmpty() || userid2.isEmpty())
			{
				Toast.makeText(FaceBookLoginPageActivity.this,
						"Token or user id is null", Toast.LENGTH_LONG).show();
				System.err.println("Token or user id is null");
				return null;
			}
			String event = MyWebService.callwebserviceforupdatefacebookeventList(
					accesstoken2, userid2);

			// CallWebservicecaddy.callwebserviceforCreateGameId(userid,
			// courseid)
			// .callwebserviceforCheckingUserInvitation(param1);

			return event;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {

			if (values[0] == true) {
//				if(viewshowprogress!=null)
//				{
//					viewshowprogress.show();
//				}
				
				Toast.makeText(FaceBookLoginPageActivity.this, "Loading Wbservice",
						Toast.LENGTH_LONG).show();
			}
			if (values[0] == false) {
//				if (viewshowprogress != null
//						&& viewshowprogress.isShowing()) {
//					viewshowprogress.dismiss();
//				}

				Toast.makeText(FaceBookLoginPageActivity.this, "Done Loading Webservice",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled(String result) {
			// viewshowprogress.setVisibility(View.INVISIBLE);
			System.out
					.println("FaceBookLoginPageActivity.TaskCallwebserviceForRetrievefacebookEvents.onCancelled()");
			System.err.println("Hi mith TaskSync is canelled");
			Toast.makeText(FaceBookLoginPageActivity.this.getApplicationContext(),
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

				try {
					JSONObject obj = new JSONObject(response);
					String userrootid = obj.optString("userrootid");
					QuickUtilDroid.doAddToPreferences(FaceBookLoginPageActivity.this, QuickUtilDroid.KEY_INTENT_USERROOTID, userrootid);
					
					/*JSONArray jsonArray = obj.getJSONArray("fbevent");
					String result = "";
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject row = (JSONObject) jsonArray.get(i);

						result += "name=" + row.optString("name", "NULL")
								+ " , location="
								+ row.optString("location", "no loc");
						result += "\n";
					}
					System.out.println("No of facebook events="
							+ jsonArray.length());*/
//					EditText welcome = (EditText) findViewById(R.id.editTextFBUserName);
//					welcome.setText("facebook events ->" + result);
					// "No of facebook events="+jsonArray.length());

				} catch (JSONException e) {
					System.out
							.println("FaceBookLoginPageActivity.TaskCallwebserviceForRetrievefacebookEvents.onPostExecute()");
					System.err.println("Sorry error " + e.getMessage());
					e.printStackTrace();
				}
				publishProgress(false);	
				/*Intent in = new Intent(getApplicationContext(),
						HomePage_Activity.class);
				in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE,response);
				in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT, "facebook");
				startActivity(in);*/
				

			}
			

			Toast.makeText(getApplicationContext(),
					"done facebook events result", Toast.LENGTH_LONG).show();
			System.out.println("hi mith done facebook events result didload");

		}
	}
}
