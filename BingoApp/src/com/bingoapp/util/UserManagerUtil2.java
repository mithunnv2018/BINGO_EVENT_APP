package com.bingoapp.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.bingoapp.db.TblAdminMaster;
import com.bingoapp.services.UserMasterController;

public class UserManagerUtil2 implements Serializable {

 
	public UserManagerUtil2() {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@PostConstruct
	public void doLoadUserManagerUtil2() {
		System.out.println("UserManagerUtil2.doLoadUserManagerUtil2()");
		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();
			UserMasterController retobj = null;

 
			HttpSession session = (HttpSession) ctx.getSession(false);
			String nameofsession = "userMaster";
			if (session.getAttribute(nameofsession) != null) {
				System.out.println("Has value in session");
				retobj = (UserMasterController) session
						.getAttribute(nameofsession);

			}
		} catch (Exception ex) {
			CommonParams2.showMessageOnLog(this.getClass(), ex.getMessage());
			CommonParams2
					.showAlertBox("Something gone wrong , please refresh page");
			ex.printStackTrace();
		}
	}

}
