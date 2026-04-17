#Author: Prithibha
Feature: Validate Complete Updation of Booking using PUT

  Background:
    Given Generate valid authentication token
    

  
  Scenario: TC_22 Validate update without mandatory field lastname
    When Send PUT request without lastname field
    Then Validate status code should be 400

  Scenario: TC_23 Validate complete update with valid data
    When Send PUT request with complete valid body
    Then Validate status code should be 200

  Scenario: TC_24 Validate update with all fields changed
    When Send PUT request with completely different values
    Then Validate status code should be 200

  Scenario: TC_25 Validate update with valid booking dates
    When Send PUT request updating booking dates
    Then Validate status code should be 200

  Scenario: TC_26 Validate update without lastname but valid totalprice & depositpaid
    When Send PUT request without lastname but with valid price and deposit
    Then Validate status code should be 400

  Scenario: TC_27 Validate update with non-existing booking ID
    When Send PUT request with non existing booking id
    Then Validate status code should be 404