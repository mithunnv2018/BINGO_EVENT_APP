package com.bingoapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingoapp.db.TblEventcommentsDetails;
import com.bingoapp.db.TblUserMaster;
import com.bingoapp.db.TblUserownMaster;
import com.bingoapp.util.QuickUtil;

import twitter4j.GeoLocation;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class LoginAndEventsTwitter {

	static String TWITTER_CONSUMER_KEY = "AipzVDBHaTYSWqQMLoMDw";

	static String TWITTER_CONSUMER_SECRET = "1IDojwF85XafYE0UuH0iZu9IlWItb354GGVHgwaMg";

	private String loggedinuserid;

	public String doCallFromThreadTwitterUpdate(final String token, final String tokensecret,final Integer userrootid)
	{
		System.out
				.println("LoginAndEventsTwitter.doCallFromThreadTwitterUpdate()");
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				 doRetrieveTwitterEvents( token, tokensecret,userrootid);
			}
		});
		t.start();
		return "done update";
	}
	
	public String doRetrieveTwitterEvents(String token, String tokensecret,Integer userrootid) {
		System.out.println("LoginAndEventsTwitter.doRetrieveTwitterEvents()");

		try {
			TwitterFactory factory = new TwitterFactory();
			AccessToken accestoken = new AccessToken(token, tokensecret);
			Twitter twitter = factory.getInstance();
			twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY,
					TWITTER_CONSUMER_SECRET);
			twitter.setOAuthAccessToken(accestoken);
			loggedinuserid=accestoken.getUserId()+"";
			
			///////done as on 10 sept 2013/////////
			User user2 = twitter.showUser(accestoken.getUserId());
			

			String screenName2 = user2.getScreenName();

			byte[] userimageurl2 = QuickUtil.doDownloadIamgeFromURL(user2
					.getProfileImageURL());


			String userusername2 = user2.getName();
			String imagetype2="jpg";
			if(user2.getProfileImageURL().endsWith(".png"))
			{
				imagetype2="png";
			}
			
			TblUserownMaster userown=new TblUserownMaster();
			userown.setUserownName(screenName2);
			userown.setUserownPhoto(userimageurl2);
			userown.setUserownUsername(userusername2);
			userown.setUserownImageContentType(imagetype2);
			
			
			QuickUtil.doSaveTblUserrootMaster(userown, loggedinuserid, "twitter",String.valueOf(userrootid));
		///////end done as on 10 sept 2013///////// 			
			JSONObject root = new JSONObject();
			
			root.put("userrootid",userrootid);
			
			// First param of Paging() is the page number, second is the number
			// per page (this is capped around 200 I think.
			Paging paging = new Paging(1, 100);

			ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
			JSONArray arrayevents = new JSONArray();

			int i=0;
			for (Status status : homeTimeline) {
				JSONObject row = new JSONObject();
				Double lati=null;
				Double longi=null;
				if(i>=1000)
				{
					break;
				}
				
				row.put("eventname", status.getText());

				if (status.getPlace() != null) {
					row.put("locationname", status.getPlace().getFullName());
				}
				String latilongi = "";
				if (status.getGeoLocation() != null) {
					latilongi = status.getGeoLocation().getLatitude() + ","
							+ status.getGeoLocation().getLongitude() + "";
					
				}
				row.put("latilongi", latilongi);
				String miniProfileImageURL = status.getUser()
						.getMiniProfileImageURL();
				row.put("userphoto", miniProfileImageURL);
				String username = status.getUser().getName();
				row.put("username", username);
				row.put("createddate", status.getCreatedAt().toString());
				// row.put("comments", value)
				// status.getURLEntities()[0].
				// MediaEntity[] mediaEntities = status.getMediaEntities();
				// med
				// status.getUser().
				String post_id = status.getId() + "";
				String permalink = status.getSource();
				String socialtype = "twitter";
				String message = status.getText();
				String eventid = "";
				User user = status.getUser();
				String userid = user.getId() + "";
				Date created_time = status.getCreatedAt();
				/*
				 * TblUserMaster tblUserMaster=new TblUserMaster();
				 * tblUserMaster.setUserAddress(user.getLocation());
				 * tblUserMaster.setUserEmailid("");
				 * tblUserMaster.setUserId(userid);
				 * tblUserMaster.setUserLatilongi
				 * (status.getGeoLocation().getLatitude
				 * ()+","+status.getGeoLocation().getLongitude());
				 * tblUserMaster.
				 * setUserLocation(status.getPlace().getFullName());
				 * tblUserMaster.setUserName(user.getScreenName());
				 * tblUserMaster.setUserPhoneno("");
				 * tblUserMaster.setUserPhoto(QuickUtil
				 * .doDownloadIamgeFromURL(user.getProfileImageURL()));
				 * tblUserMaster.setUserPincode("");
				 * tblUserMaster.setUserSocialtype("twitter");
				 * tblUserMaster.setUserUsername(user.getName());
				 * List<TblUserMaster> retrieveWherClause =
				 * QuickUtil.retrieveWherClause(new TblUserMaster(),
				 * "TblUserMaster",
				 * "userId='"+userid+"'  AND userSocialtype='twitter'");
				 * if(retrieveWherClause.size()>0) {
				 * tblUserMaster=retrieveWherClause.get(0); } else {
				 * QuickUtil.saveToNew(tblUserMaster); }
				 */
				String emailid = "";
				String latilongi2 ="";
				if(status.getGeoLocation()!=null)
				{
						latilongi2 = status.getGeoLocation().getLatitude() + ","
						+ status.getGeoLocation().getLongitude();
						
						lati=status.getGeoLocation().getLatitude();
						longi=status.getGeoLocation().getLongitude();
				}
				
				String place ="";
				if(status.getPlace()!=null)
				{
					place = status.getPlace().getFullName();
				}
				String screenName = user.getScreenName();
				String phoneno = "";
				byte[] userimageurl = QuickUtil.doDownloadIamgeFromURL(user
						.getProfileImageURL());
				String zipcode = "";
				String userusername = user.getName();
				String imagetype="jpg";
				if(user.getProfileImageURL().endsWith(".png"))
				{
					imagetype="png";
				}
				TblUserMaster tblUserMaster = QuickUtil.doSaveTblUserMaster(
						user.getLocation(), emailid, userid, latilongi2, place,
						screenName, phoneno, userimageurl, zipcode, socialtype,
						userusername,lati,longi,imagetype,userrootid);
				String images = "";

				boolean isrepeat = QuickUtil.doSaveTblEventCommentDetails(post_id, permalink,
						images, socialtype, message, eventid, userid,
						tblUserMaster,created_time,lati,longi,userrootid);
				if(isrepeat==false)
				{
					i++;
				}
				arrayevents.put(row);
			}
			root.put("twitterevents", arrayevents);

			return root.toString();
		} catch (Exception e) {
			System.out
					.println("Sorry error LoginAndEventsTwitter.doRetrieveTwitterEvents()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * To retrieve tweets for a specific location
	 * @param lati
	 * @param longi
	 * @return
	 */
	public String doRetrieveTwitterEventsWithLatiLongi(String lati,String longi)
	{
		System.out
				.println("LoginAndEventsTwitter.doRetrieveTwitterEventsWithLatiLongi()");
		try 
		{
			/*Twitter twitter = TwitterFactory.getSingleton();
		    twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
//		    RequestToken requestToken = twitter.getOAuthRequestToken();
		    AccessToken accessToken = null;
		    
		    accessToken = twitter.getOAuthAccessToken();
		    System.out.println("Token is "+accessToken.getToken());
		    */
			String token="1712240888-593ocqEE2XdEx8MSHqAv7yHgnmxvbjcRfMhSRQf";
			String tokensecret="scp8GGkFN6K3Eh4R6QrnIrnskNMqOOz3YKEYWGUhQ8";
			TwitterFactory factory = new TwitterFactory();
			AccessToken accestoken = new AccessToken(token, tokensecret);
			Twitter twitter = factory.getInstance();
			twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY,
					TWITTER_CONSUMER_SECRET);
			twitter.setOAuthAccessToken(accestoken);
			
//			 Twitter twitter = new TwitterFactory().getInstance();
//             List<Tweet> tweets = new ArrayList<Tweet>();

             double lat = Double.parseDouble(lati);
             double lon = Double.parseDouble(longi);
             
             JSONObject root=new JSONObject();
             JSONArray arr=new JSONArray();
            
             
             Query q=new Query();
             q=q.geoCode(new GeoLocation(lat, lon), 1, Query.MILES);
              
//             twitter.searchPlaces(arg0)
             QueryResult search = twitter.search(q);
             List<Status> tweets = search.getTweets();
             for(int i=0;i<tweets.size();i++)
             {
            	 Status status = tweets.get(i);
            	 String username = status.getUser().getName();
            	 String pic = status.getUser().getMiniProfileImageURL();
            	 String message = status.getText();
            	 String socialtype="twitter";
//            	 System.out.println("Message is "+message);
            	 JSONObject row2=new JSONObject();
            	 row2.put("username", username);
            	 row2.put("pic",pic);
            	 row2.put("message", message);
            	 row2.put("socialtype", socialtype);
            	 
            	 arr.put(row2);

             }
             
             root.put("fbeventcomments", arr);
             return root.toString();

		} catch (Exception e) {
			System.err
					.println("Sorry error LoginAndEventsTwitter.doRetrieveTwitterEventsWithLatiLongi()"+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

}
