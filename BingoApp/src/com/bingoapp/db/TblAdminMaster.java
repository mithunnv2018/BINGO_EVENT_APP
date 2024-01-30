package com.bingoapp.db;

// Generated 12 Sep, 2013 6:34:44 PM by Hibernate Tools 3.4.0.CR1

/**
 * TblAdminMaster generated by hbm2java
 */
public class TblAdminMaster implements java.io.Serializable {

	private Integer adminPk;
	private String adminUsername;
	private String adminPassword;
	private String adminStatus;
	private String adminEmailid;

	public TblAdminMaster() {
	}

	public TblAdminMaster(String adminUsername, String adminPassword,
			String adminStatus) {
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.adminStatus = adminStatus;
	}

	public TblAdminMaster(String adminUsername, String adminPassword,
			String adminStatus, String adminEmailid) {
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.adminStatus = adminStatus;
		this.adminEmailid = adminEmailid;
	}

	public Integer getAdminPk() {
		return this.adminPk;
	}

	public void setAdminPk(Integer adminPk) {
		this.adminPk = adminPk;
	}

	public String getAdminUsername() {
		return this.adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return this.adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminStatus() {
		return this.adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getAdminEmailid() {
		return this.adminEmailid;
	}

	public void setAdminEmailid(String adminEmailid) {
		this.adminEmailid = adminEmailid;
	}

}
