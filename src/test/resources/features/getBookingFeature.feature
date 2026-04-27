#Author: Rajmohan T
Feature: Validate retrieval of Booking using Booking Id

  @TC_10
  Scenario: TC_10 Get booking using valid booking id
    When the user send GET request with "/booking/{id}"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms

  @TC_11
  Scenario: TC_11 Validate booking data is returned
    When the user send GET request with "/booking/{id}"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    And the get response should contain Booking Object

  @TC_12
  Scenario: TC_12 Validate mandatory fields in booking response
    When the user send GET request with "/booking/{id}"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    And the get response should contain following mandatory fields firstname, lastname, totalprice, depositpaid, checkin, checkout
#Scenario Outline

  @TC_13
  Scenario Outline: TC_13 Validate booking response data values
    When the user send GET request with "/booking/{id}"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    And the fields values should match expected data "<firstname>","<lastname>",<totalprice>,"<depositpaid>","<checkin>","<checkout>"

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   |
      | Raj       | Test     |        100 | true        | 2024-01-01 | 2024-01-02 |
#DataTable

  @TC_14
  Scenario: TC_14 Get error for invalid booking id
    When the user send GET request with "/booking/<id>" with invalid Id
      | id    |
      | 52828 |
      | abc   |
    Then the response statuscode for get is 404
    And the response statusLine for get is "Not Found"
    And the response time for get is less than 5000 ms
