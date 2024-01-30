package com.appbinggo.bingoappandroid;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MyWebService {

	public static String callwebserviceforupdatefacebookeventList(String tokenaccess,String userid) {

		System.out
				.println("MyWebService.callwebserviceforupdatefacebookeventList()");
//		System.out.println("MyWebService.callwebserviceforeventList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doUpdateFacebookevents";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL = "http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl";
		
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/DemoLoginFacebook?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("tokenaccess", tokenaccess);
		request.addProperty("userid",userid);
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,120000);

		try {
			System.out.println("hi mith before Call update facebook events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  update facebook events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create update facebook events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out.println("MyWebService.callwebserviceforeventList()");
			System.err.println("Sorry err at  get facebook events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
		// System.err.println("Something went wrong");
		// return null;
	}

	/**
	 * To retrieve comments for that event
	 * @param userrootid 
	 *
	 * @param eventid
	 * @return
	 */
	public static String callwebserviceforeventList(String userrootid) {

		System.out.println("MyWebService.callwebserviceforeventList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doRetreiveEventsOnly";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL ="http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl"; 
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/DemoLoginFacebook?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userrootid", userrootid);
//		request.addProperty("userid",userid);
//		request.addProperty("eventid",eventid);
		
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);

		try {
			System.out.println("hi mith before Call get all events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  get all events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create get all events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out.println("MyWebService.callwebserviceforeventList()");
			System.err.println("Sorry err at  get all events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
		// System.err.println("Something went wrong");
		// return null;
	}

	public static String callwebserviceforadmineventList() {

		System.out.println("MyWebService.callwebserviceforadmineventList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doRetrieveEventsForAdmin";//doRetreiveEventsOnly";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL ="http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl"; 
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/DemoLoginFacebook?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		request.addProperty("userrootid", userrootid);
//		request.addProperty("userid",userid);
//		request.addProperty("eventid",eventid);
		
		System.out.println("Params are admineventList "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);

		try {
			System.out.println("hi mith before Call get admineventList ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  get admineventList" + response);
			if (response == null) {
				System.out
						.println("Hi mith create get admineventList soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out
					.println("MyWebService.callwebserviceforadmineventList()");
			System.err.println("Sorry err at  get all admineventList "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
		// System.err.println("Something went wrong");
		// return null;
	}

	/**
	 * To retrieve comments for that event
	 *
	 * @param eventid
	 * @param userrootid 
	 * @param eventype 
	 * @return
	 */
	public static String callwebserviceforeventcommentsList(String eventid, String userrootid, String eventtype) {

		System.out.println("MyWebService.callwebserviceforeventcommentsList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doRetrieveEventDetails";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL ="http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl";
		
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/DemoLoginFacebook?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("eventid",eventid);
		request.addProperty("userrootid",userrootid);
		request.addProperty("eventtype",eventtype);
		
		//		request.addProperty("userid",userid);
	
		
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);

		try {
			System.out.println("hi mith before Call get all comments of events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  get all  comments of events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create get all comments of  events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out
					.println("MyWebService.callwebserviceforeventcommentsList()");
			System.err.println("Sorry err at  get all comment of events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
		// System.err.println("Something went wrong");
		// return null;
	}

	public static String callwebserviceforupdatetwittereventList(String accesstoken2,
			String secretaccesstoken2,Integer userrootid) {
		System.out
				.println("MyWebService.callwebserviceforupdatetwittereventList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doUpdateTwitterevents";//doRetrieveTwitterEvents";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL = "http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl";
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/LoginAndEventsTwitter?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("token", accesstoken2);
		request.addProperty("tokensecret",secretaccesstoken2);
		request.addProperty("userrootid",userrootid); 
		
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);

		try {
			System.out.println("hi mith before Call update Twitter  events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after update Twitter  events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create update Twitter events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out
					.println("MyWebService.callwebserviceforupdatetwittereventList()");
			System.err.println("Sorry err at  get Twitter of events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
	}

	public static String callwebserviceforupdateinstagrameventList(String token, String userid,Integer userrootid) {
		
		System.out
				.println("MyWebService.callwebserviceforupdateinstagrameventList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doUpdateInstagramEvents";//doRetrieveTwitterEvents";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL = "http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl";
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/LoginAndEventsTwitter?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("token", token);
		request.addProperty("userid",userid);
		request.addProperty("userrootid",userrootid); 
		
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			System.out.println("hi mith before Call update Instagram  events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  update Instagram  events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create update Instagram events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out
					.println("MyWebService.callwebserviceforupdateinstagrameventList()");
			System.err.println("Sorry err at  get Instagram of events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
	}
	
	public static String callwebservicefordoRetrieveFacebookeventsWithLatiLongi(String tokenaccess,String userid, String lati, String longi) {

		System.out
				.println("MyWebService.callwebservicefordoRetrieveFacebookeventsWithLatiLongi()");
//		System.out.println("MyWebService.callwebserviceforeventList()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doRetrieveFacebookeventsWithLatiLongi";//doUpdateFacebookevents";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL = "http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl";
		
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/DemoLoginFacebook?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("tokenaccess", tokenaccess);
		request.addProperty("userid",userid);
		request.addProperty("lati",lati);
		request.addProperty("longi",longi);
		
		
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,120000);

		try {
			System.out.println("hi mith before Call update facebook events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  update facebook events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create update facebook events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.out.println("MyWebService.callwebserviceforeventList()");
			System.err.println("Sorry err at  get facebook events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
		// System.err.println("Something went wrong");
		// return null;
	}
	
	public static String callwebservicefordoRetrieveFacebookeventcommentsListWithLatiLongi(String tokenaccess,String userid, String eventid, String userrootid) {

		System.out
				.println("MyWebService.callwebservicefordoRetrieveFacebookeventcommentsListWithLatiLongi()");
		final String NAMESPACE = "http://bingoapp.com";//http://app.caddybook.com";
		final String METHOD_NAME = "doRetrieveFacebookeventscommentsLatiLongi";//doRetrieveEventDetails";//doRetrieveFacebookevents";
															
		final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		final String URL ="http://www.fasttechinfo.biz:8080/BingoApp/services/EventsFromSocialNetworks?wsdl";
		
//				"http://www.fasttechinfo.biz:8080/BingoApp/services/DemoLoginFacebook?wsdl";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("tokenaccess",tokenaccess);
		request.addProperty("userid",userid);
		request.addProperty("eventid",eventid);
		request.addProperty("userrootid",userrootid);
		
		
		//		request.addProperty("userid",userid);
	
		
		System.out.println("Params are "+request.toString()); 
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String response = "";
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,50000);

		try {
			System.out.println("hi mith before Call get all comments of events ");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Vector response = (Vector) envelope.getResponse();
			// SoapObject response = (SoapObject)
			// envelope.bodyIn;//getResponse();//bodyIn;
			SoapPrimitive r = (SoapPrimitive) envelope.getResponse();// bodyIn;

			response = r.toString();
			System.out.println("Hi mith after  get all  comments of events" + response);
			if (response == null) {
				System.out
						.println("Hi mith create get all comments of  events soapobj null");
				return null;
			}

			return response;
		} catch (Exception ex) {
			System.err
					.println("MyWebService.callwebservicefordoRetrieveFacebookeventcommentsListWithLatiLongi()");
			System.err.println("Sorry err at  get all comment of events "
					+ ex.getMessage());
			ex.printStackTrace();

			// String error=demoLoadCourseDetails();
			return null;
			// "error"+ex.getMessage();//error.toString();
		}
		// System.err.println("Something went wrong");
		// return null;
	}

}
