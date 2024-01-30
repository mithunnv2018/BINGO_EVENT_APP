package com.appbinggo.bingoappandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class LoginHomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_home);
		ImageView fb= (ImageView) findViewById(R.id.ImageView1FB);
		fb.setClickable(true);
		
		fb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						FaceBookLoginPageActivity.class);
				startActivity(in);
			}
		});
		
		ImageView tw=(ImageView) findViewById(R.id.imageViewFB);
		tw.setClickable(true);
		tw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Intent in = new Intent(getApplicationContext(),
						TwitterLoginPageActivity.class);
				startActivity(in);
			}
		});
		
		ImageView instagram=(ImageView)findViewById(R.id.imageViewEvent3);
		instagram.setClickable(true);
		instagram.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Intent in = new Intent(getApplicationContext(),
						InstagramLoginPageActivity.class);
				startActivity(in);
			}
		});
		
		Button btncontinue=(Button) findViewById(R.id.buttonContinue2);
		btncontinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						HomePage_Activity.class);
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_home, menu);
		return true;
	}

}
