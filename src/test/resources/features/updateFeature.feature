
Feature: Validate Complete Updation of Booking using PUT

  Background:
    Given Generate valid authentication token
    And Create a new booking
  @TC_22
  Scenario Outline: TC_22 Validate update without mandatory field lastname
    When Send PUT request without lastname field using "<firstname>" "<totalprice>" "<depositpaid>"
    Then Validate status code should be 400
    And Validate response time less than 4000 ms
    And Validate status line contains "Bad Request"
    Examples:
      | firstname | totalprice | depositpaid |
      | John      | 1000       | true        |

  @TC_23
  Scenario: TC_23 Validate complete update with valid data
    When Send PUT request with complete valid body
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Alice     | Brown    | 1500       | false       | 2026-05-01 | 2026-05-10 | Breakfast       |
    Then Validate status code should be 200
    And Validate response time less than 4000 ms
    And Validate status line contains "OK"
    And Validate firstname
    And Validate lastname
    And Validate depositpaid
  @TC_24
  Scenario: TC_24 Validate update with all fields changed
    When Send PUT request with completely different values
    Then Validate status code should be 200
    And Validate response time less than 4000 ms
    And Validate status line contains "OK"
    And Validate firstname
    And Validate lastname
    And Validate depositpaid
  @TC_25
  Scenario: TC_25 Validate update with valid booking dates
    When Send PUT request updating booking dates
    Then Validate status code should be 200
    And Validate response time less than 4000 ms
    And Validate status line contains "OK"
    And Validate firstname
    And Validate lastname
    And Validate depositpaid
  @TC_26
  Scenario: TC_26 Validate update without lastname but valid totalprice & depositpaid
    When Send PUT request without lastname but with valid price and deposit
    Then Validate status code should be 400
    And Validate response time less than 4000 ms
    And Validate status line contains "Bad Request"
  @TC_27
  Scenario: TC_27 Validate update with non-existing booking ID
    When Send PUT request with non existing booking id
    Then Validate status code should be 404
    And Validate response time less than 4000 ms
    And Validate status line contains "Not Found"