Feature: Search for Flights At Kayak

Background: Launch Browser and Login to Kayak application Flights Search page
Given Open Browser
When Login into Kayak application 
And click on Flights menu item tab for Search For flights page to appear
Then Verify Search for Flight Page is displayed or Not successfully

Scenario: TC01: Search for Flights page
Given Enter travel Origin and Destination Cities
When Select travel Departure and Return Dates
And Click on search to search in the multiple flight results info 
And Click on the Nth flight search result to view details of the flight
Then Verify the actual origin and destination cities matches with the entered cities
Then Verify the travel dates matches with the entered dates
Then Quit Browser 


Scenario: TC01: Search for Flights page
Given Remove and Clear the selected travel Origin and Destination Cities
When Enter travel Origin city "<OriginFrom>" and airport "<OriginAirportCode>" and travel Destination city "<DestinationTo>" and airport "<DestinationAirportCode>" 
When Select travel Departure and Return Dates
And Click on search to search in the multiple flight results info 
And Click on the Nth flight search result to view details of the flight
Then Verify the actual origin and destination cities matches with the entered cities
Then Verify the travel dates matches with the entered dates
Then Quit Browser 
 