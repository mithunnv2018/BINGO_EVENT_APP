package com.appbinggo.bingoappandroid;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appbinggo.bingoappandroid.util.QuickUtilDroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class EventListPageActivity extends Activity {

	private String typeofac;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list_page);
		Intent intent = this.getIntent();
		String response2 = intent.getStringExtra(QuickUtilDroid.KEY_INTENT_EVENTRESPONSE);
		typeofac = intent.getStringExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT);
		if(typeofac.equals("facebook") || typeofac.equals("admin") || typeofac.equals("facebooklatilongi"))
		{
			doLoadFirstEvents(response2);
		}
		else if(typeofac.equals("twitter"))
		{
			doLoadTwitterFirstEvents(response2);
		}		
			
		
	}
	
	public void doLoadFirstEvents(String response)
	{
		
		System.out.println("EventListPageActivity.doLoadFirstEvents()");
		
		try 
		{
			if(response==null || response.isEmpty())
			{
				
				return;
			}
//			RelativeLayout parent= (RelativeLayout) findViewById(R.id.parentrelativelayout);
			TableLayout parent= (TableLayout) findViewById(R.id.parenttablelayout);
			
//			QuickUtilDroid.doClearRelativeLayout(parent);
			JSONObject root=new JSONObject(response);
			JSONArray array = root.getJSONArray("fbevent");
			boolean isnewrow=true;
			TableLayout oldlayoutrow=new TableLayout(this);
//			TableRow  oldrow=new TableRow(this);
			TableRow  row1=new TableRow(this);
			for(int i=0;i<array.length();i++)
			{
				final JSONObject row = array.getJSONObject(i);
				String name = row.optString("name");
				String location = row.optString("location");
				String pic = row.optString("pic");
//				TableLayout layoutrow=new TableLayout(this);
//				layoutrow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
				
				if(i%3==0)
				{
					isnewrow=true;
				}
				
				
				
				
				ImageView img=new ImageView(this);
				img.setId(i+1);
				img.setLayoutParams(new LayoutParams(100, 100));
				TextView tv=new TextView(this);
				tv.setLayoutParams(new LayoutParams(100, 100));
				tv.setText(name);
//				Bitmap downloadFullFromUrl = QuickUtilDroid.downloadFullFromUrl(pic);
//				img.setImageBitmap(downloadFullFromUrl);
				
//				if(isnewrow==true)
//				{
//					oldlayoutrow=layoutrow;
//					oldrow=row1;
//				}
//				
//				oldrow.addView(img);
//				oldlayoutrow.addView(oldrow);
				if(isnewrow==true)
				{
					row1=new TableRow(this);
					isnewrow=false;
				}
				row1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				row1.addView(img);
				row1.addView(tv);
//				parent.addView(row1);

				new TaskCallDownloadImage2().execute(pic,img);

				if((i+1)%3==0 || (i+1)==array.length())
				{
					parent.addView(row1);//oldlayoutrow);
				}
				
				tv.setClickable(true);
				tv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						Intent in = new Intent(getApplicationContext(),
//								EventDetailsPageActivity.class);
//						
//						in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTDETAILS,row.toString());
//						startActivity(in);
						doNavigate(row);
					}
				});
				img.setClickable(true);
				img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						Intent in = new Intent(getApplicationContext(),
//								EventDetailsPageActivity.class);
//						
//						in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTDETAILS,row.toString());
//						startActivity(in);
						doNavigate(row);
					}
				});
			}
			int childCount = parent.getChildCount();
			System.out.println("Hi mith total rows added to event list"+childCount);
			
		} catch (Exception e) {
			System.out.println("Sorry error EventListPageActivity.doLoadFirstEvents()"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void doLoadTwitterFirstEvents(String response)
	{
		
		System.out.println("EventListPageActivity.doLoadTwitterFirstEvents()");
		try 
		{
			if(response==null || response.isEmpty())
			{
				
				return;
			}
//			RelativeLayout parent= (RelativeLayout) findViewById(R.id.parentrelativelayout);
			TableLayout parent= (TableLayout) findViewById(R.id.parenttablelayout);
			
//			QuickUtilDroid.doClearRelativeLayout(parent);
			JSONObject root=new JSONObject(response);
			JSONArray array = root.getJSONArray("twitterevents");
			boolean isnewrow=true;
			TableLayout oldlayoutrow=new TableLayout(this);
//			TableRow  oldrow=new TableRow(this);
			TableRow  row1=new TableRow(this);
			for(int i=0;i<array.length();i++)
			{
				final JSONObject row = array.getJSONObject(i);
				String name = row.optString("eventname");
				String location = row.optString("locationname");
				String pic = row.optString("userphoto");
//				TableLayout layoutrow=new TableLayout(this);
//				layoutrow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
				
				if(i%3==0)
				{
					isnewrow=true;
				}
				
				
				
				
				ImageView img=new ImageView(this);
				img.setId(i+1);
				img.setLayoutParams(new LayoutParams(100, 100));
				TextView tv=new TextView(this);
				tv.setLayoutParams(new LayoutParams(100, 100));
				tv.setText(name);
//				Bitmap downloadFullFromUrl = QuickUtilDroid.downloadFullFromUrl(pic);
//				img.setImageBitmap(downloadFullFromUrl);
				
//				if(isnewrow==true)
//				{
//					oldlayoutrow=layoutrow;
//					oldrow=row1;
//				}
//				
//				oldrow.addView(img);
//				oldlayoutrow.addView(oldrow);
				if(isnewrow==true)
				{
					row1=new TableRow(this);
					isnewrow=false;
				}
				row1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				row1.addView(img);
				row1.addView(tv);
//				parent.addView(row1);

				new TaskCallDownloadImage2().execute(pic,img);

				if((i+1)%3==0 || (i+1)==array.length())
				{
					parent.addView(row1);//oldlayoutrow);
				}
				
				tv.setClickable(true);
				tv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						Intent in = new Intent(getApplicationContext(),
//								EventDetailsPageActivity.class);
//						
//						in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTDETAILS,row.toString());
//						startActivity(in);
						/*doNavigate(row);*/
					}
				});
				img.setClickable(true);
				img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						Intent in = new Intent(getApplicationContext(),
//								EventDetailsPageActivity.class);
//						
//						in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTDETAILS,row.toString());
//						startActivity(in);
						/*doNavigate(row);*/
					}
				});
			}
			int childCount = parent.getChildCount();
			System.out.println("Hi mith total rows added to event list"+childCount);
			
		} catch (Exception e) {
			System.out.println("Sorry error EventListPageActivity.doLoadFirstEvents()"+e.getMessage());
			e.printStackTrace();
		}
	}

	public void doNavigate(JSONObject row)
	{
		Intent in = new Intent(getApplicationContext(),
				EventDetailsPageActivity.class);
		
		in.putExtra(QuickUtilDroid.KEY_INTENT_EVENTDETAILS,row.toString());
		in.putExtra(QuickUtilDroid.KEY_INTENT_TYPEOFACCOUNT,typeofac);
		
		startActivity(in); 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list_page, menu);
		return true;
	}
	/**
	 * To download all images and save it in bitmap to HAshmap 
	 * @author mithun
	 *
	 */
	public  class TaskCallDownloadImage2 extends
			AsyncTask<Object, Boolean, Bitmap> {
		String param1;
		Context paramcontext;
		View parent;
		String url;
		ImageView img;

		@Override
		protected Bitmap doInBackground(Object... params) {

			publishProgress(true);

	 				String url = "";//mapsurlscoregamecoursedetails.get(key);
			
			url = (String) params[0];
			img = (ImageView) params[1];
			Bitmap downloadFullFromUrl = QuickUtilDroid.downloadFullFromUrl(url);
	  		
//			
			return downloadFullFromUrl;
//					downloadFullFromUrl(url);
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

}
