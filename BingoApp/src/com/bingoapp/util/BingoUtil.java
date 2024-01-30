package com.bingoapp.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;

public class BingoUtil {

	public static String getHTML(String urlToRead,String postorget) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(postorget);//"GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String getRedirectedURL(String urlToRead,String postorget) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(postorget);//"GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			result= conn.getURL().getQuery();//Path();
			rd.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String post(String urlSpec, String data) throws Exception {
	    URL url = new URL(urlSpec);
	    URLConnection connection = url.openConnection();
	    connection.setDoOutput(true);
	    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	    writer.write(data);
	    writer.flush();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line = "";
	    StringBuilder builder = new StringBuilder();
	    while((line = reader.readLine()) != null) {
	        builder.append(line);
	    }
	    return builder.toString();
	} 
	
	/**
	 * format was 
	 * access_token=368079809988416|RRXsePdB_DU9y_rhS0BYvfzIWu8
	 * @param ab
	 * @return
	 */
	public static String parseAccessToken(String ab)
	{
		int indexOf = ab.indexOf("=");
		String result = ab.replaceFirst("access_token=", " ");
		result=result.trim();
		return result;
	}
	/*
	public static String getHTMLPost(String url)
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://example.com/");
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", "Bob"));
		try {
		    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    // writing error to Log
		    e.printStackTrace();
		}
	}*/
}
