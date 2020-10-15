package com.kayak.utilities;

import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.google.common.base.Function;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import javax.imageio.ImageIO;

public class BrowserUtilities extends TestBase{

	Logger log = Logger.getLogger(getClass().getSimpleName());
	//WebDriver driver;
	/*
	 * @Author: Srinidhi 
	 * Method NAme : launchBrowser Parameters Browser : You have to
	 * pass browser types. Allowed ch(chrome), Ff(Firefox) 
	 * return : void
	 * Description: Launch the type of the browser which is been called
	 */
	@SuppressWarnings("deprecation")
	@Parameters("browserType")
	// @BeforeSuite
	public WebDriver launchBrowser(String browser) throws Exception {
		log.info("Launching Browser");

		// Check if parameter passed from TestNG is 'firefox'/chrome
		if (browser.toLowerCase().startsWith("ch" )) {

			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--disable-notifications");
			driver = new ChromeDriver(chromeOptions);
		} else if (browser.equalsIgnoreCase("ff") || browser.toLowerCase().startsWith("fi")) {
			// create firefox instance
			WebDriverManager.firefoxdriver().setup();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("permissions.default.desktop-notification", 1);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			driver = new FirefoxDriver(capabilities);
		} else if (browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new EdgeDriver();
		} else {
			// If no browser passed throw exception
			throw new Exception("Browser type is not correct");
		}
		driver.manage().window().maximize();
		return driver;
	}
	public String takeScreenShotWebReturnPath(String ClassName) throws IOException {
		String sDestDir = "/screenshots/";
		String sImageName = System.getProperty("user.dir") + sDestDir+ClassName+".jpg";
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(), "jpg", new File(sImageName));
		System.out.println(sImageName);
		return sImageName;
	}

	@SuppressWarnings("deprecation")
	public boolean waitForElementVisible(WebDriver driver, final WebElement ele, int iTimeInSeconds) throws Exception {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(iTimeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				if (ele.isDisplayed()) {
					log.info("Element Displayed : " + ele);
					return ele;
				} else {
					log.info("******Element NOT Displayed : " + ele);
					return null;
				}
			}
		});

		return ele.isDisplayed();
	}
	
	public void waitForPageElementToVisible(WebDriver driver, WebElement eleToWait, int iTimeOutInSeconds) throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, iTimeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(eleToWait));
		if(eleToWait.isDisplayed()){
			log.info("Element is displayed " + eleToWait);
		}else {
			log.info("Element is still displaying.... " + eleToWait);
		}
	}
	
	public void standardWaitForAllPageElementsToVisible(WebDriver driver, WebElement eleToWait) throws Exception{
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		if (eleToWait.isDisplayed()) {
			log.info("Element is displayed " + eleToWait);
		} else {
			log.info("Element is still displaying.... " + eleToWait);
		}
	}

	public boolean waitForElementDisable(WebDriver driver, final WebElement ele, int iTimeInSeconds) {

		@SuppressWarnings("deprecation")
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(iTimeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(80, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				if (!ele.isDisplayed()) {
					log.info("Element Not Displayed : " + ele);
					return ele;
				} else {
					log.info("******Element is Still Displaying : " + ele);
					return null;
				}
			}
		});

		return !ele.isDisplayed();
	}

	public boolean isDisplayed(WebElement ele) throws Exception {
		boolean bRes_flag = false;
		try {
			if (ele.isDisplayed()) {
				log.info("Displayed " + ele);
				bRes_flag = true;
			}
		} catch (Exception ea) {
			bRes_flag = false;
		}
		return bRes_flag;
	}

	public void screenShotBrowser(WebDriver driver, String className) throws Exception {
		String destDir = "screenshots";
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		String destFile = className + ".png";

		try {
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "/test-output/" + destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void screenShotBrowserForWebElement(WebDriver driver, WebElement eleScreenArea, String className)
			throws Exception {
		String destDir = "screenshots";
		String destFile = className + ".png";
		log.info("ScreenShot");
		// Screenshot fpScreenshot = new
		// AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver, eleScreenArea);
		// Screenshot fpScreenshot = new AShot().takeScreenshot(driver, eleScreenArea);

		try {
			ImageIO.write(fpScreenshot.getImage(), "PNG",
					new File(System.getProperty("user.dir") + "/target/surefire-reports/" + destDir + "/" + destFile));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void isDisplayed(WebElement ele, String str) throws Exception {

		assertTrue(ele.isDisplayed(), "Actual:" + str + " is displayed. Expected:" + str + " is not displayed");
		log.info("Actual:" + str + " Expected:" + str + " ");
		assertTrue(ele.isEnabled(), "Actual:" + str + " is Enabled. Expected:" + str + " is not Enabled");
	}

	public boolean isAttribtuePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public boolean ScrollToView(WebDriver driver, WebElement ele) throws Exception {
		boolean bRes_Flag = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
		if (!ele.isDisplayed()) {
			log.info("Element Not Displayed : " + ele);
			} else {
			log.info("******Element is still Displaying : " + ele);
			}
		bRes_Flag = true;
		return bRes_Flag;
	}
	
	public boolean ScrollByUp(WebDriver driver) throws Exception {
		boolean bRes_Flag = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-250)", "");
		bRes_Flag = true;
		return bRes_Flag;
	}
	
	public boolean ScrollByDown(WebDriver driver) throws Exception {
		boolean bRes_Flag = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)", "");
		bRes_Flag = true;
		return bRes_Flag;
	}
	
	public boolean ScrollDownByPage(WebDriver driver) throws Exception {
		boolean bRes_Flag = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		bRes_Flag = true;
		return bRes_Flag;
	}

	public void ClickUsingJS(WebDriver driver, WebElement ele) throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", ele);
		log.info("Element is Clicked : " + ele);
	}
	
	public void ufClick(WebElement ele) throws Exception {
		ele.click();
	}

	public void ufSendKeys(WebElement ele, String keysToSend) throws Exception {
		ele.sendKeys(keysToSend);
	}

	public String ufGetText(WebElement ele) throws Exception {
		return ele.getText();
	}
	
	public void ufClear(WebElement ele) throws Exception{
		ele.clear();
	}
}
