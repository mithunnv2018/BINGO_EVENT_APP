package com.appbinggo.bingoappandroid;

import com.appbinggo.bingoappandroid.util.AlertDialogManager;
import com.appbinggo.bingoappandroid.util.QuickUtilDroid;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class HomePage_Activity extends Activity {

	private boolean startedgps;
	private BroadcastReceiver locReceiver;
	protected Location updateWithNewLocation=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		ImageView fbbutton=(ImageView) findViewById(R.id.imageViewFB);
		fbbutton.setClickable(true);
		fbbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String fbaccesstoken = QuickUtilDroid.doGetFromPreferences(HomePage_Activity.this, QuickUtilDroid.KEY_INTENT_FACEBOOK_ACCESSTOKEN);
				String fbuserid = QuickUtilDroid.doGetFromPreferences(HomePage_Activity.this, QuickUtilDroid.KEY_INTENT_FACEBOOK_USERID);
				String userrootid = QuickUtilDroid.doGetFromPreferences(HomePage_Activity.this, QuickUtilDroid.KEY_INTENT_USERROOTID);
				
				if(userrootid!=null && !userrootid.isEmpty() && fbaccesstoken!=null && fbuserid!=null && !fbuserid.isEmpty() && !fbaccesstoken.isEmpty())
				{
//					QuickUtilDroid.doOpenFaceBookEventPage(HomePage_Activity.this, fbaccesstoken, fbuserid,userrootid,"facebook");
					if(updateWithNewLocation==null)
					{
						Toast.makeText(HomePage_Activity.this, "Sorry Waiting for GPS Coordinates", Toast.LENGTH_LONG).show();
						//todo uncomment below line as on 9 oct 2013
//						return;
					}
					QuickUtilDroid.doOpenFaceBookLatiLongiEventPage(HomePage_Activity.this, fbaccesstoken, fbuserid,userrootid,"facebooklatilongi",updateWithNewLocation);
					
				}
				else
				{
					AlertDialogManager alert=new AlertDialogManager();
					alert.showAlertDialog(HomePage_Activity.this, "Not Logged In", "You have not logged in to Facebook yet.Please do so ", true	);
					
				}
				
			}
		});
		
		// /////start broadcast receiver here///////////////////////////////
				startedgps = false;
				locReceiver = new BroadcastReceiver() {

					@Override
					public void onReceive(Context context, Intent intent) {
						String key = LocationManager.KEY_LOCATION_CHANGED;
						Location location = (Location) intent.getExtras().get(key);
//						if (startedgps == false) {
							updateWithNewLocation=location;
							Toast.makeText(HomePage_Activity.this, "Receving GPS"+location.toString(), Toast.LENGTH_LONG).show();
							
//							unregisterReceiver(locReceiver);
//						}

					}
				};
				IntentFilter locIntentFilter = new IntentFilter(
						QuickUtilDroid.locAction);
				registerReceiver(locReceiver, locIntentFilter);

				// //////////////end broadcast recevier/////////////////////////

		
		ImageView view1=(ImageView) findViewById(R.id.imageViewEvent1);
		ImageView view2=(ImageView) findViewById(R.id.imageViewEvent2);
		ImageView view3=(ImageView) findViewById(R.id.imageViewEvent3);
		ImageView view4=(ImageView) findViewById(R.id.imageViewEvent4);
		ImageView view5=(ImageView) findViewById(R.id.ImageViewEvent5);
		ImageView view6=(ImageView) findViewById(R.id.imageViewEvent6);
		
		ShowEventPage listener=new ShowEventPage();
		
		view1.setOnClickListener(listener);
		view2.setOnClickListener(listener);
		view3.setOnClickListener(listener);
		view4.setOnClickListener(listener);
		view5.setOnClickListener(listener);
		view6.setOnClickListener(listener);
		
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {

			 
			unregisterReceiver(locReceiver);

		} catch (Exception e) {
			System.out.println("HomePage_Activity.onStop()");
			e.printStackTrace();
		}
	}
	class ShowEventPage implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			
			String fbaccesstoken = QuickUtilDroid.doGetFromPreferences(HomePage_Activity.this, QuickUtilDroid.KEY_INTENT_FACEBOOK_ACCESSTOKEN);
			String fbuserid = QuickUtilDroid.doGetFromPreferences(HomePage_Activity.this, QuickUtilDroid.KEY_INTENT_FACEBOOK_USERID);
			String userrootid = QuickUtilDroid.doGetFromPreferences(HomePage_Activity.this, QuickUtilDroid.KEY_INTENT_USERROOTID);
			
			if(userrootid!=null && !userrootid.isEmpty() && fbaccesstoken!=null && fbuserid!=null && !fbuserid.isEmpty() && !fbaccesstoken.isEmpty())
			{
				QuickUtilDroid.doOpenFaceBookEventPage(HomePage_Activity.this, fbaccesstoken, fbuserid,userrootid,"admin");
				
			}
			else
			{
				AlertDialogManager alert=new AlertDialogManager();
				alert.showAlertDialog(HomePage_Activity.this, "Not Logged In", "You have not logged in to Facebook yet.Please do so ", true	);
				
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page_, menu);
		return true;
	}

	
}
