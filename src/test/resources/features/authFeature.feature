Feature: Create Token test cases
Background: 
  Given Base URI is set to create the token
  
Scenario: TC1
  When username and password are given properly
  Then token must be generated

Scenario: TC2
  When the password is wrong
  Then status code must be 200 and reason must be displayed

Scenario: TC3
  When the password field is Missing
  Then status code must be 400

Scenario: TC4
  When content type header is Missing
  Then status code must be 400