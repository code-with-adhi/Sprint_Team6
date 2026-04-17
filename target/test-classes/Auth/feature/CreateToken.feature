Feature: Create Token test cases
Background: 
  Given Base URI is set to create the token
  
Scenario: Create token with proper creds
  When username and password are given properly
  Then token must be generated
  