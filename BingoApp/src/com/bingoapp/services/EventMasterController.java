package com.bingoapp.services;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.bingoapp.db.Tbleventdetails;
import com.bingoapp.util.CommonParams2;
import com.bingoapp.util.QuickUtil;
 
public class EventMasterController implements Serializable{

	private String eventName;
	String eventDesc;
	String eventPlace;
	String eventlatilongi;
	Date eventDate;
	String adminpk;
	String eventType="admin";
	DefaultStreamedContent eventImage;
	DefaultStreamedContent eventImageduplicate;
	private UploadedFile uploadedFileEventImage;
	private Date eventStartDate;
	 
	private Date eventEndDate;
	 
	
	
	private ArrayList<Tbleventdetails> listofoldeventdetailsmaster;
	private String uploadedfilename;
	
	byte [] eventdescbinary=null;
	
	 
	
	public EventMasterController()
	{
		
		eventName="";
		eventDesc="";
		eventPlace="";
		eventlatilongi="";
		eventDate=null;
		adminpk="";
		eventType="admin";
		eventImage=null;
		eventImageduplicate=null;
		uploadedFileEventImage=null;
		listofoldeventdetailsmaster=new ArrayList<Tbleventdetails>();
		doLoadOldEventMaster();
	}
	
	
	
	public void doLoadOldEventMaster()
	{
		System.out.println("EventMasterController.doLoadOldEventMaster()");
		try {
			
			listofoldeventdetailsmaster.clear();
			@SuppressWarnings("unchecked")
			List<Tbleventdetails> retrieveALLwithHB = QuickUtil.retrieveALLwithHB(new Tbleventdetails(), "Tbleventdetails", "");
			
			listofoldeventdetailsmaster.addAll(retrieveALLwithHB);

			eventName="";
			eventDesc="";
			eventPlace="";
			eventlatilongi="";
			eventDate=null;
			adminpk="";
			eventType="admin";
			eventImage=null;
			uploadedFileEventImage=null;
			uploadedfilename=null;
			eventStartDate=null;
			 
			eventEndDate=null;
			eventdescbinary=null; 
			
 
 		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
			e.printStackTrace();
		}
		
	}
	
	public void doUploadImage2(FileUploadEvent event) {
		// org.primefaces.component.graphicimage.GraphicImage abc=new
		// org.primefaces.component.graphicimage.GraphicImage();

		System.out.println("EventMasterController.doUploadImage2()");
		try {
			uploadedFileEventImage = event.getFile();
			InputStream inputstream = event.getFile().getInputstream();
			String contentType = event.getFile().getContentType();
//			DefaultStreamedContent mapsimage2 = mapsimage;
//			mapsimage2 = new DefaultStreamedContent(inputstream, contentType); 
//			mapsimage = mapsimage2;// new
									// DefaultStreamedContent(inputstream,contentType);
			eventImage=new DefaultStreamedContent();
			eventImage.setContentType(contentType);
			eventImage.setName(uploadedFileEventImage.getFileName());//"temp");
			eventImage.setStream(inputstream);
			
			uploadedfilename=uploadedFileEventImage.getFileName();
//			CommonParams2.showAlertBox("File name is"+uploadedfilename);
//			backgroundMapImage.setValue(event.getFile().getContents());
			// mapsimage);

		} catch (Exception e) {
			System.out
					.println("Sorry error at EventMasterController.doUploadImage2() "
							+ e.getMessage());
			e.printStackTrace();
		}
	}
	
 	public String doSaveEventMaster()
	{
		System.out.println("EventMasterController.doSaveEventMaster()");
		try {
			if(eventDesc.trim().isEmpty() || eventName.trim().isEmpty())
			{

				CommonParams2.showAlertBox("You have to enter event details below");
				return "";
			}
			
			Integer doGetNextSequence = QuickUtil.doGetNextSequence("Tbleventdetails");
			
 			UserMasterController usermaster = CommonParams2.doGetCurrentUserMaster();
 			
 			Tbleventdetails obj=new Tbleventdetails();
 			obj.setEventDate(new Date());
 			obj.setEventDesc(QuickUtil.encode(eventDesc));
 			obj.setEventId(doGetNextSequence);
 			if(uploadedFileEventImage!=null)
 			{
 				obj.setEventImg(uploadedFileEventImage.getContents());
 				obj.setEventImgType(uploadedFileEventImage.getContentType());
 			}
 			
 			obj.setEventName(QuickUtil.encode(eventName));
 			obj.setEventPlace(eventPlace);
 			if(eventlatilongi.contains(","))
 			{
 				try {
					String[] split = eventlatilongi.split(",");
					Double lati=Double.parseDouble(split[0]);
					Double longi=Double.parseDouble(split[1]);
					
					obj.setEventLatitude(lati);
					obj.setEventLongitude(longi);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 			}
 			obj.setEventPlaceLatilongi(eventlatilongi);
 			obj.setEventType(eventType);
 			obj.setAdminPk(usermaster.getTbladminmaster().getAdminPk());
 			
 			System.out.println("Hi mith start date "+eventStartDate);
 			obj.setEventStartDate(eventStartDate);
 			 
 			obj.setEventEndDate(eventEndDate);
 			 
// 			obj.setEventFbId(eventFbId);
 			
			QuickUtil.saveToNew(obj);
			doLoadOldEventMaster();
//			return "index.jsf";
			return "editcreateeventmaster.jsf";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}



	public String getEventName() {
		return eventName;
	}



	public void setEventName(String eventName) {
		this.eventName = eventName;
	}



	public String getEventDesc() {
		return eventDesc;
	}



	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}



	public String getEventPlace() {
		return eventPlace;
	}



	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}



	public String getEventlatilongi() {
		return eventlatilongi;
	}



	public void setEventlatilongi(String eventlatilongi) {
		this.eventlatilongi = eventlatilongi;
	}



	public Date getEventDate() {
		return eventDate;
	}



	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}



	public String getAdminpk() {
		return adminpk;
	}



	public void setAdminpk(String adminpk) {
		this.adminpk = adminpk;
	}



	public String getEventType() {
		return eventType;
	}



	public void setEventType(String eventType) {
		this.eventType = eventType;
	}



	public DefaultStreamedContent getEventImage() {
		return eventImage;
	}



	public void setEventImage(DefaultStreamedContent eventImage) {
		this.eventImage = eventImage;
	}



	public DefaultStreamedContent getEventImageduplicate() {
		return eventImageduplicate;
	}



	public void setEventImageduplicate(DefaultStreamedContent eventImageduplicate) {
		this.eventImageduplicate = eventImageduplicate;
	}



	public UploadedFile getUploadedFileEventImage() {
		return uploadedFileEventImage;
	}



	public void setUploadedFileEventImage(UploadedFile uploadedFileEventImage) {
		this.uploadedFileEventImage = uploadedFileEventImage;
	}



	public ArrayList<Tbleventdetails> getListofoldeventdetailsmaster() {
		return listofoldeventdetailsmaster;
	}



	public void setListofoldeventdetailsmaster(
			ArrayList<Tbleventdetails> listofoldeventdetailsmaster) {
		this.listofoldeventdetailsmaster = listofoldeventdetailsmaster;
	}



	public String getUploadedfilename() {
		return uploadedfilename;
	}



	public void setUploadedfilename(String uploadedfilename) {
		this.uploadedfilename = uploadedfilename;
	}



	public Date getEventStartDate() {
		return eventStartDate;
	}



	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}



	 


	public Date getEventEndDate() {
		return eventEndDate;
	}



	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}



	 

  
}
