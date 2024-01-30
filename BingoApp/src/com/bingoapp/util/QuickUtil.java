package com.bingoapp.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bingoapp.db.TblEventcommentsDetails;
import com.bingoapp.db.TblUserMaster;
import com.bingoapp.db.TblUserownMaster;
import com.bingoapp.db.TblUserrootDetails;
import com.bingoapp.db.TblUserrootMaster;
import com.bingoapp.db.Tblsequence;

public class QuickUtil {

	public static String[] modules = { "City", "State", "Golf Clubs",
			"Golf Courses", "Score Card", "GPS Marker Master", "GPS Lati Longi" };

	public QuickUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String saveToNew(Object mobj) {
		Session session = HibernateUtils.currentSession();
		Transaction tx = null;
		boolean rollback = true;
		try {
			tx = session.beginTransaction();
			Serializable save = session.save(mobj);

			tx.commit();
			rollback = false;
			return save.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("saveToNew Failed for " + mobj.getClass()
					+ e.getCause());
		} finally {
			if (rollback && tx != null) {
				tx.rollback();
			}
			HibernateUtils.closeSession();
		}
		return "";
	}

	public static void updateToOld(Object mobj) {
		Session session = HibernateUtils.currentSession();
		Transaction tx = null;
		boolean rollback = true;
		try {
			tx = session.beginTransaction();
			session.update(mobj);

			tx.commit();
			rollback = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rollback && tx != null) {
				tx.rollback();
			}
			HibernateUtils.closeSession();
		}
	}

	public static int deleteFromDB(String tablename, String whereclause) {
		Session session = HibernateUtils.currentSession();
		Transaction tx = null;
		int ret = 0;
		boolean rollback = true;
		try {
			tx = session.beginTransaction();
			Query createQuery = session.createQuery("delete from " + tablename
					+ " where " + whereclause);
			ret = createQuery.executeUpdate();
			System.out.println("Mith Rows Deleted:" + ret);
			tx.commit();
			rollback = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rollback && tx != null) {
				tx.rollback();
			}
			HibernateUtils.closeSession();

		}
		return ret;
	}

	/*
	 * public static <T> List retrieveALL(T a, String tablename, String
	 * columnames) { Session session = HibernateUtils.currentSession(); try {
	 * Query query = session.createQuery("SELECT   " + columnames + " FROM " +
	 * tablename + " a");
	 * 
	 * // +" WHERE u_username='" + U_UserName + // "' AND u_pass='"+U_Pass+"'");
	 * List<T> dataList = query.list(); return dataList;
	 * 
	 * } catch (Exception e) { System.out.println("checklogin method error:" +
	 * e.getMessage()); e.printStackTrace(); } finally {
	 * 
	 * HibernateUtils.closeSession(); } return null; }
	 */
	public static <T> List retrieveALLwithHB(T a, String tablename,
			String columnames) {
		Session session = HibernateUtils.currentSession();
		try {
			Query query = session.createQuery("from  " + tablename + " a");

			// +" WHERE u_username='" + U_UserName +
			// "' AND u_pass='"+U_Pass+"'");
			List<T> dataList = query.list();
			return dataList;

		} catch (Exception e) {
			System.out.println("retieveALlwithHB method error:"
					+ e.getMessage());
			e.printStackTrace();
		} finally {

			HibernateUtils.closeSession();
		}
		return null;
	}

	public static <T> List retrieveWherClause(T a, String tablename,
			String whereclause) {
		Session session = HibernateUtils.currentSession();
		try {
			// Query query = session.createQuery("SELECT   "+columnames
			// + " FROM "+tablename+" a" +" "+whereclause);

			// +" WHERE u_username='" + U_UserName +
			// "' AND u_pass='"+U_Pass+"'");
			Query query = session.createQuery("from " + tablename + " where "
					+ whereclause);
			List<T> dataList = query.list();
			return dataList;

		} catch (Exception e) {
			System.out.println("checklogin method error:" + e.getMessage());
			e.printStackTrace();
		} finally {

			HibernateUtils.closeSession();
		}
		return null;
	}

	public static <T> List retrieveDistinctWhereClause(T a, String tablename,
			String[] columns, String whereclause) {
		Session session = HibernateUtils.currentSession();
		System.out.println("QuickUtil.retrieveDistinctWhereClause()");
		try {

			String distinctcolumn = "";
			if (columns.length > 0) {
				for (int i = 0; i < columns.length; i++) {
					distinctcolumn += "a." + columns[i];
					if ((i + 1) == columns.length) {

					} else {
						distinctcolumn += " , ";
					}

				}
			}

			String query = "SELECT DISTINCT " + distinctcolumn + " FROM "
					+ tablename + " a ";
			if ((whereclause.trim()).isEmpty() == false) {
				query += "WHERE " + whereclause;
			}
			Query createQuery = session.createQuery(query);
			List<T> list = createQuery.list();
			return list;
		} catch (Exception e) {
			System.err.println("error here mith : " + e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateUtils.closeSession();
		}
		return null;
	}

	/*
	 * Exceute Raw SQL Query
	 */
	public static List CreateSQLQuery(String sql) {
		Session session = HibernateUtils.currentSession();

		try {

			Query qx = session.createSQLQuery(sql);
			List lstqry = qx.list();
			return lstqry;

		} catch (Exception ex1) {

			System.err.println("ErrorCreateSQLQuery:" + ex1.getMessage());
			ex1.printStackTrace();
			return null;
		} finally {
			HibernateUtils.closeSession();
		}
	}

	/*
	 * Exceute Raw SQL Query whcih returns in HashMap
	 */
	public static List CreateSQLQueryHashMap(String sql) {
		Session session = HibernateUtils.currentSession();

		try {

			Query qx = session.createSQLQuery(sql);
			qx.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List lstqry = qx.list();
			return lstqry;

		} catch (Exception ex1) {

			System.err.println("ErrorCreateSQLQuery:" + ex1.getMessage());
			return null;
		} finally {
			HibernateUtils.closeSession();
		}
	}

	public static Integer doGetNextPKdate() {
		Date abc = Calendar.getInstance().getTime();
		int a = (int) abc.getTime();
		return a;
	}

	public static Integer doGetNextPK(String tablename, String pkid) {

		if (tablename.isEmpty()) {
			Integer ret = new Random().nextInt(10000);
			return ret;
		} else {
			StringBuffer sqlQry = new StringBuffer();
			sqlQry.append(" SELECT  max(E." + pkid + ") from  " + tablename
					+ " E");

			try {
				Session session = HibernateUtils.currentSession();
				Query dashBrdQry = session.createQuery(sqlQry.toString());

				List dataList = dashBrdQry.list();
				System.out.println("size is" + dataList.size());

				if (dataList.get(0) == null) {
					return 1;
				}
				System.out.println("class is" + dataList.get(0).getClass()
						+ "valui is " + dataList.get(0));
				Integer a = 0;

				System.out.println("IS a string here");
				if (dataList.get(0).getClass().getName().indexOf("String") != -1) {
					String r = (String) dataList.get(0);
					a = Integer.parseInt(r);
				} else if (dataList.get(0).getClass().getName()
						.indexOf("Integer") != -1) {
					a = (Integer) dataList.get(0);
				}
				a++;
				System.out.println("Value is " + a);
				return a;

				// Integer ret=(Integer) dataList.get(0);
				// ret++;

				// return ret;

			} catch (Exception e) {
				System.out.println("dogetNextPK method has error "
						+ e.getMessage());
				e.printStackTrace();
			} finally {

				HibernateUtils.closeSession();
			}
		}
		return null;
	}

	/**
	 * retrieves a random number and checks if the new Primary Key is Not
	 * repeated.
	 * 
	 * @param tablename
	 * @param column
	 *            must be a integer type columnn
	 * @param b
	 *            is just for methodoverloading
	 * @return
	 */
	public static Integer doGetNextPK(String tablename, String column, boolean b) {
		boolean stop2 = false;
		Integer pk2 = 0;

		for (int i = 0; i < 100 && stop2 != true; i++) {
			pk2 = doGetNextPK("", "");
			if (tablename.length() == 0 || column.length() == 0) {
				System.out
						.println("doGetNextPK tablename is empty so returned random number");
				return pk2;
			}
			try {
				List abcd = retrieveWherClause(new Object(), tablename, column
						+ "=" + pk2);
				if (abcd.size() == 0) {
					System.out
							.println("doGetNextPK is a uniques number so returned random number");

					stop2 = true;
					return pk2;
				} else {
					System.out
							.println("doGetNextPK tablename is already existing");

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				break;
			}
		}
		System.out.println("doGetNextPK(3args) had an error");
		return pk2;
	}

	public static Integer doGetNextSequence(String tablename) {
		List<Tblsequence> retrieveWherClause = QuickUtil.retrieveWherClause(
				new Tblsequence(), "Tblsequence", "tablename='" + tablename
						+ "' ");
		if (retrieveWherClause.size() > 0) {
			Tblsequence t = retrieveWherClause.get(0);
			Integer nextsequenceno = t.getNextsequenceno();
			++nextsequenceno;
			System.out.println("New sequence id is " + nextsequenceno);
			t.setNextsequenceno(nextsequenceno);
			QuickUtil.updateToOld(t);
			System.out.println("Update sequence id.");
			return nextsequenceno;
		}
		return 1;

	}

	/*
	 * To check if the string field exists in the that table
	 */
	public static <T> Boolean doCheckIfRepeat(T obj, String tablename,
			String columnname, Object value2) {

		List retrieveWherClause = QuickUtil.retrieveWherClause(obj, tablename,
				columnname + "='" + value2 + "' ");

		if (retrieveWherClause.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static byte[] doDownloadIamgeFromURL(String imageurl) {
		try {
			if (imageurl == null || imageurl.isEmpty()) {
				return null;
			}
			URL url = new URL(imageurl);

			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response1 = out.toByteArray();

			return response1;
		} catch (MalformedURLException e) {
			System.out.println("Sorry eror QuickUtil.doDownloadIamgeFromURL()"
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Sorry eror QuickUtil.doDownloadIamgeFromURL()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static boolean doSaveTblEventCommentDetails(String post_id,
			String permalink, String images, String socialtype, String message,
			String eventid, String userid, TblUserMaster tblUserMaster,
			Date created_time, Double lati, Double longi, Integer userrootid) {
		try {
			TblEventcommentsDetails objcomm = new TblEventcommentsDetails();
			Integer doGetNextSequence = QuickUtil
					.doGetNextSequence("TblEventcommentsDetails");
			objcomm.setCommentPk(doGetNextSequence);
			objcomm.setCommentId(post_id);
			objcomm.setCommentLink(permalink);
			objcomm.setCommentPhoto(QuickUtil.doDownloadIamgeFromURL(images));
			objcomm.setCommentSocialtype(socialtype);
			objcomm.setCommentText(QuickUtil.encode(message));
			objcomm.setCommentCreatedTime(created_time);
			objcomm.setCommentLatitude(lati);
			objcomm.setCommentLongitude(longi);
			System.err.println("Event id is " + eventid);
			objcomm.setEventId(eventid);

			TblUserrootMaster tblUserrootMaster=new TblUserrootMaster();
			tblUserrootMaster.setUserrootId(userrootid);
			objcomm.setTblUserrootMaster(tblUserrootMaster);
			
			// Tbleventdetails tbleventdetails=new Tbleventdetails();
			// tbleventdetails.setEventId(Integer.parseInt(eventid));
			// setTbleventdetails(tbleventdetails);

			objcomm.setTblUserMaster(tblUserMaster);
			objcomm.setUserId(userid);

			List<TblEventcommentsDetails> retrieveWherClause2 = QuickUtil
					.retrieveWherClause(new TblEventcommentsDetails(),
							"TblEventcommentsDetails", "commentId='" + post_id
									+ "' AND commentSocialtype='" + socialtype
									+ "' AND tblUserrootMaster.userrootId="+userrootid+"");
			if (retrieveWherClause2.size() > 0) {
					return true;
			} else {
				QuickUtil.saveToNew(objcomm);
			}
			return false;
		} catch (Exception e) {
			System.out
					.println("Sorry error here QuickUtil.doSaveTblEventCommentDetails() "
							+ e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Save data to tblUSerMaster
	 * 
	 * @param location
	 * @param emailid
	 * @param userid
	 * @param latilongi2
	 * @param place
	 * @param screenName
	 * @param phoneno
	 * @param userimageurl
	 * @param zipcode
	 * @param socialtype
	 * @param userusername
	 * @param longi
	 * @param lati
	 * @param imagetype 
	 * @param userrootid 
	 * @return
	 */
	public static TblUserMaster doSaveTblUserMaster(String location,
			String emailid, String userid, String latilongi2, String place,
			String screenName, String phoneno, byte[] userimageurl,
			String zipcode, String socialtype, String userusername,
			Double lati, Double longi, String imagetype, Integer userrootid) {
		try {
			TblUserMaster tblUserMaster = new TblUserMaster();
			tblUserMaster.setUserAddress(location);
			tblUserMaster.setUserEmailid(emailid);
			tblUserMaster.setUserId(userid);
			tblUserMaster.setUserLatilongi(latilongi2);
			tblUserMaster.setUserLocation(place);
			tblUserMaster.setUserName(screenName);
			tblUserMaster.setUserPhoneno(phoneno);
			tblUserMaster.setUserPhoto(userimageurl);
			tblUserMaster.setUserPincode(zipcode);
			tblUserMaster.setUserSocialtype(socialtype);
			tblUserMaster.setUserUsername(userusername);
			tblUserMaster.setUserLatitude(lati);
			tblUserMaster.setUserLongitude(longi);
			tblUserMaster.setUserImageContentType(imagetype);
			
			TblUserrootMaster tblUserrootMaster=new TblUserrootMaster();
			tblUserrootMaster.setUserrootId(userrootid);
			tblUserMaster.setTblUserrootMaster(tblUserrootMaster);
			
			List<TblUserMaster> retrieveWherClause = QuickUtil
					.retrieveWherClause(new TblUserMaster(), "TblUserMaster",
							"userId='" + userid + "'  AND userSocialtype='"
									+ socialtype + "' AND tblUserrootMaster.userrootId="+userrootid+"" );
			if (retrieveWherClause.size() > 0) {
				tblUserMaster = retrieveWherClause.get(0);
			} else {
				QuickUtil.saveToNew(tblUserMaster);
			}
			return tblUserMaster;
		} catch (Exception e) {
			System.out
					.println("Sorry errror here QuickUtil.doSaveTblUserMaster()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the TblEventCommentDetails for that particular COMMENTPK
	 * 
	 * @param commentpk
	 * @return
	 */
	public static TblEventcommentsDetails doGetTblEventCommentDetails(
			String commentpk) {
		System.out.println("QuickUtil.doGetTblEventCommentDetails()");
		try {
			int pk = Integer.parseInt(commentpk);
			List<TblEventcommentsDetails> retrieveWherClause = QuickUtil
					.retrieveWherClause(new TblEventcommentsDetails(),
							"TblEventcommentsDetails", "commentPk=" + pk);
			if (retrieveWherClause.size() > 0) {
				TblEventcommentsDetails tblEventcommentsDetails = retrieveWherClause
						.get(0);

				return tblEventcommentsDetails;
			}
		} catch (Exception e) {
			System.out
					.println("Sorry error QuickUtil.doGetTblEventCommentDetails()"
							+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the TblUSerMaster for that userid
	 * 
	 * @param userId
	 * @return
	 */
	public static TblUserMaster doGetTblUserMaster(String userId) {

		if (userId != null && !userId.isEmpty()) {
			List<TblUserMaster> userlist = QuickUtil.retrieveWherClause(
					new TblUserMaster(), "TblUserMaster", "userId='" + userId
							+ "' ");
			if (userlist != null && userlist.size() > 0) {
				return userlist.get(0);
			}
		}
		return null;
	}

	/**
	 * Path of where the image is saved
	 * 
	 * @return
	 */
	public static String getBingoImageAppPath() {
		System.out.println("GamePlay.getGolfImageAppPath()");
		URL url = QuickUtil.class.getResource("QuickUtil.class");
		// .getCanonicalName());
		File file = new File(url.getPath());
		String x = file.getAbsolutePath().toString();
		int indexOf = x.indexOf("BingoApp");
		indexOf += 8;
		x = x.substring(0, indexOf);
		String separator = File.separator;
		x = x += "" + separator + "bingoimages" + separator;
		System.out.println("file is " + x);
		return x;
	}

	public static String getBingoWebImageURL(byte[] objattach,String image) {
		String url2 = "http://www.fasttechinfo.biz:8080/BingoApp/bingoimages/";

		if(objattach==null)
		{
			return "";
		}
		
		try {
			long currentTimeMillis = System.currentTimeMillis();

			String filename = String.valueOf(currentTimeMillis + ".jpg");
			if(image.contains("png"))
			{
				filename = String.valueOf(currentTimeMillis + ".png");
			}
			

			FileInputStream fileInputStream = null;
			String golfImageAppPath = getBingoImageAppPath();
			
			FileOutputStream fileOuputStream = new FileOutputStream(
					golfImageAppPath + filename);
			fileOuputStream.write(objattach);
			fileOuputStream.close();
			
			return url2 +filename;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static Integer doSaveTblUserrootMaster(TblUserownMaster userownmaster, String userid,String socialtype,String rootid)
	{
		System.out.println("QuickUtil.doSaveTblUserrootMaster()");
		Integer userrootid = null;
		try {
			List<TblUserownMaster> retrieveWherClause = QuickUtil.retrieveWherClause(new TblUserownMaster(), "TblUserownMaster", "userownId='"+userid+"' AND userownSocialtype='"+socialtype+"' ");
			if(retrieveWherClause.size()>0)
			{
				userownmaster= retrieveWherClause.get(0);
				List<TblUserrootDetails> retrieveWherClause2 = QuickUtil.retrieveWherClause(new TblUserrootDetails(), "TblUserrootDetails", "tblUserownMaster.userownPk="+userownmaster.getUserownPk());
				if(retrieveWherClause2!=null && retrieveWherClause2.size()>0)
				{
					TblUserrootDetails tblUserrootDetails = retrieveWherClause2.get(0);
					userrootid=tblUserrootDetails.getTblUserrootMaster().getUserrootId();
				}
			}
			else if(socialtype.equals("facebook"))
			{
				Integer userpk = QuickUtil.doGetNextSequence("TblUserownMaster");
				userownmaster.setUserownPk(userpk);
				QuickUtil.saveToNew(userownmaster);
				
				TblUserrootMaster tblUserrootMaster = new TblUserrootMaster();
				Integer doGetNextSequence = QuickUtil.doGetNextSequence("TblUserrootMaster");
				tblUserrootMaster.setUserrootId(doGetNextSequence);
				tblUserrootMaster.setUserrootName(userpk+"");
				QuickUtil.saveToNew(tblUserrootMaster);
				
				 TblUserrootDetails tblUserrootDetails = new TblUserrootDetails();
				 Integer doGetNextSequence2 = QuickUtil.doGetNextSequence("TblUserrootDetails");
				 tblUserrootDetails.setUserrootdetailsId(doGetNextSequence2);
				 tblUserrootDetails.setTblUserownMaster(userownmaster);
				 tblUserrootDetails.setTblUserrootMaster(tblUserrootMaster);
				 QuickUtil.saveToNew(tblUserrootDetails);
				 
				userrootid=tblUserrootMaster.getUserrootId();
				
			}
			if(!(socialtype.equals("facebook")) && retrieveWherClause.size()==0)
			{
				
				//save in tbluserownmaster
				Integer userpk = QuickUtil.doGetNextSequence("TblUserownMaster");
				userownmaster.setUserownPk(userpk);
				QuickUtil.saveToNew(userownmaster);
				
				
				//Save in tblrootdetails
					TblUserrootDetails tblUserrootDetails = new TblUserrootDetails();
				 Integer doGetNextSequence2 = QuickUtil.doGetNextSequence("TblUserrootDetails");
				 tblUserrootDetails.setUserrootdetailsId(doGetNextSequence2);
				 tblUserrootDetails.setTblUserownMaster(userownmaster);
				 
				 
				 
				 TblUserrootMaster tblUserrootMaster = new TblUserrootMaster();
				 tblUserrootMaster.setUserrootId(Integer.valueOf(rootid));
				 tblUserrootDetails.setTblUserrootMaster(tblUserrootMaster);
				 tblUserrootDetails.setTblUserownMaster(userownmaster);
				 QuickUtil.saveToNew(tblUserrootDetails);
				 
			}
		} catch (Exception e) {
			System.out.println("Sorry error QuickUtil.doSaveTblUserrootMaster()"+e.getMessage());
			e.printStackTrace();
		}
		
		return userrootid;
		
	}
	
	/**
	 * To prevent unknown characters from saving  to databse we decode and encode strings
	 * @param value
	 * @return
	 */
	public static String decode(byte[] value)
	{
		if(value==null)
		{
			return "";
		}
		try {
			String s = new String(value, "UTF-8");
			return s;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	
	/**
	 * To prevent unknown characters from saving  to database we decode and encode strings
	 * @param value
	 * @return
	 */
	public static byte[] encode(String value)
	{
		if(value==null)
		{
			return null;
		}
		try {
			return value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
