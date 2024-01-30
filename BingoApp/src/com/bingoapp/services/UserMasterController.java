package com.bingoapp.services;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.bingoapp.db.TblAdminMaster;
import com.bingoapp.util.CommonParams2;
import com.bingoapp.util.QuickUtil;
import com.bingoapp.util.UserManagerUtil2;
 

public class UserMasterController implements Serializable{

	private String UUserName;
	private String UPass;
	TblAdminMaster tbladminmaster;

	public UserMasterController() {
		UUserName = "";
		UPass = "";
	}

	public String reset() {
		this.UUserName = "";
		this.UPass = "";
		// U_FullName="";
		// U_Tag="";
		return "loginpage";
	}

	public String checklogin() {
		if (UUserName == null || UUserName.trim().isEmpty() || UPass == null
				|| UPass.isEmpty()) {
			return reset();
		}
		try {
//			List<TblUserMaster> dataList = QuestionsUtil.retrieveWherClause(
//					new TblUserMaster(), "TblUserMaster", "U_UserName='"
//							+ UUserName + "' AND U_Pass='" + UPass + "' ");

			String admin="";
			String pass="";
			String whereclause="adminUsername='"+UUserName+"' AND adminPassword='"+UPass+"' AND adminStatus='active' ";
			List<TblAdminMaster> retrieveWherClause = QuickUtil.retrieveWherClause(new TblAdminMaster(), "TblAdminMaster", whereclause);
			if(retrieveWherClause.size()>0)
			{
				TblAdminMaster tblAdminMaster = retrieveWherClause.get(0);
				admin=tblAdminMaster.getAdminUsername();
				pass=tblAdminMaster.getAdminPassword();
				if(UUserName.equals(admin) && UPass.equals(pass))
				{		CommonParams2
							.showMessageOnScreen("Successfull Login for Admin!");
					tbladminmaster=tblAdminMaster;
					doLoadAccessData();
					return "index";//"successlogin";
					
				} 
			}
			
			
			//			if (dataList.size() > 0) {
 			 	System.out.println("BAD LOG");
		 	CommonParams2
					.showMessageOnScreen("Your Login was not successfull!,Please Reenter your credentials.");

			return reset();
 		} catch (Exception e) {
			System.out.println("checklogin method error:" + e.getMessage());
			
		}
		return "success";
	}
	
	/*
	 * Calls userManagerUtil2 to retrieve the data of user acccess roles ,etc.
	 */
	private void doLoadAccessData()
	{
		System.out.println("UserMasterController.doLoadAccessData()");
		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();
			UserManagerUtil2 retobj=null;
			
			HttpSession session = (HttpSession) ctx.getSession(false);
			String nameofsession="userManagerUtil2";
			if (session.getAttribute(nameofsession) != null) {
				System.out.println("Has userManagerUtil2 value in session");
				 retobj = (UserManagerUtil2) session.getAttribute(nameofsession);
				 retobj.doLoadUserManagerUtil2();
				 session.setAttribute(nameofsession, retobj);
			}
		} catch (Exception e) {
			System.err.println("Sorry error here "+e.getMessage());
			e.printStackTrace();
		}

	}

	public String updateLogin() {
		try {
			System.out.println("UserMasterController.updateLogin()");

			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();
			UserMasterController retobj=null;
			
			HttpSession session = (HttpSession) ctx.getSession(false);
			String nameofsession="userMaster";
			if (session.getAttribute(nameofsession) != null) {
				System.out.println("Has value in session");
				 retobj = (UserMasterController) session.getAttribute(nameofsession);
			}
			

			List<TblAdminMaster> retrieveWherClause = QuickUtil.
					retrieveWherClause(new TblAdminMaster(), "TblAdminMaster",
							"adminUsername='" + retobj.getUUserName() + "'");
			if (retrieveWherClause.size() > 0) {
				TblAdminMaster tblUserMaster = retrieveWherClause.get(0);
				tblUserMaster.setAdminPassword(UPass);
				QuickUtil.updateToOld(tblUserMaster);
				 

				System.out.println("Done Updating password");
			}
			return "loginpage";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}

	public String getUUserName() {
		return UUserName;
	}

	public void setUUserName(String uUserName) {
		UUserName = uUserName;
	}

	public String getUPass() {
		return UPass;
	}

	public void setUPass(String uPass) {
		UPass = uPass;
	}

	public TblAdminMaster getTbladminmaster() {
		return tbladminmaster;
	}

	public void setTbladminmaster(TblAdminMaster tbladminmaster) {
		this.tbladminmaster = tbladminmaster;
	}

}
