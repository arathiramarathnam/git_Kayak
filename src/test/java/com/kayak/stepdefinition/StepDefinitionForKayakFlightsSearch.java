package com.kayak.stepdefinition;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kayak.POM.poKayakFlightsSearch;
import com.kayak.POM.poKayakFlightsSearch1;
import com.kayak.utilities.TestBase;
import com.kayak.utilities.TestDataManager;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitionForKayakFlightsSearch extends TestBase{
	
	poKayakFlightsSearch1 searching;	
	
	Logger log = Logger.getLogger(getClass().getSimpleName());
@Test
@Given("^Open Browser$")
public void open_Browser() throws Throwable {
	TriggerDependencies();
}
@Test(priority=2)
@When("^Login into Kayak application$")
public void login_into_Kayak_application() throws Throwable {
	sErrorMessage = "";
	sClassNameForScreenShot = getClass().getSimpleName();
	driver.get(oCons.settingUpAppURL());
	searching = new poKayakFlightsSearch1(driver);
 }

@Test(priority=3)
@And("^click on Flights menu item tab for Search For flights page to appear$")
public void click_on_Flights_menu_item_tab_for_Search_For_flights_page_to_appear() throws Throwable {
	searching.selectFlightsMenuItem();
	}

@Test(priority=4,dependsOnMethods="click_on_Flights_menu_item_tab_for_Search_For_flights_page_to_appear")
@Then("^Verify Search for Flight Page is displayed or Not successfully$")
public void verify_Search_for_Flight_Page_is_displayed_or_Not_successfully() throws Throwable {
	String sPageText=searching.title_SearchFlights.getText();
	oBroUti.isDisplayed(searching.title_SearchFlights, sPageText);
}

@Test(priority=5, enabled=true)
@Given("^Remove and Clear the selected travel Origin and Destination Cities$")
public void remove_and_clear_the_selected_travel_Origin_and_Destination_Cities() throws Throwable {
 boolean bremovecities_flag = searching.clearOrginAndDestinationCities();
   Assert.assertEquals(bremovecities_flag, true);
}

@Test(dataProvider="KayakFlightsAndAirportsSearchData", priority=6)
@When("^Enter travel Origin city \"([^\"]*)\" and airport \"([^\"]*)\" and travel Destination city \"([^\"]*)\" and airport \"([^\"]*)\"$") 
public void enter_travel_Origin_and_Destination_Cities(String OriginFrom, String OriginAirportCode, String DestinationTo, String DestinationAirportCode) throws Throwable {
	System.out.println("City Origin Airport: " +OriginFrom+ "Origin airport code: " +OriginAirportCode+ "Destination airport: "+DestinationTo+ "Destination airport code: " +DestinationAirportCode);
	log.info("City Origin Airport: " +OriginFrom+ "Origin airport code: " +OriginAirportCode+ "Destination airport: "+DestinationTo+ "Destination airport code" +DestinationAirportCode);
   boolean bentercities_flag = searching.enterOrginAndDestinationCities(OriginFrom, OriginAirportCode, DestinationTo, DestinationAirportCode);
   Assert.assertEquals(bentercities_flag, true);
}

@DataProvider(name="KayakFlightsAndAirportsSearchData")
public Object[][] testdataForKayakFlightsSearch() throws Exception {
    final String xlsFile = System.getProperty("user.dir") + "\\resources\\testdata\\Kayak_FlightSearchDetails.xls";
    Object[][] arrayObject = TestDataManager.getExcelData(xlsFile);
    return arrayObject;
}
@Test(dataProvider="KayakFlightsAndAirportsSearchData", priority=7)
@When("^Select travel Departure date \"([^\"]*)\" and Return date \"([^\"]*)\"$")
public void select_travel_Departure_and_Return_Dates(String DepartureDate, String ReturnDate) throws Throwable {
	boolean btraveldate_flag=searching.selectTravelDepartureAndReturnDates(DepartureDate, ReturnDate);
	Assert.assertEquals(btraveldate_flag, true);
}

@Test(priority=8)
@And("^Click on search to search in the multiple flight results info$")
public void click_on_search_to_search_in_the_multiple_flight_results_info() throws Throwable {
	boolean bsearch_flag=searching.searchForMultipleFlightsResultsInfo();
    Assert.assertEquals(bsearch_flag, true);
  }
@Test(priority=9)
@And("^Click on the Nth \"([^\"]*)\" flight search result to view details of the flight$")
public void click_on_the_Nth_flight_search_result_to_view_details_of_the_flight(int n) throws Throwable {
   boolean bNth_searchflag=searching.selectOnNthFlightSearchResults(n);
   Assert.assertEquals(bNth_searchflag, true);
}
@Test(priority=10)
@Then("^Verify the actual origin and destination cities matches with the entered cities$")
public void verify_the_actual_origin_and_destination_cities_matches_with_the_entered_cities() throws Throwable {
    boolean bverifycity_flag=searching.checkoriginanddestinationcitiesmatchesornot();
    Assert.assertEquals(bverifycity_flag, true);
}

@Test(priority=11)
@And("^Verify the travel dates matches with the entered dates$")
public void verify_the_travel_dates_matches_with_the_entered_dates() throws Throwable {
	boolean bverifydate_flag=searching.checkdepartureandreturndatesmatchesornot();
    Assert.assertEquals(bverifydate_flag, true);
}
	
@Test(priority=12)
@Then("^Quit Browser$")
public void quit_Browser() throws Throwable {
	ShuttingDownAllDependencies();
}

}
