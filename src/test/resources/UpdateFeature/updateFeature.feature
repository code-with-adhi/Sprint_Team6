#Author: Prithibha
Feature: Validate Complete Updation of Booking using PUT

  Background:
    Given Generate valid authentication token
    And Create a new booking

  Scenario: TC_22 Validate update without mandatory field lastname
    When Send PUT request without lastname field
    Then Validate status code should be 400
    And Validate response time less than 4000 ms
    And Validate status line contains "Bad Request"

  Scenario: TC_23 Validate complete update with valid data
    When Send PUT request with complete valid body
    Then Validate status code should be 200
    And Validate response time less than 4000 ms
    And Validate status line contains "OK"
    And Validate firstname
    And Validate lastname
    And Validate depositpaid

  Scenario: TC_24 Validate update with all fields changed
    When Send PUT request with completely different values
    Then Validate status code should be 200
    And Validate response time less than 4000 ms
    And Validate status line contains "OK"
    And Validate firstname
    And Validate lastname
    And Validate depositpaid

  Scenario: TC_25 Validate update with valid booking dates
    When Send PUT request updating booking dates
    Then Validate status code should be 200
    And Validate response time less than 4000 ms
    And Validate status line contains "OK"
    And Validate firstname
    And Validate lastname
    And Validate depositpaid

  Scenario: TC_26 Validate update without lastname but valid totalprice & depositpaid
    When Send PUT request without lastname but with valid price and deposit
    Then Validate status code should be 400
    And Validate response time less than 4000 ms
    And Validate status line contains "Bad Request"

  Scenario: TC_27 Validate update with non-existing booking ID
    When Send PUT request with non existing booking id
    Then Validate status code should be 404
    And Validate response time less than 4000 ms
    And Validate status line contains "Not Found"