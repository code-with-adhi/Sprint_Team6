#Author: Prithibha
Feature: Validate Partial Update of Booking using PATCH

  Background:
    Given Generate PATCH valid authentication token
    And Create a new booking
  @TC_28
  Scenario: TC_28 Validate only firstname is updated
    When Send PATCH request with firstname "<firstname>" only
    Then Validate status code should be 200
    And Validate response time less than 2000 ms
    And Validate status line contains "OK"
    And Validate firstname is "<firstname>"
    And Validate lastname is "<expectedLastname>"
    Examples:
      | firstname | expectedLastname |
      | Alice     | Jones            |
  @TC_29
  Scenario: TC_29 Validate only totalprice is updated
    When Send PATCH request with totalprice only
      | totalprice |
      | 2500       |
    Then Validate status code should be 200
    And Validate response time less than 2000 ms
    And Validate status line contains "OK"
    And Validate lastname is "Jones"
  @TC_30
  Scenario: TC_30 Validate PATCH depositpaid field update
    When Send PATCH request updating depositpaid
    Then Validate status code should be 200
    And Validate response time less than 2000 ms
    And Validate status line contains "OK"
    And Validate firstname is "Susan"
    And Validate lastname is "Jones"
  @TC_31
  Scenario: TC_31 Validate multiple fields update
    When Send PATCH request with multiple fields
    Then Validate status code should be 200
    And Validate response time less than 2000 ms
    And Validate status line contains "OK"
    And Validate firstname is "Robert"
    And Validate lastname is "Jones"
  @TC_32
  Scenario: TC_32 Validate invalid token error
    When Send PATCH request with invalid token
    Then Validate status code should be 403
    And Validate response time less than 2000 ms
    And Validate status line contains "Forbidden"