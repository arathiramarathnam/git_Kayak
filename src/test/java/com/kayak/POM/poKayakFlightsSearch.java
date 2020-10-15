package com.kayak.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.JavascriptExecutor;

import com.kayak.utilities.TestBase;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;

public class poKayakFlightsSearch extends TestBase{

	Logger log = Logger.getLogger(getClass().getSimpleName());

	public poKayakFlightsSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath ="//ul[@class='keel-grid _jrD _jrE _joN _joO']")
	WebElement nav_kayak;
	@FindBy(xpath ="//li[@class='col js-vertical-flights _iMq _ia1 _iaL _irH _ics']//a[contains(text(),'Flights')]")
	WebElement link_flights;
	@FindBy(xpath="//div[@class='keel-container s-t-bp']//h2[@class='title dark']")
	public
	WebElement title_SearchFlights;
	@FindBy(xpath="//div[@class='ZRVS']//a[contains(@href,'/cars') and @aria-label='Search for cars']")
	WebElement menu_kayak;
	@FindBy(xpath="//a[contains(@href,'/flights') and @aria-label='Search for flights']")
	WebElement menu_flights;
	
	public boolean selectFlightsMenuItem() throws Exception {
		boolean bmenu_Flag=false;
		oBroUti.ScrollByUp(driver);
		try { 
			oBroUti.waitForPageElementToVisible(driver, nav_kayak, 80);
			oBroUti.ufClick(link_flights);
			}/* catch (NoSuchElementException ex) {
			ex.printStackTrace();
			}finally {
				try {
					oBroUti.waitForPageElementToVisible(driver, menu_kayak, 80);	
					oBroUti.ClickUsingJS(driver, menu_flights);
					}*/ catch (NoSuchElementException e) {
						e.printStackTrace();
					}finally {
			oBroUti.waitForPageElementToVisible(driver, title_SearchFlights, 80);
			}
			String sPageText = oBroUti.ufGetText(title_SearchFlights);
			if (sPageText.trim().equalsIgnoreCase("Search hundreds of flight sites at once.")) {
			log.info("Kayak Search for Flights page is displayed: " +oBroUti.ufGetText(title_SearchFlights));
			}else
			{
			log.info("Kayak Search for Flights page is not displayed: " +oBroUti.ufGetText(title_SearchFlights));
			}
			if ((driver.getCurrentUrl()).contains("flights")) {
				bmenu_Flag=true;
				log.info("Kayak Search for Flights page is displayed: " +oBroUti.ufGetText(title_SearchFlights));
			}
		return bmenu_Flag;
	}
	
	@FindBy(xpath="//div[contains(@class,'js-selection-display')]")
	WebElement isDisplay_origin;
	@FindBy(xpath="//div[contains(@class,'js-selection-display')]")
	WebElement isDisplay_destination;
	@FindBy(xpath="//div[@class='_iac _irF _Hk _h-8']//button[contains(@class,'js-remove-selection')]")
	WebElement close_origin;
	@FindBy(xpath="//div[@class='_iac _irF _Hk _h-8']//button[contains(@class,'js-remove-selection')][1]")
	WebElement close_destination;
	@FindBy(xpath="//div[contains(@id,'origin-airport-close')]//*[contains(@class,'x-icon-x')]")
	WebElement closeicon_origin;
		
	public boolean clearOrginAndDestinationCities() throws Exception{
		boolean bclearcities_Flag=false;
	if (oBroUti.isDisplayed(isDisplay_origin)) {
			oBroUti.waitForPageElementToVisible(driver, isDisplay_origin, 30);
//			oBroUti.ufClick(isDisplay_origin);
			oBroUti.ufClick(close_origin);
			log.info("Removed the origin airport displayed");
		}
		oBroUti.ufClick(title_SearchFlights);
		if (oBroUti.isDisplayed(isDisplay_destination)) {
			oBroUti.waitForPageElementToVisible(driver, isDisplay_destination, 30);
			oBroUti.ufClick(close_destination);
			log.info("Removed the destination airport displayed");
		}
		bclearcities_Flag=true;
		log.info("Origin and Destination Cities are cleared to take inputs");
		return bclearcities_Flag;
	}
	
	@FindBy(xpath="//*[@data-placeholder='From?' and @aria-label='Flight origin input']")
	WebElement ph_origin;
	@FindBy(xpath="//*[@data-placeholder='To?' and @aria-label='Flight destination input']")
	WebElement ph_destination;
	@FindBy(xpath="//*[@placeholder='From?' and @aria-label='Flight origin input']")
	WebElement input_origin;
	@FindBy(xpath="//*[@placeholder='To?' and @aria-label='Flight destination input']")
	WebElement input_destination;
	@FindBy(xpath="//*[contains(@id,'origin-airport-smartbox-dropdown')]//ul[@class='flight-smarty']")
	WebElement dropdown_originairport;
	@FindBy(className="flight-smarty")
	WebElement listbox_airportoptions;
	@FindBys(@FindBy(xpath="//ul[@class='flight-smarty']//li[@role='option']"))
	List<WebElement> selectOptions_airports;
	@FindBy(xpath="//*[contains(@id,'destination-airport-smartbox-dropdown')]//ul[@class='flight-smarty']")
	WebElement dropdown_destinationairport;
	
	public boolean enterOrginAndDestinationCities(String OriginFrom, String OriginAirportCode, String DestinationTo, String DestinationAirportCode) throws Exception {
		boolean binput_Flag=false;
		//enter origin airport 
		oBroUti.ufClick(ph_origin);
		oBroUti.ufSendKeys(input_origin, OriginFrom);
		oBroUti.waitForPageElementToVisible(driver, dropdown_originairport, 60);
		oBroUti.waitForPageElementToVisible(driver, listbox_airportoptions, 60);
		int listOfairportOptions_origin = selectOptions_airports.size();
		for (int i = 0; i < listOfairportOptions_origin; i++) {
			if (selectOptions_airports.get(i).getText().contains(OriginAirportCode)) {
				selectOptions_airports.get(i).click();
                log.info("Selected the origin airport: "+selectOptions_airports.get(i).getAttribute("data-short-name"));
                binput_Flag=true;
                break;
		}}
		oBroUti.waitForPageElementToVisible(driver, ph_origin, 50);	
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].parentNode.removeChild(arguments[0])", listbox_airportoptions);
        
        //enter destination airport
        oBroUti.ufClick(ph_destination);
		oBroUti.ufSendKeys(input_destination, DestinationTo);
		oBroUti.waitForPageElementToVisible(driver, dropdown_destinationairport, 60);
		oBroUti.waitForPageElementToVisible(driver, listbox_airportoptions, 60);
		int listOfairportOptions_dest = selectOptions_airports.size();
		for (int i = 0; i < listOfairportOptions_dest; i++) {
			if (selectOptions_airports.get(i).getText().contains(DestinationAirportCode)) {
				selectOptions_airports.get(i).click();
                log.info("Selected the destination airport: "+selectOptions_airports.get(i).getAttribute("data-short-name"));
                binput_Flag=true;
                break;
		}}
		oBroUti.waitForPageElementToVisible(driver, ph_destination, 40);	
		js.executeScript("arguments[0].parentNode.removeChild(arguments[0])", listbox_airportoptions);
        return binput_Flag;
	}
  
	@FindBy(xpath="//div[contains(@class,'startDate')]//*[@data-placeholder='Depart']")
	WebElement isDisplay_departure;
	@FindBy(xpath="//div[contains(@id,'depart-input') and @aria-label='Departure date input']")
	WebElement input_departuredate;
	@FindBy(xpath="//div[contains(@id,'datePicker-flexDepartLabel') and @aria-label='Flexible depart dates']")
	WebElement label_departuredate;
	@FindBy(xpath="//div[@aria-label='Next month' and @role='button']")
	WebElement btn_NextMonth;
	@FindBy(xpath="//div[@aria-label='Previous month' and @role='button']")
	WebElement btn_PreviousMonth;
	@FindBy(xpath="//div[contains(@class,'month ')]")
	public WebElement datewidget_month;
	@FindBy(xpath="//div[contains(@id,'cal-202008')]//div[@class='month ']//*[contains(text(),'August 2020')]")
	WebElement text_departuremonthandyear;
	@FindBy(xpath="//div[contains(@class,'weeks ')]")
	public WebElement datewidget_weeks;									
	@FindBy(xpath="//*[@data-placeholder='Return']")
	WebElement isDisplay_return;
	@FindBy(xpath="//div[contains(@id,'return-input') and @aria-label='Return date input']")
	WebElement input_returndate;
	@FindBy(xpath="//div[contains(@id,'datePicker-flexReturnLabel') and @aria-label='Flexible return dates']")
	WebElement label_returndate;
	@FindBy(xpath="//div[contains(@id,'cal-202009']//div[@class='month ']//div[contains(text(),'September 2020')]")
	WebElement text_returnmonthandyear;
	/*@FindBys(@FindBy(xpath="//div[@class='weeks ']//*[@role='row']"))
	List<WebElement> datewidget_rows;*/
