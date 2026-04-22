Feature: Create Token test cases
# Background: 
#   Given Base URI is set to create the token
  
Scenario Outline: TC1
  Given valid username and password
  |username|admin|
  |password|password123|
  When POST request is sent to "/auth" endpoint
  Then status code must be 200
  And token must be generated
  And token must be string
  And token length must be 15



Scenario: TC2
  Given invalid username "<username>" or password "<password>" are given
  When POST request is sent to "/auth" endpoint
  Then status code must be 200 
  And reason must be displayed
  And reason must be "Bad credentials"

  Examples:
  |username|password|
  |admin|passburrrp|
  |adshhdih|password123|
  |adshhdih|passburrrp|


Scenario: TC3
  Given the password field is Missing
  When POST request is sent to "/auth" endpoint
  Then status code must be 400

Scenario: TC4
  Given content type header is Missing
  When POST request is sent to "/auth" endpoint
  Then status code must be 400