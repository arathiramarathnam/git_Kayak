package com.kayak.utilities;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.kayak.listenersandextentreports.ExtentManager;

@Listeners(com.kayak.listenersandextentreports.TestListener.class)
public class TestBase {
	public static CommonUtilities oCommUtil = new CommonUtilities();
	public static BrowserUtilities oBroUti = new BrowserUtilities();
	public static Constants oCons = new Constants();
	public static ExtentManager oExt = new ExtentManager();
	
	Logger log = Logger.getLogger(getClass().getSimpleName());
	public static HashMap<String, String> sMapAuthorizationHeader = new HashMap<String, String>();
	public WebDriver driver;
	public static String sHost;
	public static String sScreenShotName;
	public static String sClassNameForScreenShot;
	public static String sErrorMessage="";
	
	@BeforeSuite
	public void TriggerDependencies() throws Exception {
		oCommUtil.deleteScreenShotDirectory();
		oCommUtil.loadPropertyFiles(System.getProperty("user.dir") + "/src/main/java/com/kayak/configurations/config.properties");
		oCommUtil.loadLog4jProperty(System.getProperty("user.dir") + "/src/main/java/com/kayak/configurations/log4j.properties");
		oCommUtil.loadPropertyFiles(System.getProperty("user.dir") + "/resources/testdata/testdata.properties");
		oCommUtil.deleteCreateScreenShotDirectorySureFireReports();
		
	/*	if(System.getProperty("AutomationRunning") == null || System.getProperty("AutomationRunning").isEmpty())
			System.setProperty("AutomationRunning", System.getProperty("AutomationRunningConf"));
	*/	if(System.getProperty("Environment") == null || System.getProperty("Environment").isEmpty())
			System.setProperty("Environment", System.getProperty("EnvironmentConf"));
		
		log.info(System.getProperty("AutomationRunning"));
		log.info(System.getProperty("Environment"));
		
		if(System.getProperty("AutomationRunning").equalsIgnoreCase(Constants.sAutomationAPI))
		{
			if(System.getProperty("Environment").equalsIgnoreCase("staging"))
				sHost=System.getProperty("StageHost"); //StageHost
			else
				throw new Exception("Environment is not found OR Give the RIGHT Environment");
			System.out.println(sHost);
			//oResUtil.loginToTekarchAndGenerateToken();
			
		}
		if(System.getProperty("AutomationRunning").equalsIgnoreCase(Constants.sAutomationWeb.toLowerCase()))
		{
			driver=oBroUti.launchBrowser(System.getProperty("Browser"));
		}
	
	}

	@AfterSuite
	public void ShuttingDownAllDependencies() throws Exception {
		if (System.getProperty("AutomationRunning").equalsIgnoreCase(Constants.sAutomationWeb.toLowerCase())) {
			driver.quit();
			}
	}
}



