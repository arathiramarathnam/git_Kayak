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

Feature: Transfer Amounts
  
  Scenario Outline: TC01: Transfer Amounts from checkin account to savings account
  Given User has bank accounts "<TypesOfAccounts>" and "<CurrentBalance>" current balance
	When User transfers amount "<AmountInDollars>" from checkin to savings account
	Then Verify the balance after transfer on both accounts
	
	Examples: 
      | TypesofAccounts  | CurrentBalance| AmountInDollars  |		 
      | checkIn			 		 | 10000				 | $100							|		 
      | savings			 		 | 20000				 | $0								|		 
	
  
      
      
