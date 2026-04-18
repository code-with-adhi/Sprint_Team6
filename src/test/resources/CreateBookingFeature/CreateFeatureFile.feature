#Author: Rajmohan T

Feature: Validate Complete Creation of Booking using POST

Background:  
    Given The Base URL for The Restful Booker is set "https://restful-booker.herokuapp.com/booking"
    
    

@TC_15   
Scenario: Create new booking with valid data
    When the user sends POST request "/booking"
    Then the response statuscode is 200
    And the response time is less than 5000 ms
    And the response should contain the booking object
    

@TC_16   
Scenario: Create booking and validate booking id
    When the user sends POST request "/booking"
    Then the response statuscode is 200
    And the response time is less than 5000 ms
    And the response should contain the bookingid
    

@TC_17    
Scenario: Validate request and response data
    When the user sends POST request "/booking"
    Then the response statuscode is 200
    And the response time is less than 5000 ms
    And Validate response matches request data
  

@TC_18  
Scenario: Create booking with totalprice as string
    When the user sends POST request "/booking"
    Then the response statuscode is 400
    And the response time is less than 5000 ms
    

@TC_19   
Scenario: Create booking with empty firstname and lastname
    When the user sends POST request "/booking"
    Then the response statuscode is 400
    And the response time is less than 5000 ms
    

@TC_20
Scenario: Create booking with invalid date format   
    When the user sends POST request "/booking"
    Then the response statuscode is 400
    And the response time is less than 5000 ms
    

@TC_21   
Scenario: Create booking with missing mandatory field
    When the user sends POST request "/booking"
    Then the response statuscode is 400
    And the response time is less than 5000 ms