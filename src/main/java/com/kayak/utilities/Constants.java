package com.kayak.utilities;

import org.apache.log4j.Logger;

public class Constants {
	
	Logger log = Logger.getLogger(getClass().getSimpleName());
	//Environment and Running app (to avoid driver.quit error)
	public static final String sConstEnvironment="PROD",sAutomationAPI="API",sAutomationWeb="Web";
	
	public static final int iHTTPCode201=201,iHTTPCode400=400,iHTTPCode200=200,iHTTPCode203=203,iHTTPCode404=404,
			iHTTPCode401=401,iHTTPCode405=405,iHTTPCode418=418,iHTTPCode403=403;
	public static final String sContentType="application/json";
	public static String sToken=""; 
	public static String sApiURL="";
	public static String sAppURL="";
	public String settingUpAppURL() throws Exception{
		sAppURL=System.getProperty("URL");
		if(sAppURL.toLowerCase().trim().contains("kayak"))
			return System.getProperty("URL");
		/*else if(System.getProperty("Environment").toLowerCase().trim().startsWith("dev"))
			return "https://dev-tekarch.firebaseapp.com/";
			else if(System.getProperty("Environment").toLowerCase().trim().startsWith("qa"))
			return "https://qa-tekarch.firebaseapp.com/";
		*/
		
		log.info("wrong URL for kayak please set environment correctly in property file");
		return "set environment correctly...";
	}
	
}