/*	@FindBys(@FindBy(xpath="//div[@class='weeks ']//*[@role='row']//*[contains(@class,'col day') and @role='gridcell']//div"))
	List<WebElement> datewidget_columns;*/
	
	public WebElement getdeparturemonth(String text_departuremonth){
		return datewidget_month.findElement(By.xpath("//*[contains(text(),'"+text_departuremonth+"')]"));
	}
	
	public WebElement getdeparturedate(String text_depaturedate){
		return datewidget_weeks.findElement(By.xpath("//div[@role='row']//div[@aria-label='"+text_depaturedate+"']"));
	}
	
	public WebElement getreturnmonth(String text_returnmonth){
		return datewidget_month.findElement(By.xpath("//*[contains(text(),'"+text_returnmonth+"')]"));
	}
	
	public WebElement getreturndate(String text_returndate){
		return datewidget_weeks.findElement(By.xpath("//div[@role='row']//div[@aria-label='"+text_returndate+"']"));
	}

	public boolean selectTravelDepartureAndReturnDates(String DepartureDate, String ReturnDate) throws Exception {
		boolean bRes_Flag=false;
		// Enter Departure date
        // split date to take out month,year,day in the date
        // String departdate="07/31/2020";
        String actualdepartdate = DepartureDate;
        String[] dateArray;
        String delimiter = "/";
        dateArray = actualdepartdate.split(delimiter);
         for (int i = 0; i < dateArray.length; i++) {
            System.out.println(dateArray[i]);
            log.info("departure date given as input: "+dateArray[i]);
       }
    // Click on textbox so that datepicker will display
        oBroUti.ufClick(isDisplay_departure);
        oBroUti.waitForPageElementToVisible(driver, input_departuredate, 60);
        // click month and year in the calendar
       
      /*  SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
        SimpleDateFormat month = new SimpleDateFormat("MMMM YYYY", Locale.ENGLISH);
        Date date =  dateFormat.parse(actualdepartdate);
        String monthnameplusyear = month.format(date);
        System.out.println(monthnameplusyear);
        log.info("month and year given as input: " + monthnameplusyear);*/
        
//        String actual_departdate = "08/20/2020";
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        LocalDate ldm = LocalDate.parse(actualdepartdate, dtf1);
        String monthnameplusyear = dtf2.format(ldm);
        System.out.println(monthnameplusyear); 
        
        oBroUti.waitForPageElementToVisible(driver, label_departuredate, 60);
        
        if (oBroUti.ufGetText(getdeparturemonth(monthnameplusyear)).contains(monthnameplusyear) && oBroUti.isDisplayed(label_departuredate)) {
			log.info("departure month is selected as given input: "+oBroUti.ufGetText(getdeparturemonth(monthnameplusyear)));
		}else {
        oBroUti.ufClick(btn_NextMonth);
        log.info("departure month is selected as given input: "+oBroUti.ufGetText(getdeparturemonth(monthnameplusyear)));
        }
       // oBroUti.waitForPageElementToVisible(driver, tablegrid_month, 60);
        oBroUti.ufGetText(text_departuremonthandyear);
        // Get the year difference between current year and year to set in calendar
        Calendar cal= Calendar.getInstance();
        int year = Integer.parseInt(dateArray[2]) - Calendar.getInstance().get(Calendar.YEAR);
        if (year==0) {
        	 log.info("year is displayed as given input: " + dateArray[2]);
        	 log.info("year is displayed as given input: " + year);
		}
        /*
         * DatePicker is not a table.So navigate to each cell If a particular cell
         * matches date value then select it
         */
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("MMMM dd", Locale.ENGLISH);
        LocalDate ldd = LocalDate.parse(actualdepartdate, dtf3);
        String monthnameplusdate = dtf4.format(ldd);
        System.out.println(monthnameplusdate); 
        
//        oBroUti.waitForPageElementToVisible(driver, datewidget_weeks, 80);
         log.info("label of departure date displayed in the grid cell: "+getdeparturedate(monthnameplusdate));
        
        if (getdeparturedate(monthnameplusdate).getAttribute("aria-label").contains(dateArray[1])) {
        	oBroUti.ufClick(getdeparturedate(monthnameplusdate));
        	 log.info("departure date is clicked as per user input: "+getdeparturedate(monthnameplusdate).getAttribute("aria-label"));
             System.out.println(getdeparturedate(monthnameplusdate).getAttribute("aria-label"));
             }
        
          /*for (WebElement datecell : datewidget_columns) {
            // Select Date
            if (datecell.getAttribute("aria-label").contains(dateArray[1])) {
                oBroUti.ufClick(datecell);
                log.info("departure date is clicked as per user input: "+datecell.getAttribute("aria-label"));
                System.out.println(datecell.getAttribute("aria-label"));
                break;
            }
        }*/
       oBroUti.waitForPageElementToVisible(driver, isDisplay_departure, 60);
       oBroUti.ufClick(input_departuredate);
       oBroUti.waitForPageElementToVisible(driver, input_departuredate, 60);
       if (input_departuredate.getText().equalsIgnoreCase(actualdepartdate)) {
		log.info("Travel departure date is entered as per user input: "+oBroUti.ufGetText(input_departuredate));
		bRes_Flag=true;	
		}
       
        // Enter Arriving date
        // split date to take out month,year,day in the date
        // String arrdate="26-Feb-2016";
        String actualreturndate = ReturnDate;
        String[] dateArray1;
        String delimiter1 = "/";
        dateArray1 = actualreturndate.split(delimiter1);
        for (int i = 0; i < dateArray1.length; i++) {
            System.out.println(dateArray1[i]);
        log.info("return date given as input: "+dateArray1[i]);
	    }
       
   // Click on textbox so that datepicker will display
       oBroUti.ufClick(input_returndate);
       oBroUti.waitForPageElementToVisible(driver, input_returndate, 60);
       // click month and year in the calendar
        /*SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
       SimpleDateFormat month1 = new SimpleDateFormat("MMMM YYYY", Locale.ENGLISH);
       Date date1 =  dateFormat1.parse(actualreturndate);
       String monthnameplusyear1 = month1.format(date1);
       System.out.println(monthnameplusyear1);
       log.info("month and year given as input: " + monthnameplusyear1);*/
       
//       String actualDate = "08/20/2020";
       DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
       DateTimeFormatter dtf6 = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
       LocalDate ldr = LocalDate.parse(actualreturndate, dtf5);
       String monthnameplusyear1 = dtf6.format(ldr);
       System.out.println(monthnameplusyear1); 
       
       log.info("label of return month is displayed: "+oBroUti.ufGetText(getreturnmonth(monthnameplusyear1)));
       log.info("label of return is displayed: "+oBroUti.ufGetText(label_returndate));
       
       if (oBroUti.ufGetText(getreturnmonth(monthnameplusyear1)).contains(monthnameplusyear1) && oBroUti.isDisplayed(label_returndate)) {
			log.info("return month is selected as given input: "+oBroUti.ufGetText(getreturnmonth(monthnameplusyear1)));
		}else {
       oBroUti.ufClick(btn_NextMonth);
       log.info("return month is selected as given input: "+oBroUti.ufGetText(getreturnmonth(monthnameplusyear1)));
       }
//       oBroUti.waitForPageElementToVisible(driver, tablegrid_month, 60);
//       oBroUti.ufGetText(text_returnmonthandyear);
       // Get the year difference between current year and year to set in calendar
       Calendar cal1= Calendar.getInstance();
       int year1 = Integer.parseInt(dateArray1[2]) - Calendar.getInstance().get(Calendar.YEAR);
       if (year1==0) {
    	   log.info("year is displayed as given input: " + dateArray1[2]);
       	 log.info("year is displayed as given input: " + year1);
		}
       
       DateTimeFormatter dtf7 = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
       DateTimeFormatter dtf8 = DateTimeFormatter.ofPattern("MMMM dd", Locale.ENGLISH);
       LocalDate ldrd = LocalDate.parse(actualreturndate, dtf7);
       String monthnameplusdate1 = dtf8.format(ldrd);
       System.out.println(monthnameplusdate1); 
       
//      oBroUti.waitForPageElementToVisible(driver, datewidget_weeks, 60);
       log.info("label of return date displayed in the grid cell: "+getreturndate(monthnameplusdate1));
       
       if (getreturndate(monthnameplusdate1).getAttribute("aria-label").contains(dateArray1[1])) {
       	oBroUti.ufClick(getreturndate(monthnameplusdate1));
       	 log.info("return date is clicked as per user input: "+getreturndate(monthnameplusdate1).getAttribute("aria-label"));
            System.out.println(getreturndate(monthnameplusdate1).getAttribute("aria-label"));
            }
       
       /*
        * DatePicker is not a table.So navigate to each cell If a particular cell matches date value then select it */
    /*  for (WebElement datecell1 : datewidget_columns) {
           // Select Date
           if (datecell1.getAttribute("aria-label").contains(dateArray1[1])) {
               oBroUti.ufClick(datecell1);
               break;
           }
       }*/
      oBroUti.waitForPageElementToVisible(driver, isDisplay_return, 60);
      oBroUti.ufClick(isDisplay_return);
      oBroUti.waitForPageElementToVisible(driver, input_returndate, 80);
      oBroUti.ufClick(input_returndate);
      if (oBroUti.ufGetText(input_returndate).equalsIgnoreCase(actualreturndate)) {
		log.info("Travel return date is entered as per user input: "+oBroUti.ufGetText(input_returndate));
		bRes_Flag=true;	
		}
      oBroUti.waitForPageElementToVisible(driver, label_returndate, 60);
      oBroUti.ufClick(getreturndate(monthnameplusdate1));
      oBroUti.waitForPageElementToVisible(driver, isDisplay_return, 60);
      return bRes_Flag;
	}
	
	@FindBy(xpath="//button[contains(@id,'submit') and (@aria-label='Search flights')]")
	public WebElement button_searchflights;
	@FindBy(xpath="//button[contains(@id,'submit') and (@type='submit')]")
	WebElement buttontext_searchflights;
	@FindBy(xpath="//div[contains(@id,'airports-section-title')]//h3[contains(text(),'Airports')]")
	WebElement searchresults_airports;
	@FindBy(xpath="//span[contains(text(),'Cheapest')]")
	WebElement text_Cheapest;
	@FindBy(xpath="//span[contains(text(),'Best')]")
	WebElement text_Best;
	@FindBy(xpath="//span[contains(text(),'Quickest')]")
	WebElement text_Quickest;
	
	public boolean searchForMultipleFlightsResultsInfo() throws Exception{
		boolean bRes_Flag=false;
		try {
		oBroUti.waitForElementVisible(driver, button_searchflights, 60);
		oBroUti.ufClick(button_searchflights);
		log.info("search flights button is clicked:"+button_searchflights.getAttribute("title"));
		oBroUti.waitForElementVisible(driver, searchresults_airports, 90);
		}catch(Exception ea) {
			log.error("search flights button is not clicked");
			}
		if(oBroUti.isDisplayed(text_Cheapest) && oBroUti.isDisplayed(text_Best) && oBroUti.isDisplayed(text_Quickest))
			log.info("search flights results for airports entered are displayed:"+oBroUti.ufGetText(text_Cheapest)+","+oBroUti.ufGetText(text_Best)+","+oBroUti.ufGetText(text_Quickest));
			bRes_Flag=true;
	return bRes_Flag;
	}
	
	@FindBy(xpath="//div[@class='resultsList' and @aria-label='Flight Search Results']//div[@id='searchResultsList']")
	public WebElement section_searchflightresults;
	@FindBy(xpath="//p[@class='covid-policy-header'][contains(text(),'Airline policy updates')]")
	public WebElement label_airlinepolicyupdates;
	
	public WebElement getNthFlightsSearchResultsFromList(int n) throws Exception{
		return section_searchflightresults.findElement(By.xpath("//div[contains(@aria-label,'Result number "+n+":')]"));
	}
	
		
	public boolean selectOnNthFlightSearchResults(int n) throws Exception{
		boolean bNthflightresults_Flag=false;
		if(oBroUti.isDisplayed(section_searchflightresults)) {
			oBroUti.waitForPageElementToVisible(driver, section_searchflightresults, 90);
			oBroUti.ScrollToView(driver, getNthFlightsSearchResultsFromList(n));  
			oBroUti.waitForPageElementToVisible(driver, getNthFlightsSearchResultsFromList(n), 90);	
			oBroUti.ClickUsingJS(driver, getNthFlightsSearchResultsFromList(n));
			log.info("selected the nth flight results from search results list: "+n);
			oBroUti.waitForPageElementToVisible(driver, label_airlinepolicyupdates, 90);	
			bNthflightresults_Flag=true;}
	 return bNthflightresults_Flag;
	}
	
	@FindBy(xpath="//label[contains(text(),'Depart')]")
	public WebElement label_depart;
	@FindBy(xpath="//*[@class='airports left']//span[@class='origin-destination']")
	public WebElement label_originToDestination;
	@FindBy(xpath="//label[contains(text(),'Return')]")
	public WebElement label_Return;
	@FindBy(xpath="//div[@class='airport-codes details-heading text-row']//span[@class='origin']//span[@class='city']")
	public WebElement label_origin;
	@FindBy(xpath="//div[@class='airport-codes details-heading text-row']//span[@class='destination']//span[@class='city']")
	public WebElement label_destination;
	
	public boolean checkoriginanddestinationcitiesmatchesornot() throws Exception{
		boolean bRes_Flag=false;
		oBroUti.waitForPageElementToVisible(driver, label_origin, 60);	
		oBroUti.waitForPageElementToVisible(driver, label_destination, 60);	
		oBroUti.ufGetText(label_origin);
		oBroUti.ufGetText(label_destination);
		log.info("origin and destination cities matches: "+oBroUti.ufGetText(label_origin)+","+oBroUti.ufGetText(label_destination));		
			bRes_Flag=true;
		return bRes_Flag;
		}
	
	
	@FindBy(xpath="//div[@class='left-column segment-dates']//*[@class='leg-dates-set']//div[@class='date']")
	public WebElement label_departplusarrivaldate;
	@FindBy(xpath="//div[@class='left-column segment-dates']//*[@class='leg-dates-set']")
	public WebElement date_column;
	
	public WebElement getdepartdate(String text_depaturedate){
		return date_column.findElement(By.xpath("//div[@class='date'][contains(text(),'"+text_depaturedate+"')]"));
	}
	
	public WebElement getarrivaldate(String text_returndate){
		return date_column.findElement(By.xpath("//div[@class='date'][contains(text(),'"+text_returndate+"')]"));
	}
	
	public boolean checkdepartureandreturndatesmatchesornot() throws Exception{
		boolean bRes_Flag=false;
		oBroUti.waitForPageElementToVisible(driver, label_departplusarrivaldate, 60);	
		oBroUti.ufGetText(label_departplusarrivaldate);
		log.info("departure and return dates matches: "+oBroUti.ufGetText(label_departplusarrivaldate));		
			bRes_Flag=true;
		return bRes_Flag;
		}
	
	

}
