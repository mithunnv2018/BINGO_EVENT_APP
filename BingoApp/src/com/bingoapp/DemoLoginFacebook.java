package com.bingoapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingoapp.db.TblAdminMaster;
import com.bingoapp.db.TblEventcommentsDetails;
import com.bingoapp.db.TblUserMaster;
import com.bingoapp.db.TblUserownMaster;
import com.bingoapp.db.TblUserrootMaster;
import com.bingoapp.db.Tbleventdetails;
import com.bingoapp.util.BingoUtil;
import com.bingoapp.util.QuickUtil;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.types.Event;
import com.restfb.types.Page;
import com.restfb.types.Place;
import com.restfb.types.User;
import com.restfb.util.DateUtils;

public class DemoLoginFacebook {

	public static String app_id = "368079809988416";

	private String redirect_uri = "www.fasttechinfo.biz";
	public static String app_secret = "134da1bf5373678165f16f7600963233";
	private String code_parameter;
	private String userid = "100002005261537";
	private String access_token = "CAACEdEose0cBAB8xSHaq9bShpmV7JfwtWlh4SlHejBlzg6qoGLwOYy9s59ZCjMVNQEkV6hCqgjWn3bz7ZCuOVt8EfHZB96W5ARLxP8AehT5ORj4VgPmZAlnNY35pJ667oQJg9ioyduptlmPwZA30Pm9q6rTR5L1MZD";

	// "access_token=CAACEdEose0cBAIAGGCBZBvkZB3rmzicNvYniDraKwq4F4NuoIZCUgWEHc34D8ZBmtpcUGGOjMHtcMxG7R0fxjYj0Qz9RgoxGY6JjNnLA3JopZCBN1E0ZCIYhzskLM4d4ZBvK3fHYG1PCiDtoGU6Rn9MSYHKTsWcXcIZD";

