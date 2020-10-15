#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Search for Flights At Kayak
  
  Background: Launch Browser and Login to Kayak application Flights Search page
  Given Open Browser
	When Login into Kayak application 
	And click on Flights menu item tab for Search For flights page to appear
	Then Verify Search for Flight Page is displayed or Not successfully
	
  Scenario Outline: TC01: Search for Flights page
    Given Remove and Clear the selected travel Origin and Destination Cities
    When Enter travel Origin city "<OriginFrom>" and airport "<OriginAirportCode>" and travel Destination city "<DestinationTo>" and airport "<DestinationAirportCode>"
    When Select travel Departure date "<DepartureDate>" and Return date "<ReturnDate>" 
    And Click on search to search in the multiple flight results info
    And Click on the Nth "<n>" flight search result to view details of the flight
    Then Verify the actual origin and destination cities matches with the entered cities
    And Verify the travel dates matches with the entered dates
    Then Quit Browser 

   Examples: 
      | OriginFrom  | OriginAirportCode | DestinationTo  				| DestinationAirportCode | DepartureDate |	ReturnDate	|	n		|
      | Denver, CO 	| DEN							  | Chicago, All airports | ORD								 		 | 10/15/2020		 |	10/26/2020	|	2		|
      
      
