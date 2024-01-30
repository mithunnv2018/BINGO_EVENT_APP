package com.bingoapp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingoapp.db.TblEventcommentsDetails;
import com.bingoapp.db.TblUserMaster;
import com.bingoapp.db.Tbleventdetails;
import com.bingoapp.util.QuickUtil;

public class RetrieveEventDetails {

	/**
	 * Retireve events for eventype=facebook
	 * @param userrootid
	 * @return
	 */
	public String doRetrieveEvents(String userrootid) {
		System.out.println("RetrieveEventDetails.doRetrieveEvents()");
		try {
			List<Tbleventdetails> retrieveWherClause = QuickUtil
					.retrieveWherClause(new Tbleventdetails(),
							"Tbleventdetails",
							"eventType='facebook' AND tblUserrootMaster.userrootId="
									+ userrootid + "");
			JSONObject root = new JSONObject();
			JSONArray arr = new JSONArray();

			if (retrieveWherClause != null && retrieveWherClause.size() > 0) {
				System.out.println("Hi mith size of evets="+retrieveWherClause.size());
				for (int i = 0; i < retrieveWherClause.size(); i++) {
					Tbleventdetails tbleventdetails = retrieveWherClause.get(i);
					JSONObject row = new JSONObject();
					row.put("eid", tbleventdetails.getEventFbId());
					row.put("name", QuickUtil.decode(tbleventdetails.getEventName()));
					row.put("location", tbleventdetails.getEventPlace());
					row.put("pic", QuickUtil.getBingoWebImageURL(
							tbleventdetails.getEventImg(),
							tbleventdetails.getEventImgType()));
					row.put("description", QuickUtil.decode(tbleventdetails.getEventDesc()));

					arr.put(i, row);

				}

			}
			root.put("fbevent", arr);

			
			System.out.println("RetrieveEventDetails.doRetrieveEvents()"+root.toString());
			return root.toString();
		} catch (Exception e) {
			System.out
					.println("Sorry error RetrieveEventDetails.doRetrieveEvents()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * To retreve events as of admin event type='admin"
	 * @return
	 */
	public String doRetrieveEventsForAdmin() {
		System.out.println("RetrieveEventDetails.doRetrieveEventsForAdmin()");
		try {
			 
			List<Tbleventdetails> retrieveWherClause = QuickUtil
					.retrieveWherClause(new Tbleventdetails(),
							"Tbleventdetails",
							"eventType='admin'");
			JSONObject root = new JSONObject();
			JSONArray arr = new JSONArray();

			if (retrieveWherClause != null && retrieveWherClause.size() > 0) {
				System.out.println("Hi mith size of evets="+retrieveWherClause.size());
				for (int i = 0; i < retrieveWherClause.size(); i++) {
					Tbleventdetails tbleventdetails = retrieveWherClause.get(i);
					JSONObject row = new JSONObject();
					row.put("eid", tbleventdetails.getEventId());
					row.put("name", QuickUtil.decode(tbleventdetails.getEventName()));
					row.put("location", tbleventdetails.getEventPlace());
					row.put("pic", QuickUtil.getBingoWebImageURL(
							tbleventdetails.getEventImg(),
							tbleventdetails.getEventImgType()));
					row.put("description", QuickUtil.decode(tbleventdetails.getEventDesc()));

					arr.put(i, row);

				}

			}
			root.put("fbevent", arr);

			
			System.out.println("RetrieveEventDetails.doRetrieveEvents()"+root.toString());
			return root.toString();
		} catch (Exception e) {
			System.out
					.println("Sorry error RetrieveEventDetails.doRetrieveEvents()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Retrieve event details for that specific event
	 * If eventtype==admin then use "eventId=eventid" or else use eventFbId=evenmtid 
	 * @param eventid
	 * @param userrootid
	 * @param eventtype
	 * @return
	 */
	public String doRetrieveEventDetails(String eventid, String userrootid,String eventtype) {
		System.out.println("RetrieveEventDetails.doRetrieveEventDetails()");

		// SELECT * FROM tbl_eventcomments_details WHERE comment_created_time
		// BETWEEN '2012-11-13 00:32:56' AND '2013-11-13 00:32:56' AND
		// comment_latitude IS NULL OR comment_longitude IS NULL

		// SELECT comment_pk,( 3959 * ACOS( COS( RADIANS(19.2) ) * COS( RADIANS(
		// comment_latitude ) ) * COS( RADIANS( comment_longitude ) -
		// RADIANS(72.9667) ) + SIN( RADIANS(19.2) ) * SIN( RADIANS(
		// comment_latitude ) ) ) ) AS distance FROM tbl_eventcomments_details
		// WHERE comment_created_time BETWEEN '2012-11-13 00:32:56' AND
		// '2013-11-13 00:32:56' AND comment_latitude IS NOT NULL OR
		// comment_longitude IS NOT NULL HAVING distance < 30 ORDER BY distance
		try {
			List<Tbleventdetails> retrieveWherClause = null;
			
			if(!eventtype.equals("admin"))
			{
				retrieveWherClause = QuickUtil
					.retrieveWherClause(
							new Tbleventdetails(),
							"Tbleventdetails",
							"eventFbId='"
									+ eventid
									+ "' AND eventType='facebook'  AND tblUserrootMaster.userrootId="
									+ userrootid + "");
			}
			if(eventtype.equals("admin"))
			{
				retrieveWherClause =QuickUtil.retrieveWherClause(
						new Tbleventdetails(),
						"Tbleventdetails",
						"eventId='"
								+ eventid
								+ "' AND eventType='admin' ");
			}
			if (retrieveWherClause.size() > 0) {
				System.out.println("Hi mith retrieved size ="
						+ retrieveWherClause.size());
				Tbleventdetails tbleventdetails = retrieveWherClause.get(0);

				Date eventDate = tbleventdetails.getEventDate();
				Date eventStartDate = tbleventdetails.getEventStartDate();
				Date eventEndDate = tbleventdetails.getEventEndDate();
				if (eventStartDate == null) {
					eventStartDate = eventDate;
				}
				if (eventEndDate == null) {
					return null;
				}

				Double eventLatitude = tbleventdetails.getEventLatitude();
				Double eventLongitude = tbleventdetails.getEventLongitude();

				String sql1 = "commentCreatedTime BETWEEN '"
						+ eventStartDate.toString()
						+ "' AND '"
						+ eventEndDate.toString()
						+ "' AND commentLatitude IS NULL OR commentLongitude IS NULL AND tblUserrootMaster.userrootId="+userrootid+"";
				String sql2 = "";

				// String
				// sql2="SELECT comment_text,comment_link,comment_photo,user_id,event_id,comment_id,user_pk,comment_created_time,comment_latitude,comment_longitude FROM tbl_eventcomments_details WHERE comment_created_time BETWEEN '2012-11-13 00:32:56' AND '2013-11-13 00:32:56' AND comment_latitude IS NULL OR comment_longitude IS NULL";
				/*System.out.println("Query is " + sql1);
				List<TblEventcommentsDetails> createSQLQuery = QuickUtil
						.retrieveWherClause(new TblEventcommentsDetails(),
								"TblEventcommentsDetails", sql1);*/

				JSONObject root = new JSONObject();

				JSONArray arr = new JSONArray();

				/*if (createSQLQuery != null && createSQLQuery.size() > 0) {
					System.out.println("hi mith size of 1st query comment size"+createSQLQuery.size());
					for (int i = 0; i < createSQLQuery.size() && i<100; i++) {
						TblEventcommentsDetails obj = createSQLQuery.get(i);
						JSONObject row = new JSONObject();
						String userId = obj.getUserId();
						System.out.println("Hi mith user id from comment is "+userId+" at row = "+i);
						List<TblUserMaster> userlist = QuickUtil
								.retrieveWherClause(new TblUserMaster(),
										"TblUserMaster", "userId='" + userId
												+ "' ");
						if (userlist != null && userlist.size() > 0) {
							System.out.println("Hi mith userlist size="+userlist.size());
							TblUserMaster user = userlist.get(0);
							row.put("username", user.getUserUsername());
							row.put("pic",
									QuickUtil.getBingoWebImageURL(
											user.getUserPhoto(),
											user.getUserImageContentType()));
							row.put("message", QuickUtil.decode(obj.getCommentText()));
							row.put("socialtype", obj.getCommentSocialtype());
							arr.put(row);
						}
					}
				}*/

				if (eventLatitude != null && eventLongitude != null) {
					sql2 = "SELECT comment_pk,( 3959 * ACOS( COS( RADIANS("
							+ eventLatitude
							+ ") ) * COS( RADIANS( comment_latitude ) ) * COS( RADIANS( comment_longitude ) - RADIANS("
							+ eventLongitude
							+ ") ) + SIN( RADIANS("
							+ eventLatitude
							+ ") ) * SIN( RADIANS( comment_latitude ) ) ) ) AS distance FROM tbl_eventcomments_details WHERE comment_created_time BETWEEN '"
							+ eventStartDate.toString()
							+ "' AND '"
							+ eventEndDate.toString()
							+ "' AND comment_latitude IS NOT NULL OR comment_longitude IS NOT NULL AND userroot_id="+userrootid+" HAVING distance < 1 ORDER BY distance";
					List createSQLQuery2 = QuickUtil.CreateSQLQuery(sql2);
					if (createSQLQuery2 != null && !createSQLQuery2.isEmpty()) {
						System.out.println("hi mith size of 2nd query comment size"+createSQLQuery2.size());
						for (int i = 0; i < createSQLQuery2.size()&& i<100; i++) {
							JSONObject row2 = new JSONObject();
							Object[] row = (Object[]) createSQLQuery2.get(i);
							String commentpk = (String) String.valueOf(row[0]);
							String distance = (String) String.valueOf(row[1]);
							TblEventcommentsDetails obj = QuickUtil
									.doGetTblEventCommentDetails(commentpk);
							TblUserMaster user = QuickUtil
									.doGetTblUserMaster(obj.getUserId());
							row2.put("username", user.getUserUsername());
							row2.put(
									"pic",
									QuickUtil.getBingoWebImageURL(
											user.getUserPhoto(),
											user.getUserImageContentType()));
							row2.put("message", QuickUtil.decode(obj.getCommentText()));
							row2.put("socialtype", obj.getCommentSocialtype());
							arr.put(row2);
						}
					}
				}

				root.put("fbeventcomments", arr);

				System.out
						.println("RetrieveEventDetails.doRetrieveEventDetails()"+root.toString());
				return root.toString();

			}
		} catch (Exception e) {
			System.out
					.println("Sorry error RetrieveEventDetails.doRetrieveEventDetails()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
