package com.bingoapp;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingoapp.db.TblUserMaster;
import com.bingoapp.db.TblUserownMaster;
import com.bingoapp.util.QuickUtil;
import com.restfb.util.DateUtils;
import com.sola.instagram.InstagramSession;
import com.sola.instagram.auth.AccessToken;
import com.sola.instagram.auth.InstagramAuthentication;
import com.sola.instagram.model.Comment;
import com.sola.instagram.model.Media;
import com.sola.instagram.model.User;
import com.sola.instagram.util.PaginatedCollection;

public class LoginAndEventsInstagram {

	String INSTAGRAM_CLIENTID = "e28c5dfc8db14b76ad75cad27ebf02ed";
	String INSTAGRAM_CLIENT_SECRET = "1af26ef5df5e44bf8956a16599b37520";
	String INSTAGRAM_REDIRECT_URI = "http://www.fasttechinfo.biz";

	public LoginAndEventsInstagram() {
		// TODO Auto-generated constructor stub

	}

	public String doCallFromThreadInstagramUpdate(final String token, final String userid,final Integer userrootid)
	{
		System.out
				.println("LoginAndEventsInstagram.doCallFromThreadInstagramUpdate()");
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				doRetrieveInstagramEvents(token, userid, userrootid);
			}
		});
		t.start();
		return "done updating";
	}
	public String doRetrieveInstagramEvents(String token, String userid,Integer userrootid ) {
		System.out
				.println("LoginAndEventsInstagram.doRetrieveInstagramEvents()");

		try {
			InstagramAuthentication auth = new InstagramAuthentication();
			/*
			 * String authorizationUri =
			 * auth.setRedirectUri(INSTAGRAM_REDIRECT_URI
			 * ).setClientSecret(INSTAGRAM_CLIENT_SECRET).
			 * setClientId(INSTAGRAM_CLIENTID).getAuthorizationUri();
			 */
			AccessToken accesstokenobj = new AccessToken(token);
			InstagramSession session = new InstagramSession(accesstokenobj);

			int useridint = Integer.parseInt(userid);
			User userById = session.getUserById(useridint);
			System.out.println("User name is " + userById.getUserName());
			
		///////done as on 10 sept 2013/////////
			TblUserownMaster tblUserownMaster = new TblUserownMaster();
//			tblUserownMaster.set
			tblUserownMaster.setUserownName(userById.getFullName());
			tblUserownMaster.setUserownId(userById.getId()+"");
			tblUserownMaster.setUserownUsername(userById.getUserName());
			tblUserownMaster.setUserownPhoto(QuickUtil.doDownloadIamgeFromURL(userById.getProfilePictureURI()));
			String imagetype2="jpg";
			if(userById.getProfilePictureURI().endsWith(".png"))
			{
				imagetype2="png";
			}
			tblUserownMaster.setUserownImageContentType(imagetype2);
			QuickUtil.doSaveTblUserrootMaster(tblUserownMaster, userid, "twitter",String.valueOf(userrootid));
			
		///////done as on 10 sept 2013/////////
			
			
			// PaginatedCollection<Media> listmedias =
			// session.getRecentPublishedMedia(useridint);
			// List<Media> listmedias = session.getPopularMedia();
			PaginatedCollection<Media> listmedias = session.getFeed();
			JSONObject jsonroot = new JSONObject();
			
			jsonroot.put("userrootid", userrootid);
			int i=0;
			JSONArray array = new JSONArray();
			System.out.println("Made it here nos " + listmedias.size());
			for (Media media : listmedias) {
				JSONObject row = new JSONObject();
				Double lati=null;
				Double longi=null;
				
				if(i>=1000)
				{
					break;
				}
				
				row.put("eventname", media.getCaption().getText());
				row.put("createddate", media.getCreatedTimestamp());
				row.put("userphoto", media.getLowResolutionImage().getUri());
				row.put("username", media.getUser().getUserName());

				String latilongi = "", place = "";
				if (media.getLocation() != null) {
					place = media.getLocation().getName();
					latilongi = media.getLocation().getLatitude() + ","
							+ media.getLocation().getLongitude();
					lati=media.getLocation().getLatitude();
					longi=media.getLocation().getLongitude();
				}
				row.put("latilongi", latilongi);
				row.put("locationname", place);
				array.put(row);

				System.out.println("Comments length="+media.getComments().size());
				List<Comment> comments = media.getComments();
				for (Comment comment : comments) {
					String createdTimestamp = comment.getCreatedTimestamp();
					String commentid = comment.getId();
					User sender = comment.getSender();
					String text = comment.getText();

					String location = sender.getWebsite();
					String emailid = "";
					String senderid = sender.getId() + "";
					String latilongi2 = latilongi;
					String screenName = sender.getFullName();
					String phoneno = "";
					byte[] userimageurl = QuickUtil
							.doDownloadIamgeFromURL(sender
									.getProfilePictureURI());
					String imagetype="jpg";
					if(sender.getProfilePictureURI().endsWith("png"))
					{
						imagetype="png";
					}
					String zipcode = "";
					String socialtype = "instagram";
					String userusername = sender.getUserName();
					TblUserMaster tblUserMaster = QuickUtil
							.doSaveTblUserMaster(location, emailid, senderid,
									latilongi2, place, screenName, phoneno,
									userimageurl, zipcode, socialtype,
									userusername,lati,longi,imagetype,userrootid);

					Date created_time = DateUtils
							.toDateFromLongFormat(createdTimestamp);
					boolean isrepeat = QuickUtil.doSaveTblEventCommentDetails(commentid, "", "",
							socialtype, text, "", senderid, tblUserMaster,
							created_time,lati,longi,userrootid);
					if(isrepeat==false)
					{
						i++;
					}

				}

			}

			jsonroot.put("instagramevents", array);

			return jsonroot.toString();

		} catch (Exception e) {
			System.out
					.println("Sorry error LoginAndEventsInstagram.doRetrieveInstagramEvents()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