	/*
	 * GET https://graph.facebook.com/oauth/access_token? client_id={app-id}
	 * &redirect_uri={redirect-uri} &client_secret={app-secret}
	 * &code={code-parameter}
	 * 
	 * public String doLoginFacebook() { String loginurl2 =
	 * "https://graph.facebook.com/oauth/access_token?" + "client_id=" + app_id
	 * + "&client_secret=" + app_secret + "&grant_type=client_credentials";
	 * 
	 * String loginurl1 = "https://www.facebook.com/dialog/oauth?" +
	 * "client_id=" + app_id + "&redirect_uri=" + redirect_uri;
	 * 
	 * String loginurl = "https://graph.facebook.com/oauth/access_token?" +
	 * "client_id=" + app_id + "&redirect_uri=" + redirect_uri +
	 * "&client_secret=" + app_secret + "&code=" + code_parameter;
	 * 
	 * String accesstoken = BingoUtil.getHTML(loginurl2, "GET");
	 * System.out.println("Reply from server is " + accesstoken); return
	 * accesstoken; }
	 * 
	 * public String getRealPermissionToken() { String url1 =
	 * "https://graph.facebook.com/oauth/authorize?client_id=" + app_id +
	 * " & redirect_uri=http://www.facebook.com/connect/login_success.html &" +
	 * " scope=create_event"; String url2 =
	 * "https://graph.facebook.com/oauth/authorize?" + "client_id=" + app_id +
	 * "&redirect_uri=http://www.facebook.com/connect/login_success.html&" +
	 * "scope=publish_stream,offline_access,create_event,read_stream";
	 * 
	 * String verifycode = BingoUtil.getHTML(url1, "GET");
	 * System.out.println("Verify code=" + verifycode); return verifycode; }
	 * 
	 * public String doRetrieveEvents() { String accesstoken =
	 * doLoginFacebook(); String events =
	 * "https://graph.facebook.com/me/events?" + accesstoken; String events2 =
	 * "https://graph.facebook.com/me/likes?" + accesstoken; String events3 =
	 * "https://graph.facebook.com/100002005261537?fields=events.fields(name)";
	 * String permissions = "https://graph.facebook.com/" + userid +
	 * "/permissions," + accesstoken; String eventsresult = ""; try { //
	 * eventsresult = BingoUtil.post(events, accesstoken); eventsresult =
	 * BingoUtil.getHTML(permissions, "GET"); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }// getHTML(events2,
	 * "GET"); System.out.println("event is " + eventsresult); return
	 * eventsresult; }
	 * 
	 * public String doLoginfacebook2() {
	 * System.out.println("DemoLoginFacebook.doLoginfacebook2()"); String fire1
	 * = "https://graph.facebook.com/oauth/authorize?client_id=" + app_id +
	 * "&redirect_uri=http://localhost:8080/BingoApp/index2.jsp&scope=publish_stream,create_event"
	 * ; String fire2 =
	 * "https://graph.facebook.com/oauth/access_token?client_id=" + app_id +
	 * "& redirect_uri=http://www.facebook.com/connect/login_success.html& client_secret="
	 * + app_secret + "&code="; String html = BingoUtil.getRedirectedURL(fire1,
	 * "GET"); String html2 = BingoUtil.getHTML(fire1, "GET");
	 * 
	 * System.out.println("Result" + html); System.out.println("Result2" +
	 * html2);
	 * 
	 * return null; }
	 * 
	 * public User doFacebookClient() {
	 * 
	 * try { String tokenaccess =
	 * "CAAFOxEJSx0ABALZCnQZAEnzON5YHB4W3Qh2PTPEHzm4hTN4KhyOPdRT4m1LgjHD6dRDZCgsdxICv0gQZADIVhxTO5oSJZBZBZB8rj57FrvNhhwGGRAKZBjvTCFFYFZAnW6Dz3TdoG368ZBsRLVZBpCouOHwXswqQZBLnWYgZD"
	 * ; //
	 * "CAACEdEose0cBAFeRUVlrq82c9LCMF0ZCSDSjvsZCsLTDW618FxVdZApZADwSxgdimhDH9H7xxVSfFsXtknZC4zbDiLE844ZAYfuTU9ffbMAiZCVxNHUjkyNVNZCzJ3ZCbk5JPFGVlYsDbMD2x0u3MXvnvH4ZA5QTIVpL0ZD"
	 * ; // doLoginFacebook(); //
	 * "CAACEdEose0cBANqLAQQulwmHq1iiXhvKdOeZAIV5iJ7RhTiXBCjEARAsqhNOhX6xB47Ab2wnetVAECsYmNaauTq7ATpdq9smufCXDuflaLxyU2WQ5nzx8VZADzOX2fswdXCmG221UdGIG2jslmKgyelKCJCGgZD"
	 * ; //
	 * "CAACEdEose0cBADfVMamsIPrNouKafburaKjUJstrPNnVWqOdqqn5KxM6vPbMBC84ECxq9HVcag5X95ikO6sxZCXK4J0hW6amhYO2ULnazhUNdTrqthSIVHlZC3SC7JVVBo7AtjgSitB9fmRp5ONOez4kO6uZBAZD"
	 * ; // doLoginFacebook(); tokenaccess =
	 * BingoUtil.parseAccessToken(tokenaccess);
	 * System.out.println("New token is =" + tokenaccess); FacebookClient client
	 * = new DefaultFacebookClient(tokenaccess); AccessToken
	 * obtainAppAccessToken = client.obtainAppAccessToken( app_id, app_secret);
	 * 
	 * System.out.println("Brand new token=" +
	 * obtainAppAccessToken.getAccessToken()); // client=new //
	 * DefaultFacebookClient(obtainAppAccessToken.getAccessToken()); User user =
	 * client.fetchObject("mith.nv", User.class); Parameter param =
	 * Parameter.with("id", "225895170891450"); String fqlquery =
	 * "SELECT eid,name,location from event where creator=" + userid;
	 * List<JsonObject> executeFqlQuery = client.executeFqlQuery(fqlquery,
	 * JsonObject.class); System.out.println("Size of fqlqweury=" +
	 * executeFqlQuery.size()); for (int i = 0; i < executeFqlQuery.size(); i++)
	 * { JsonObject jsonObject = executeFqlQuery.get(i); String string =
	 * jsonObject.getString("name"); String string2 =
	 * jsonObject.getString("location"); System.out.println("Event name=" +
	 * string + " location=" + string2);
	 * 
	 * }
	 * 
	 * // Page fetchObject = client.fetchObject("/"+userid+"/accounts", //
	 * Page.class); // String pageaccessToken = fetchObject.getAccessToken();
	 * Connection<Page> pageConnection = client.fetchConnection(userid +
	 * "/accounts", Page.class); System.out.println("Page aces token=" +
	 * pageConnection.getData().size());
	 * 
	 * Connection<User> fetchConnection =
	 * client.fetchConnection(userid+"/events", User.class);//,param); //
	 * Connection<User> fetchConnection2 =
	 * client.fetchConnection(userid+"/friends", User.class);//,param);
	 * 
	 * User fetchObject = client.fetchObject(userid+"/events",
	 * User.class);//,param);
	 * System.out.println("eventobject fetched ="+fetchObject); System.out
	 * .println("Size of events ="+fetchConnection.getData().size()); //
	 * if(fetchConnection2!=null) // { // for(User u
	 * :fetchConnection2.getData()) // { //
	 * System.out.println("friends="+u.toString()); // } // }
	 * 
	 * if(fetchConnection!=null) { for(User p :fetchConnection.getData()) {
	 * System.out.println("user event="+p.toString()); } } else {
	 * System.out.println("Sorry no events"); }
	 * 
	 * System.out.println("user is " + user.toString());// getFirstName());
	 * 
	 * return user; } catch (FacebookOAuthException e2) {
	 * System.err.println(e2.getErrorCode() + "Sorry here mith=" +
	 * e2.getErrorMessage());
	 * 
	 * e2.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } return null; }
	 */
	public String doRetrieveFacebookevents(final String tokenaccess, final String userid) {
		System.out.println("DemoLoginFacebook.doRetrieveFacebookevents()");
		try {
			// tokenaccess="CAACEdEose0cBAFwH3MdSKaQi6lKksDQJZAJuKqkk9CTelXBt5uR5LS61I544aQ9LYlH4pSoD3n88UQZCnReQ7HOoynL0BzJV6qGdbqCeZCDqsKhzOx3CNiqGtPSFNQSCaXvkclKcCZCl4NBe4orOF8rZC2zE9lE8VwJMGAxK8kQZDZD";
			// userid="100000666207852";
			final FacebookClient client = new DefaultFacebookClient(tokenaccess);
			JSONObject obj = new JSONObject();

			User user = client.fetchObject(userid, User.class);
			// "mith.nv", User.class);
			String id = user.getId();

			TblUserownMaster userownmaster = new TblUserownMaster();
			userownmaster.setUserownLocation(user.getHometownName());

			userownmaster.setUserownEmailid(user.getEmail());
			if (user.getLocation() != null) {
				String placeid = user.getLocation().getId();
				Double lati2 = null, longi2 = null;
				if (placeid != null) {

					Place place = client.fetchObject(placeid, Place.class);
					// Place place=(Place) commentinguser.getLocation();
					String latilongi = place.getLocation().getLatitude() + ","
							+ place.getLocation().getLongitude();
					lati2 = place.getLocation().getLatitude();
					longi2 = place.getLocation().getLongitude();
					String zip = place.getLocation().getZip();

					userownmaster.setUserownAddress(place.getLocation()
							.getStreet()
							+ ","
							+ place.getLocation().getCity()
							+ ","
							+ place.getLocation().getState()
							+ ","
							+ place.getLocation().getCountry());

					userownmaster.setUserownLatilongi(latilongi);
					userownmaster.setUserownLatitude(lati2);
					userownmaster.setUserownLongitude(longi2);
					userownmaster.setUserownPincode(zip);

				}
			}
			userownmaster.setUserownName(user.getFirstName() + " "
					+ user.getLastName());
			userownmaster.setUserownPhoneno("");
			userownmaster.setUserownId(user.getId());
			userownmaster.setUserownSocialtype("facebook");
			userownmaster.setUserownUsername(user.getUsername());

			final Integer userrootid = QuickUtil.doSaveTblUserrootMaster(
					userownmaster, userid, "facebook","");
			obj.put("userrootid", userrootid);

			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String fqlquery = "SELECT eid,name,location,pic,description,pic_big,venue,start_time,end_time,timezone,is_date_only  from event WHERE eid IN ( SELECT eid FROM event_member WHERE uid IN ( SELECT uid2 FROM friend WHERE uid1 = me() )  AND start_time > '2012-01-01T00:00:00' OR uid = me())";

					// "where creator="
					// + id;
					List<JsonObject> executeFqlQuery = client.executeFqlQuery(fqlquery,
							JsonObject.class);
					System.out.println("Size of events fqlqweury="
							+ executeFqlQuery.size());
					JSONArray array = new JSONArray();
					System.out
							.println("Print ArrayList =" + executeFqlQuery.toString());
					for (int i = 0; i < executeFqlQuery.size(); i++) {
						JsonObject jsonObject = executeFqlQuery.get(i);
						String eid = jsonObject.getString("eid");
						String string = jsonObject.getString("name");
						String string2 = jsonObject.getString("location");
						String string3 = jsonObject.getString("pic");
						String string4 = jsonObject.optString("description");
						String string5 = jsonObject.getString("pic_big");
						JsonObject venue = jsonObject.getJsonObject("venue");
						Double lati = null, longi = null;
						String string6 = "";

						if (venue != null && venue.has("latitude")
								&& venue.has("longitude")) {
							string6 = (venue.getString("latitude") + "," + venue
									.getString("longitude"));
							lati = venue.getDouble("latitude");
							longi = venue.getDouble("longitude");
						}

						String string7 = jsonObject.getString("start_time");
						String string8 = jsonObject.getString("end_time");
						String string9 = "facebook";
						String timezone = jsonObject.getString("timezone");
						boolean isdateonly = jsonObject.getBoolean("is_date_only");
						DateFormat dateformat2 = DateFormat.getDateTimeInstance();
						if (isdateonly) {
							// dateformat2=DateFormat.getDateInstance();
							dateformat2 = new SimpleDateFormat("yyyy-MM-dd");

						}
						if (timezone != null || !timezone.isEmpty()) {
							dateformat2.setTimeZone(TimeZone.getTimeZone(timezone));
						}

						Date startdate = new Date();
						Date enddate = new Date();
						if (string8 != null && !string8.equals("null")) {
							// enddate=dateformat2.parse(string8);
							enddate = DateUtils.toDateFromLongFormat(string8);
						}
						if (string7 != null && !string7.equals("null")) {
							// startdate=dateformat2.parse(string7);
							startdate = DateUtils.toDateFromLongFormat(string7);
						}

						String imgtype = "";
						if (string3 != null && string3.endsWith(".png")) {
							imgtype = "image/png";
						} else if (string3 != null && string3.endsWith(".jpg")
								|| string3.endsWith(".jpeg")) {
							imgtype = "image/jpeg";
						}

						// string4=string4.replaceAll("'", "");
						// System.err.println("The problem desc is ="+string4);
						Tbleventdetails objevents = new Tbleventdetails();
						Integer doGetNextSequence = QuickUtil
								.doGetNextSequence("Tbleventdetails");
						objevents.setEventId(doGetNextSequence);
						objevents.setEventDate(new Date());
						objevents.setEventDesc(QuickUtil.encode(string4));
						objevents.setEventEndDate(enddate);
						objevents.setEventFbId(eid);
						objevents
								.setEventImg(QuickUtil.doDownloadIamgeFromURL(string3));
						objevents.setEventImgType(imgtype);
						objevents.setEventName(QuickUtil.encode(string));
						objevents.setEventPlace(string2);
						objevents.setEventPlaceLatilongi(string6);
						objevents.setEventStartDate(startdate);
						objevents.setEventType(string9);
						objevents.setEventLatitude(lati);
						objevents.setEventLongitude(longi);

						TblUserrootMaster tblUserrootMaster = new TblUserrootMaster();
						tblUserrootMaster.setUserrootId(userrootid);

						objevents.setTblUserrootMaster(tblUserrootMaster);

						List<Tbleventdetails> retrieveWherClause = QuickUtil
								.retrieveWherClause(
										new Tbleventdetails(),
										"Tbleventdetails",
										"eventFbId='"
												+ eid
												+ "' AND eventType='facebook' AND tblUserrootMaster.userrootId="
												+ userrootid + " ");
						if (retrieveWherClause.size() > 0) {

						} else {
							QuickUtil.saveToNew(objevents);
						}
						doRetrieveFacebookeventscomments(tokenaccess, userid, eid,
								userrootid);

						System.out.println("Event name=" + string + " location="
								+ string2 + " pic=" + string3);
						System.out.println("Json is event details = "
								+ jsonObject.toString());
						array.put(i, jsonObject);
					
					}
				
				}
				
			});
				
			t.start();

			
			
