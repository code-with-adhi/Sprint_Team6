Feature: Create Token test cases
Background: 
  Given Base URI is set to create the token
  
Scenario: Create token with proper creds
  When username and password are given properly
  Then token must be generated

Scenario: Improper creds
  When the password is wrong
  Then status code must be 200 and reason must be displayed

Scenario: Missing fields
  When the password field is Missing
  Then status code must be 400

Scenario: Missing header
  When content type header is Missing
  Then status code must be 400