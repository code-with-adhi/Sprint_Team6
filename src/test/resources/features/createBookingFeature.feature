#Author: Rajmohan T

Feature: Validate Complete Creation of Booking using POST


#Data Table
@TC_15   
Scenario: Create new booking with valid data
    When the user sends the POST request "/booking" with following details
    | firstname | lastname | totalprice | depositpaid | checkin     | checkout    | additionalneeds |
    | Sharan    |  Kumar   | 1000       | true        | 2024-01-01  | 2024-01-05  |  Breakfast      |
    | Arun      | Kumar    | 150        | false       | 2024-02-10  | 2024-02-15  |  Dinner         |
    | Priya     | Sharma   | 200        | true        | 2024-03-05  | 2024-03-10  |  Wifi           |
    
    Then the response statuscode for post is 200
    And the response statusLine for post is "OK"
    And the response time for post is less than 5000 ms
    And the post response should contain the booking object 
   
 
 #Scenario Outline  
@TC_16   
Scenario Outline: Create booking and validate booking id
    When the user sends the POST request "/booking" with "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    Then the response statuscode for post is 200
    And the response statusLine for post is "OK"
    And the response time for post is less than 5000 ms
    And the post response should contain the bookingid
    Examples:
    | firstname | lastname | totalprice | depositpaid | checkin     | checkout    | additionalneeds |
    | Sharan    |  Kumar   | 1000       | true        | 2024-01-01  | 2024-01-05  |  Breakfast      |
    | Arun      | Kumar    | 150        | false       | 2024-02-10  | 2024-02-15  |  Dinner         |
    | Priya     | Sharma   | 200        | true        | 2024-03-05  | 2024-03-10  |  Wifi           |


#DDT
@TC_17    
Scenario Outline: Validate request and response data
    When the user sends the POST request "/booking" with tcId 
    Then the response statuscode for post is 200
    And the response statusLine for post is "OK"
    And the response time for post is less than 5000 ms
    And Validate the post response matches request data
    



@TC_18  
Scenario: Create booking with totalprice as string
    When the user sends the POST request "/booking" with details "Raj","Mohan","1500",true,"2024-10-15","2025-01-11","Lunch"
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
   


@TC_19   
Scenario: Create booking with empty firstname and lastname
    When the user sends the POST request "/booking" with Invalid Date Format "Raj","Mohan",2500,true,"15/10/2024","25-01-2025","Breakfast"
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
    

#Scenario Outline
@TC_20
Scenario Outline: Create booking with invalid date format   
    When the user sends the POST request "/booking" with Empty mandatory Fields
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
    Example:
    | firstname | lastname | totalprice | depositpaid | checkin     | checkout    | additionalneeds |
    | Sharan    |  Kumar   |            |             | 2024-01-01  | 2024-01-05  |  Breakfast      |


#DDT
@TC_21   
Scenario Outline: Create booking with missing mandatory field
    When the user sends the POST request "/booking" with missing data
    Then the response statuscode for post is 400
    And the response statusLine for post is "Bad Request"
    And the response time for post is less than 5000 ms
   
