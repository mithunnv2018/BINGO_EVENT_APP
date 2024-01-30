package com.appbinggo.bingoappandroid;



 

import com.appbinggo.bingoappandroid.util.QuickUtilDroid;

import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btnshowlogin=(Button) findViewById(R.id.btnshowlogin);

		btnshowlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						LoginHomeActivity.class);
				startActivity(in);
				
			}
		});
		doRegisterGps();
	}

	public void doRegisterGps() {
		// /////start location api//////////////////
		// LocationManager locationManager;
		String svcName = Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) getSystemService(svcName);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS is Enabled in your devide",
					Toast.LENGTH_SHORT).show();
		} else {
			System.out.println("Hi Waiting to activate gps");
			showGPSDisabledAlertToUser();
		}
		System.out.println("Hi mithun done acitivating gps");
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// COARSE);//ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		String provider = locationManager.getBestProvider(criteria, true);

		int flags = PendingIntent.FLAG_UPDATE_CURRENT;
		int resultcode = 0;
		int mintime = 0;
		int mindistance = 0;

		Intent intent = new Intent(QuickUtilDroid.locAction);
		PendingIntent broadcast = PendingIntent.getBroadcast(this, resultcode,
				intent, flags);

		locationManager.requestLocationUpdates(provider, mintime, mindistance,
				broadcast);

		// /////end location api
	}

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder
				.setMessage(
						"GPS is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Goto Settings Page To Enable GPS",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);

							}
						});

		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
