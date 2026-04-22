Feature: Create Token test cases
Background: 
  Given Base URI is set to create the token
  
Scenario Outline: TC1
  When the "<username>" and "<password>" are given properly
  Then status code must be 200
  And token must be generated
  And token must be string
  And token length must be 15

  Examples:
  |username|password|
  |admin|password123|


Scenario: TC2
  When the password is wrong
  |username|adshhdih|
  |password|pass|
  Then status code must be 200 
  And reason must be displayed
  And reason must be "Bad credentials"

Scenario: TC3
  When the password field is Missing
  Then status code must be 400

Scenario: TC4
  When content type header is Missing
  Then status code must be 400