//			obj.put("fbevent", array);
			System.out.println("Result is " + obj.toString());
			return obj.toString();
		} catch (Exception e) {
			System.err.println("Sorry mith error here " + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String doRetrieveFacebookeventscomments(String tokenaccess,
			String userid, String eventid, Integer userrootid) {
		System.out
				.println("DemoLoginFacebook.doRetrieveFacebookeventscomments()");
		try {
			FacebookClient client = new DefaultFacebookClient(tokenaccess);
			JSONObject obj = new JSONObject();

			User user = client.fetchObject(userid, User.class);

			// "mith.nv", User.class);
			String id = user.getId();
			String fqlquery = "SELECT message,actor_id,post_id,permalink,app_data,created_time FROM stream WHERE source_id ="
					+ eventid;
			// "SELECT eid,name,location,pic,description,pic_big  from event where creator="+
			// id;
			List<JsonObject> executeFqlQuery = client.executeFqlQuery(fqlquery,
					JsonObject.class);
			System.out.println("Size of comments fqlqweury="
					+ executeFqlQuery.size());
			JSONArray array = new JSONArray();

			for (int i = 0; i < executeFqlQuery.size(); i++) {
				JsonObject jsonObject = executeFqlQuery.get(i);

				// done added code below retireving user details of the person
				// who posted this comment
				String message = jsonObject.getString("message");
				String actorid = jsonObject.getString("actor_id");
				String post_id = jsonObject.getString("post_id");
				String permalink = jsonObject.getString("permalink");
				JsonObject appdata = jsonObject.optJsonObject("app_data");
				String created_time = jsonObject.optString("created_time");
				Date createddateobj = DateUtils
						.toDateFromLongFormat(created_time);
				Double lati = null, longi = null;

				String images = "";
				TblUserMaster tblUserMaster = new TblUserMaster();
				if (appdata != null && !appdata.isNull("images")) {
					images = appdata.getString("images");
				}

				if(actorid.equals("100002005261537"))
				{
					System.err.println("Danger zone is here ");
				}
				
				User commentinguser = client.fetchObject(actorid, User.class);

				tblUserMaster.setUserLocation(commentinguser.getHometownName());

				tblUserMaster.setUserEmailid(commentinguser.getEmail());

				String userSocialtype = "facebook";
				if (commentinguser.getLocation() != null) {
					String placeid = commentinguser.getLocation().getId();

					if (placeid != null) {

						Place place = client.fetchObject(placeid, Place.class);
						// Place place=(Place) commentinguser.getLocation();
						String latilongi = place.getLocation().getLatitude()
								+ "," + place.getLocation().getLongitude();
						lati = place.getLocation().getLatitude();
						longi = place.getLocation().getLongitude();

						String zip = place.getLocation().getZip();
						tblUserMaster.setUserAddress(place.getLocation()
								.getStreet()
								+ ","
								+ place.getLocation().getCity()
								+ ","
								+ place.getLocation().getState()
								+ ","
								+ place.getLocation().getCountry());

						tblUserMaster.setUserLatilongi(latilongi);
						tblUserMaster.setUserLatitude(lati);
						tblUserMaster.setUserLongitude(longi);
						tblUserMaster.setUserPincode(zip);
					}
				} else {
					tblUserMaster.setUserLatilongi("");

				}

				tblUserMaster.setUserName(commentinguser.getFirstName() + " "
						+ commentinguser.getLastName());
				tblUserMaster.setUserPhoneno("");
				tblUserMaster.setUserId(commentinguser.getId());
				tblUserMaster.setUserSocialtype(userSocialtype);
				tblUserMaster.setUserUsername(commentinguser.getUsername());

				String fqlquery2 = "select username,pic,first_name,last_name from user where uid="
						+ actorid;
				List<JsonObject> executeFqlQuery2 = client.executeFqlQuery(
						fqlquery2, JsonObject.class);
				if (executeFqlQuery2.size() > 0) {
					JsonObject userobj = executeFqlQuery2.get(0);
					if (userobj.length() > 0) {
						String pic = userobj.optString("pic");
						String username = userobj.optString("username");
						String firstname = userobj.optString("first_name");
						String lastname = userobj.optString("last_name");

						String imagetype = "jpg";
						if (pic.contains("png")) {
							imagetype = "png";
						}
						tblUserMaster.setUserPhoto(QuickUtil
								.doDownloadIamgeFromURL(pic));
						tblUserMaster.setUserImageContentType(imagetype);

						System.out.println("The message was added by "
								+ username);

					}
					jsonObject.put("userprofile", userobj);
				}
/*				List<TblUserMaster> retrieveWherClause = QuickUtil
						.retrieveWherClause(new TblUserMaster(),
								"TblUserMaster", "userId='" + actorid
										+ "' AND userSocialtype='facebook' ");
				if (retrieveWherClause.size() > 0) {
					tblUserMaster = retrieveWherClause.get(0);
				} else {
					
					QuickUtil.saveToNew(tblUserMaster);
				}
*/
				String location=tblUserMaster.getUserAddress();
				String emailid=tblUserMaster.getUserEmailid();
				String latilongi2=tblUserMaster.getUserLatilongi();
				String place=tblUserMaster.getUserLocation();
				String screenName=tblUserMaster.getUserName();
				String phoneno="";
				byte[] userimageurl=tblUserMaster.getUserPhoto();
				String zipcode=tblUserMaster.getUserPincode();
				String socialtype="facebook";
				String userusername=tblUserMaster.getUserUsername();
				String imagetype=tblUserMaster.getUserImageContentType();
				String userid2=actorid;//tblUserMaster.getUserId();
				
				
				tblUserMaster=QuickUtil.doSaveTblUserMaster(location, emailid, userid2, latilongi2, place, screenName, phoneno, userimageurl, zipcode, socialtype, userusername, lati, longi, imagetype, userrootid);

				System.out.println(i+"= Comenting User detail"+tblUserMaster.toString());
				TblEventcommentsDetails objcomm = new TblEventcommentsDetails();
				Integer doGetNextSequence = QuickUtil
						.doGetNextSequence("TblEventcommentsDetails");
				objcomm.setCommentPk(doGetNextSequence);
				objcomm.setCommentId(post_id);
				objcomm.setCommentLink(permalink);
				objcomm.setCommentPhoto(QuickUtil
						.doDownloadIamgeFromURL(images));
				objcomm.setCommentSocialtype("facebook");
				objcomm.setCommentText(QuickUtil.encode(message));
				objcomm.setCommentCreatedTime(createddateobj);
				objcomm.setCommentLatitude(lati);
				objcomm.setCommentLongitude(longi);

				System.err.println("Event id is " + eventid);
				objcomm.setEventId(eventid);

				// Tbleventdetails tbleventdetails=new Tbleventdetails();
				// tbleventdetails.setEventId(Integer.parseInt(eventid));
				// setTbleventdetails(tbleventdetails);

				objcomm.setTblUserMaster(tblUserMaster);
				objcomm.setUserId(userid);

				TblUserrootMaster tblUserrootMaster = new TblUserrootMaster();
				tblUserrootMaster.setUserrootId(userrootid);
				objcomm.setTblUserrootMaster(tblUserrootMaster);

	/*			List<TblEventcommentsDetails> retrieveWherClause2 = QuickUtil
						.retrieveWherClause(
								new TblEventcommentsDetails(),
								"TblEventcommentsDetails",
								"commentId='"
										+ post_id
										+ "' AND commentSocialtype='facebook' AND tblUserrootMaster.userrootId="
										+ userrootid);
				if (retrieveWherClause2.size() > 0) {

				} else {
					QuickUtil.saveToNew(objcomm);
				}*/
				// System.out.println("Event Message=" + message);

				QuickUtil.doSaveTblEventCommentDetails(post_id, permalink, images, socialtype, message, eventid, userid2, tblUserMaster, createddateobj, lati, longi, userrootid);
				
				System.out.println("Json is event details = "
						+ jsonObject.toString());
				array.put(i, jsonObject);

			}
			obj.put("fbeventcomments", array);
			System.out.println("Result is " + obj.toString());
			return obj.toString();
		} catch (Exception e) {
			System.err.println("Sorry mith error here " + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String doRetrieveFacebookeventsLatiLongi(final String tokenaccess, final String userid,final String lati,final String longi)
	{
		System.out
				.println("DemoLoginFacebook.doRetrieveFacebookeventsLatiLongi()");
		String miles="1609";//rem decimal not acceptable
		try 
		{
			final FacebookClient client = new DefaultFacebookClient(tokenaccess);
			String firstfqlquery="SELECT eid,name,location,pic,description  from event WHERE eid IN (SELECT eid FROM event_member WHERE uid IN (SELECT page_id FROM place WHERE distance(latitude, longitude,\""+lati+"\", \""+longi+"\") < "+miles+"))";
//					"SELECT eid FROM event_member WHERE uid IN (SELECT page_id FROM place WHERE distance(latitude, longitude, "+lati+", "+longi+") < "+miles+")";
			
			List<JsonObject> firstexecuteFqlQuery = client.executeFqlQuery(firstfqlquery,
					JsonObject.class);
			JSONObject root=new JSONObject();
			JSONArray arr=new JSONArray();
			
			if(firstexecuteFqlQuery!=null && firstexecuteFqlQuery.size()>0)
			{
				for(int i=0;i<firstexecuteFqlQuery.size();i++)
				{
					JsonObject jsonObject = firstexecuteFqlQuery.get(i);
					String eid = jsonObject.getString("eid");
					
//					String fqlquery = "SELECT eid,name,location,pic,description,pic_big,venue,start_time,end_time,timezone,is_date_only  from event WHERE eid ="+eid;

					
					String string = jsonObject.getString("name");
					String string2 = jsonObject.getString("location");
					String string3 = jsonObject.getString("pic");
					String string4 = jsonObject.optString("description");
					
					JSONObject row=new JSONObject();
					row.put("eid", eid);
					row.put("name",string);
					row.put("location",string2);
					row.put("pic",string3);
					row.put("description",string4);
					
					arr.put(i, row);
				}
				
			}
			root.put("fbevent", arr);
			
			return root.toString();
			
		}
		catch (Exception e) {
			System.err
					.println("Sorry error here DemoLoginFacebook.doRetrieveFacebookeventsLatiLongi()"+e.getMessage());
		}
		return "";
	}
	
	public String doRetrieveFacebookeventscommentsLatiLongi(String tokenaccess,String userid, String eventid, String userrootid)
	{
		System.out
				.println("DemoLoginFacebook.doRetrieveFacebookeventscommentsLatiLongi()");
		
		try 
		{
			FacebookClient client = new DefaultFacebookClient(tokenaccess);
			
			String fqlquery = "SELECT message,actor_id,post_id,permalink,app_data,created_time FROM stream WHERE source_id ="
					+ eventid;
			List<JsonObject> executeFqlQuery = client.executeFqlQuery(fqlquery,
					JsonObject.class);
			System.out.println("Size of comments fqlqweury="
					+ executeFqlQuery.size());
			JSONArray array = new JSONArray();


			JSONObject root = new JSONObject();

			JSONArray arr = new JSONArray();

			for (int i = 0; i < executeFqlQuery.size(); i++) {
				JsonObject jsonObject = executeFqlQuery.get(i);

				// done added code below retireving user details of the person
				// who posted this comment
				String message = jsonObject.getString("message");
				String actorid = jsonObject.getString("actor_id");
//				String post_id = jsonObject.getString("post_id");
//				String permalink = jsonObject.getString("permalink");
//				JsonObject appdata = jsonObject.optJsonObject("app_data");
				String pic="";
				String username="";
				String socialtype="facebook";
				
//				String images = "";
				
				String fqlquery2 = "select username,pic,first_name,last_name from user where uid="
						+ actorid;
				
				List<JsonObject> executeFqlQuery2 = client.executeFqlQuery(
						fqlquery2, JsonObject.class);
				if (executeFqlQuery2.size() > 0) {
					JsonObject userobj = executeFqlQuery2.get(0);
					if (userobj.length() > 0) {
						 pic = userobj.optString("pic");
						username = userobj.optString("username");
						
					}
				}
				
				JSONObject row2 = new JSONObject();
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
					.println("Sorry error DemoLoginFacebook.doRetrieveFacebookeventscommentsLatiLongi()"+e.getMessage());
			e.printStackTrace();
		}
		return "";
		
	}

	public static void main(String[] args) {
		System.out.println("AllTests.test1()");
		DemoLoginFacebook login = new DemoLoginFacebook();
		//
		// login.doFacebookClient();
		// login.doLoginfacebook2();
		String retulevents = login
				.doRetrieveFacebookevents(
						"CAACEdEose0cBAFwH3MdSKaQi6lKksDQJZAJuKqkk9CTelXBt5uR5LS61I544aQ9LYlH4pSoD3n88UQZCnReQ7HOoynL0BzJV6qGdbqCeZCDqsKhzOx3CNiqGtPSFNQSCaXvkclKcCZCl4NBe4orOF8rZC2zE9lE8VwJMGAxK8kQZDZD",
						"100000666207852");
		System.out.println("Result = " + retulevents);
		// login.doRetrieveFacebookeventscomments("CAACEdEose0cBAJZAO43Q9IPCrgEs7RvsIMHGo0qzFaTYEG85vRrB4hAuIM2L2euRjBEA7MaiNSZCLZADVR8NmcOoqTDVHxO2Pz6rdWUWnrmMp05GvTqNMW8SeVnsIGeAC8uXooNonZCAZCQGCZAaKZB2WaFE05xdyEZD",
		// "100002005261537", "1406507996234286");
		//
	}
}
