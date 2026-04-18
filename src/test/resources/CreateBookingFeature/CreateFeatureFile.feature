#Author: Rajmohan T

Feature: Validate Complete Creation of Booking using POST

Background:  
    Given The Base URL for The Restful Booker is set "https://restful-booker.herokuapp.com"
        

@TC_15   
Scenario Outline: Create new booking with valid data
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 200
    And the response statusLine for post is "OK"
    And the response time for post is less than 5000 ms
    And the post response should contain the booking object 
    Examples:
    |tcId|
    |TC_15|

@TC_16   
Scenario Outline: Create booking and validate booking id
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 200
    And the response statusLine for post is "OK"
    And the response time for post is less than 5000 ms
    And the post response should contain the bookingid
    Examples:
    |tcId|
    |TC_16|


@TC_17    
Scenario Outline: Validate request and response data
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 200
    And the response statusLine for post is "OK"
    And the response time for post is less than 5000 ms
    And Validate the post response matches request data
    Examples:
    |tcId|
    |TC_17|


@TC_18  
Scenario Outline: Create booking with totalprice as string
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
    Examples:
    |tcId|
    |TC_18|


@TC_19   
Scenario Outline: Create booking with empty firstname and lastname
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
    Examples:
    |tcId|
    |TC_19|


@TC_20
Scenario Outline: Create booking with invalid date format   
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
    Examples:
    |tcId|
    |TC_20|


@TC_21   
Scenario Outline: Create booking with missing mandatory field
    When the user sends the POST request "/booking" with tcId "<tcId>"
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
    Examples:
    |tcId|
    |TC_21|
