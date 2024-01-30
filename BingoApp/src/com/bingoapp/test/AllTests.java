package com.bingoapp.test;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bingoapp.DemoLoginFacebook;
import com.bingoapp.EventsFromSocialNetworks;
import com.bingoapp.LoginAndEventsInstagram;
import com.bingoapp.LoginAndEventsTwitter;
import com.bingoapp.RetrieveEventDetails;
import com.bingoapp.util.QuickUtil;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;

public class AllTests {

	@Test
	public void test11()
	{
		LoginAndEventsTwitter t=new LoginAndEventsTwitter();
		String lat="18.991403";
		String lon="72.814379";
		t.doRetrieveTwitterEventsWithLatiLongi(lat, lon);
		
	}
	
	
	public void test10()
	{
		String appId=DemoLoginFacebook.app_id;
		String appSecret=DemoLoginFacebook.app_secret;
		AccessToken obtainAppAccessToken = new DefaultFacebookClient().obtainAppAccessToken(appId, appSecret);
		
		String accessToken = obtainAppAccessToken.getAccessToken();
		
		if(accessToken.contains("|"))
		{
			String replace = accessToken.replace('|',':');
			String[] split = replace.split(":");
			for (String row : split) {
				System.out.println(row);
			}
			accessToken=split[1];
		}
		System.out.println("Access token is = "+accessToken);
	}
	
	public void test9()
	{
		String appId=DemoLoginFacebook.app_id;
		String appSecret=DemoLoginFacebook.app_secret;
		AccessToken obtainAppAccessToken = new DefaultFacebookClient().obtainAppAccessToken(appId, appSecret);
		
		String accessToken = obtainAppAccessToken.getAccessToken();
		
		if(accessToken.contains("|"))
		{
			String replace = accessToken.replace('|',':');
			String[] split = replace.split(":");
			for (String row : split) {
				System.out.println(row);
			}
			accessToken=split[1];
		}
		
		DemoLoginFacebook f=new DemoLoginFacebook();
		String lati="18.991403";
		String longi="72.814379";
		String resultoflatilongi = f.doRetrieveFacebookeventsLatiLongi(accessToken, "", lati, longi);
		System.out.println("Result is "+resultoflatilongi);
	}
	
	public void test7()
	{
		String abc="This is my world good";
		byte[] encode = QuickUtil.encode(abc);
		System.out.println("byte "+encode.length);
		String decode = QuickUtil.decode(encode);
		System.out.println("Decode "+decode);
	}
	
	public void test8()
	{
		System.out.println("AllTests.test8()");
		EventsFromSocialNetworks p=new EventsFromSocialNetworks();
		String result = p.doRetrieveEventDetails("32", "2", "admin");
		System.out.println("result is "+result);
	}
	public void test1() {
		System.out.println("AllTests.test1()");
		DemoLoginFacebook login = new DemoLoginFacebook();
		login.doRetrieveFacebookevents(
				"CAACEdEose0cBAOb5ydwoYtZA1HnY59WXzIJT8a9Gwh9ZBZAxUoiRncoOWKk34xOAXK8ZBWnbIpZCZBZCTmcFKBJgMwmZBwVkaxjVTDF94dA8vr8vOYipt7NXkIsjjxUOoShK6h64YpmF1MaBneALWW0DA67XxKeDDOvBCdDLna2TXu0hWeZBGZBJihAq82QaVRelIZD",
				"100000666207852");
		// String doLoginFacebook = login.doLoginFacebook();
		// System.out.println(doLoginFacebook);
	}

	
	public void test2() {
		System.out.println("AllTests.test2()");
		LoginAndEventsTwitter t = new LoginAndEventsTwitter();
		String result = t.doRetrieveTwitterEvents(
				"1712240888-593ocqEE2XdEx8MSHqAv7yHgnmxvbjcRfMhSRQf",
				"scp8GGkFN6K3Eh4R6QrnIrnskNMqOOz3YKEYWGUhQ8",3);
		System.out.println("Result=" + result);
	}

	
	public void test12()
	{
		Object row[]={1,2.2};
		
		String commentpk=(String) String.valueOf(row[0]);
		String distance=(String)  String.valueOf(row[1]);
		System.out.println("Comment pk is "+commentpk+"  "+distance);
	}
	
	public void test3() {
		System.out.println("AllTests.test3()");
		LoginAndEventsInstagram i = new LoginAndEventsInstagram();
		String result = i.doRetrieveInstagramEvents(
				"535684036.e28c5df.c556425292a6488b897300ce24650b41",
				"535684036",3);
		System.out.println("result =" + result);
	}

	
	public void test4() {
		try {

			String eventid = "279198395504627";//203069629825348";
			RetrieveEventDetails evt = new RetrieveEventDetails();
			String result = evt.doRetrieveEvents("3");
//					.doRetrieveEventDetails(eventid, "3");
//			String result = evt.doRetrieveEventDetails(eventid);
			System.out.println("Result is " + result);
		} catch (Exception e) {
			System.err.println("Sorry eror AllTests.test4()" + e.getMessage());
			e.printStackTrace();
		}
	}

	 
	public void test5() {

		System.out.println("GamePlay.getGolfImageAppPath()");
		URL url = getClass().getResource("AllTests.class");
				//.getCanonicalName());
		File file = new File(url.getPath());
		String x = file.getAbsolutePath().toString();
		System.out.println("Wholepath"+x);
		int indexOf = x.indexOf("BingoApp");
		indexOf+=8;
		x=x.substring(0, indexOf);
		String separator = File.separator;
		x=x+=""+separator+"bingoimages";
		System.out.println("file is "+x);
		System.out.println("Real path="+QuickUtil.getBingoImageAppPath());
	}
	
	
	public void test6()
	{
		RetrieveEventDetails ret=new RetrieveEventDetails();
//		String doRetrieveEvents = ret.doRetrieveEvents();
//		System.out.println("All event s "+doRetrieveEvents);
	}
}
