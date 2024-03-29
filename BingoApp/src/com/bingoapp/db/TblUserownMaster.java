package com.bingoapp.db;

// Generated 12 Sep, 2013 6:34:44 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TblUserownMaster generated by hbm2java
 */
public class TblUserownMaster implements java.io.Serializable {

	private int userownPk;
	private String userownName;
	private String userownUsername;
	private String userownLocation;
	private String userownLatilongi;
	private String userownEmailid;
	private String userownPhoneno;
	private String userownAddress;
	private String userownPincode;
	private byte[] userownPhoto;
	private String userownSocialtype;
	private String userownId;
	private Double userownLatitude;
	private Double userownLongitude;
	private String userownImageContentType;
	private Set<TblUserrootDetails> tblUserrootDetailses = new HashSet<TblUserrootDetails>(
			0);

	public TblUserownMaster() {
	}

	public TblUserownMaster(int userownPk) {
		this.userownPk = userownPk;
	}

	public TblUserownMaster(int userownPk, String userownName,
			String userownUsername, String userownLocation,
			String userownLatilongi, String userownEmailid,
			String userownPhoneno, String userownAddress,
			String userownPincode, byte[] userownPhoto,
			String userownSocialtype, String userownId, Double userownLatitude,
			Double userownLongitude, String userownImageContentType,
			Set<TblUserrootDetails> tblUserrootDetailses) {
		this.userownPk = userownPk;
		this.userownName = userownName;
		this.userownUsername = userownUsername;
		this.userownLocation = userownLocation;
		this.userownLatilongi = userownLatilongi;
		this.userownEmailid = userownEmailid;
		this.userownPhoneno = userownPhoneno;
		this.userownAddress = userownAddress;
		this.userownPincode = userownPincode;
		this.userownPhoto = userownPhoto;
		this.userownSocialtype = userownSocialtype;
		this.userownId = userownId;
		this.userownLatitude = userownLatitude;
		this.userownLongitude = userownLongitude;
		this.userownImageContentType = userownImageContentType;
		this.tblUserrootDetailses = tblUserrootDetailses;
	}

	public int getUserownPk() {
		return this.userownPk;
	}

	public void setUserownPk(int userownPk) {
		this.userownPk = userownPk;
	}

	public String getUserownName() {
		return this.userownName;
	}

	public void setUserownName(String userownName) {
		this.userownName = userownName;
	}

	public String getUserownUsername() {
		return this.userownUsername;
	}

	public void setUserownUsername(String userownUsername) {
		this.userownUsername = userownUsername;
	}

	public String getUserownLocation() {
		return this.userownLocation;
	}

	public void setUserownLocation(String userownLocation) {
		this.userownLocation = userownLocation;
	}

	public String getUserownLatilongi() {
		return this.userownLatilongi;
	}

	public void setUserownLatilongi(String userownLatilongi) {
		this.userownLatilongi = userownLatilongi;
	}

	public String getUserownEmailid() {
		return this.userownEmailid;
	}

	public void setUserownEmailid(String userownEmailid) {
		this.userownEmailid = userownEmailid;
	}

	public String getUserownPhoneno() {
		return this.userownPhoneno;
	}

	public void setUserownPhoneno(String userownPhoneno) {
		this.userownPhoneno = userownPhoneno;
	}

	public String getUserownAddress() {
		return this.userownAddress;
	}

	public void setUserownAddress(String userownAddress) {
		this.userownAddress = userownAddress;
	}

	public String getUserownPincode() {
		return this.userownPincode;
	}

	public void setUserownPincode(String userownPincode) {
		this.userownPincode = userownPincode;
	}

	public byte[] getUserownPhoto() {
		return this.userownPhoto;
	}

	public void setUserownPhoto(byte[] userownPhoto) {
		this.userownPhoto = userownPhoto;
	}

	public String getUserownSocialtype() {
		return this.userownSocialtype;
	}

	public void setUserownSocialtype(String userownSocialtype) {
		this.userownSocialtype = userownSocialtype;
	}

	public String getUserownId() {
		return this.userownId;
	}

	public void setUserownId(String userownId) {
		this.userownId = userownId;
	}

	public Double getUserownLatitude() {
		return this.userownLatitude;
	}

	public void setUserownLatitude(Double userownLatitude) {
		this.userownLatitude = userownLatitude;
	}

	public Double getUserownLongitude() {
		return this.userownLongitude;
	}

	public void setUserownLongitude(Double userownLongitude) {
		this.userownLongitude = userownLongitude;
	}

	public String getUserownImageContentType() {
		return this.userownImageContentType;
	}

	public void setUserownImageContentType(String userownImageContentType) {
		this.userownImageContentType = userownImageContentType;
	}

	public Set<TblUserrootDetails> getTblUserrootDetailses() {
		return this.tblUserrootDetailses;
	}

	public void setTblUserrootDetailses(
			Set<TblUserrootDetails> tblUserrootDetailses) {
		this.tblUserrootDetailses = tblUserrootDetailses;
	}

}
