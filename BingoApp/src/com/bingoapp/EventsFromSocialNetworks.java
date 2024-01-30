package com.bingoapp;

public class EventsFromSocialNetworks {

	public String  doUpdateFacebookevents(String tokenaccess, String userid)
	{
		try {
			DemoLoginFacebook login=new DemoLoginFacebook();
			return login.doRetrieveFacebookevents(tokenaccess,userid);
		} catch (Exception e) {
			System.out
					.println("Sorrry error EventsFromSocialNetworks.doUpdateFacebookevents()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String  doRetrieveFacebookeventsWithLatiLongi(String tokenaccess, String userid, String lati, String longi)
	{
		try {
			DemoLoginFacebook login=new DemoLoginFacebook();
			return login.doRetrieveFacebookeventsLatiLongi(tokenaccess, userid, lati, longi);
		} catch (Exception e) {
			System.out
					.println("Sorrry error EventsFromSocialNetworks.doUpdateFacebookevents()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String doRetrieveFacebookeventscommentsLatiLongi(String tokenaccess,String userid, String eventid, String userrootid)	
	{
		try 
		{
			DemoLoginFacebook login=new DemoLoginFacebook();
			return login.doRetrieveFacebookeventscommentsLatiLongi(tokenaccess, userid, eventid, userrootid);
		} catch (Exception e) {
			System.out
					.println("Sorry error EventsFromSocialNetworks.doRetrieveFacebookeventscommentsLatiLongi()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String doUpdateTwitterevents(String token, String tokensecret,Integer userrootid )
	{
		try {
			LoginAndEventsTwitter t=new LoginAndEventsTwitter();
			String result=t.doCallFromThreadTwitterUpdate(token,tokensecret,userrootid);
			System.out.println("Result="+result);
			return result;
		} catch (Exception e) {
			System.out
					.println("Sorry error EventsFromSocialNetworks.doUpdateTwitterevents()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String doUpdateInstagramEvents(String token, String userid,Integer userrootid )
	{
		try {
			LoginAndEventsInstagram i=new LoginAndEventsInstagram();
			String result = i.doCallFromThreadInstagramUpdate(token,userid,userrootid);
			System.out.println("result ="+result);
			return result;
		} catch (Exception e) {
			System.out
					.println("Sorry error EventsFromSocialNetworks.doUpdateInstagramEvents()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String doRetreiveEventsOnly(String userrootid)
	{
		System.out.println("EventsFromSocialNetworks.doRetreiveEventsOnly()");
		try {
			RetrieveEventDetails events=new RetrieveEventDetails();
			return events.doRetrieveEvents(userrootid);
		} catch (Exception e) {
			System.out
					.println("Sorrry error EventsFromSocialNetworks.doRetreiveEventsOnly()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String doRetrieveEventDetails(String eventid,String userrootid, String eventtype)
	{
		System.out.println("EventsFromSocialNetworks.doRetrieveEventDetails()");
		try {
			RetrieveEventDetails events=new RetrieveEventDetails();
			return events.doRetrieveEventDetails(eventid,userrootid,eventtype);		
		} catch (Exception e) {
			System.out.println("Sorry error EventsFromSocialNetworks.doRetrieveEventDetails()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String doRetrieveEventsForAdmin()
	{
		System.out
				.println("EventsFromSocialNetworks.doRetrieveEventsForAdmin()");
		try {
			RetrieveEventDetails events=new RetrieveEventDetails();
			return events.doRetrieveEventsForAdmin();
		} catch (Exception e) {
			System.out
					.println("Sorry error here EventsFromSocialNetworks.doRetrieveEventsForAdmin()"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
