package com.bingoapp.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.bingoapp.services.UserMasterController;

;

public class CommonParams2 {

	public static String KEY_TRACKING2 = "KEY_TRACKING2";
	
	//below keys for showing hashmaps on per edit page showing tracking details
	public static String KEY_ADMINNAME="KEY_ADMINNAME";
//	public static String KEY_TIMESTARTED="KEY_TIMESTARTED";
//	public static String KEY_TIMESAVED="KEY_TIMESAVED";
//	public static String KEY_TRACKTIME="KEY_TRACKTIME";
//	
	
	
	/**
	 * @param newremtime
	 * @return
	 */
	public static String doConvertSecondsToCountdown2(Long newremtime) {
		String countremaining;
		long newminutes = newremtime / 60;
		long newseconds = newremtime % 60;
		if (newminutes < 10) {
			countremaining = "0" + newminutes;
		} else {
			countremaining = newminutes + "";
		}
		countremaining += ":";
		if (newseconds < 10) {
			countremaining += "0" + newseconds;
		} else {
			countremaining += newseconds + "";
		}
		return countremaining;
	}
	public static HttpSession doGetHttpSession() {
		System.out.println("CommonParams2.doGetHttpSession()");
		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();

			HttpSession session = (HttpSession) ctx.getSession(false);
			return session;
		} catch (Exception e) {
			System.out.println("CommonParams2.doGetCurrentUserMaster()");
			System.err.println("error here " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public boolean isAdmin() {
		System.out.println("CommonParams2.isAdmin()");
		UserMasterController doGetCurrentUserMaster = CommonParams2
				.doGetCurrentUserMaster();
		String adminUsername = doGetCurrentUserMaster.getTbladminmaster()
				.getAdminUsername();
		if (adminUsername.equals("admin")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Retrieves the current user from session.
	 */
	public static UserMasterController doGetCurrentUserMaster() {
		UserMasterController retobj = null;

		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();

			HttpSession session = (HttpSession) ctx.getSession(false);
			String nameofsession = "userMaster";
			if (session.getAttribute(nameofsession) != null) {
				retobj = (UserMasterController) session
						.getAttribute(nameofsession);

			}
		} catch (Exception e) {
			System.out.println("CommonParams2.doGetCurrentUserMaster()");
			System.err.println("error here " + e.getMessage());
			e.printStackTrace();
		}
		return retobj;

	}

	public static <T> boolean checkDuplicateEntryUpdate(T a, String tablename,
			String column, String oldid, String newid) {
		if (!oldid.equals(newid)) {
			List retrieveWherClause = QuickUtil.retrieveWherClause(a,
					tablename, column + " != '" + oldid + "' AND " + column
							+ " ='" + newid + "' ");
			if (retrieveWherClause.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * If user is not logged then redirect to loginpage
	 * 
	 * @param sessionname
	 * @param urltoredirect
	 */
	// public void checkCredentialsForLoggedOut(String sessionname,
	public boolean checkCredentialsForLoggedOut(String sessionname,
			String urltoredirect) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);

		if (session.getAttribute(sessionname) != null) {
			HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();

			if (sessionname.equals("userMaster")) {
				UserMasterController user2 = (UserMasterController) session
						.getAttribute(sessionname);

				if (user2.getUUserName() != null
						&& user2.getUUserName().isEmpty() == false) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkcredentials user2 is not null");
					return true;
				}
			}
			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working before redirect");
			// resp.sendRedirect(urltoredirect);
			try {
				ctx.redirect(urltoredirect);
			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedOut mehod cannot redirect");
				e.printStackTrace();
			}

			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working after redirect");

		} else {
			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working before redirect");
			// resp.sendRedirect(urltoredirect);
			try {
				ctx.redirect(urltoredirect);
			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedOut mehod cannot redirect");
				e.printStackTrace();
			}

		}
		return false;

	}

	/**
	 * If user is already logged in direct to the required page
	 * 
	 * @param sessionname
	 * @param urltoredirect
	 */
	public void checkCredentialsForLoggedIn(String sessionname,
			String urltoredirect) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();

		HttpSession session = (HttpSession) ctx.getSession(false);
		Map<String, Object> sessionMap = ctx.getSessionMap();
		for (String va2 : sessionMap.keySet()) {

			System.out.println(" Key is :" + va2 + " Value is :"
					+ sessionMap.get(va2));
		}
		if (session.getAttribute(sessionname) != null) {
			HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();

			if (sessionname.equals("userMaster")) {
				UserMasterController user2 = (UserMasterController) session
						.getAttribute(sessionname);
				if (user2.getUUserName() == null
						|| user2.getUUserName().isEmpty()) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials usermaster is null");
					return;
				}
			}
			try {
				CommonParams2.showMessageOnLog(getClass(),
						"checkCredentials working before redirect");
				// resp.sendRedirect(urltoredirect);
				ctx.redirect(urltoredirect);

				CommonParams2.showMessageOnLog(getClass(),
						"checkCredentials working after redirect");

			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedIn mehod cannot redirect");
				e.printStackTrace();
			}
		}

	}

	public static void showMessageOnScreen(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("growlkey", new FacesMessage(message, message));
	}

	public static void executeCommand(String message) {
		System.out.println("CommonParams2.showDialogBox()");
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		String command = message;
		if (currentInstance != null) {
			System.out.println("In Requestcontext method");
			currentInstance.execute(command);

		} else {
			System.err.println("Sorry Mith RequestContext is null");
		}
		System.out.println("Shown command from server = " + command);
	}

	public static void showAlertBox(String message) {
		System.out.println("CommonParams2.showAlertBox()");
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		String command = "alert('" + message + "')";
		if (currentInstance != null) {
			System.out.println("In Requestcontext method");
			currentInstance.execute(command);

		} else {
			System.err.println("Sorry Mith RequestContext is null");
		}
		System.out.println("Shown Alert from server = " + command);
	}

	/*
	 * Java Script to refresh page
	 */
	public static void refreshPage() {
		System.out.println("CommonParams2.refreshPage()");
		RequestContext currentInstance = RequestContext.getCurrentInstance();

		String command = "document.location.reload(true)";
		if (currentInstance != null) {
			System.out.println("In Requestcontext method");
			currentInstance.execute(command);

		} else {
			System.err.println("Sorry Mith RequestContext is null");
		}
		System.out.println("done calling refreshpage");
	}

	public static void showMessageOnLog(Class a, String message) {
		Logger l = Logger.getLogger(a.getName());
		l.setLevel(Level.INFO);
		// l.warning(message);
		l.info(message);
	}

	public static Boolean sendMail2(String emailto, String firstname) {
		final String username = "atulmith@gmail.com";
		final String password = "opentaps";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		String message2 = "Hello " + firstname + " , \n";
		message2 += "\n To Activate hit this URL: http://wwww.fasttechinfo.biz:8080/CaddyBook/1-start.jsp?UserId=5345&Activate=true";
		message2 += "\n To Deactivate hit this url: http://wwww.fasttechinfo.biz:8080/CaddyBook/1-start.jsp?UserId=893&Activate=false";
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("atulmith@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailto));
			message.setSubject("Testing Subject");
			message.setText(message2);

			Transport.send(message);

			System.out.println("Done");
			return true;

		} catch (Exception e) {
			System.err.println("Sorry sending error " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public String destroySessions(String e) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = ctx.getSessionMap();
		System.out.println("No of sessions =" + sessionMap.size());
		for (String key2 : sessionMap.keySet()) {

			System.out.println("Session name=" + sessionMap.get(key2));
			String packagename = sessionMap.get(key2).getClass().getPackage()
					.getName();
			HttpSession session = (HttpSession) ctx.getSession(false);
			if (sessionMap.get(key2) instanceof UserMasterController) {
				System.out.println("not Deleting user master controller");
				// session.removeAttribute(key2);
			} else if (packagename.contains("bingoapp.services")) {
				System.out.println("Deleting service =" + key2);
				session.removeAttribute(key2);
			}

		}
		return e;
		// return retvalue + "?redirect=true";
	}

	public static void deleteSessionName(String sessionname) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);
		session.removeAttribute(sessionname);
		System.out.println("Destroyed session " + sessionname + " isit="
				+ (session.getAttribute(sessionname) == null));
	}
}
