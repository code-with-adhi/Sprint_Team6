#Author: Rajmohan T

Feature: Validate retrieval of Booking using Booking Id




@TC_10
Scenario Outline: TC_10 Get booking using valid booking id
    When the user send GET request with "/booking/<id>"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    Examples:
    |id |
    |100|

@TC_11
Scenario Outline: TC_11 Validate booking data is returned
    When the user send GET request with "/booking/<id>"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    And the get response should contain Booking Object
    Examples:
    |id|
    |100|


@TC_12
Scenario Outline: TC_12 Validate mandatory fields in booking response
    When the user send GET request with "/booking/<id>"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    And the get response should contain following mandatory fields firstname, lastname, totalprice, depositpaid, checkin, checkout
    Examples:
    |id|
    |25|


@TC_13
Scenario Outline: TC_13 Validate booking response data values
    When the user send GET request with "/booking/<id>"
    Then the response statuscode for get is 200
    And the response statusLine for get is "OK"
    And the response time for get is less than 5000 ms
    And the fields values should match expected data "<firstname>","<lastname>",<totalprice>,"<depositpaid>","<checkin>","<checkout>","<additionalneeds>"
   Examples:
  |id| firstname | lastname | totalprice | depositpaid | checkin     | checkout    | additionalneeds |
  |100| John      | Smith    | 111        | true        | 2018-01-01  | 2019-01-01  | Breakfast       |


@TC_14
Scenario Outline: TC_14 Get error for invalid booking id
    When the user send GET request with "/booking/<id>"
    Then the response statuscode for get is 404
    And the response statusLine for get is "Not Found"
    And the response time for get is less than 5000 ms
    Examples:
    |id|
    |52828|