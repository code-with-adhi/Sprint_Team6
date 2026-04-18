Feature: Health Check (Ping) API


  Scenario: TC_38 - Validate API responds successfully for health check request
    Given the API is up
    When I send a GET request to "/ping"
    Then the response status code should be 201
    And the response message should be "Created"


  Scenario: TC_39 - Validate API is accessible multiple times without failure
    Given the API is running
    When I send multiple GET requests to "/ping"
    Then all responses should have status code 201
    And the response should always be successful


  Scenario: TC_40 - Validate no authentication is required for ping API
    Given the API is up
    And I do not provide any authentication
    When I send a GET request to "/ping"
    Then the response status code should be 201
    And the response should be successful


  Scenario: TC_41 - Validate response time is minimal
    Given the API is up
    When I send a GET request to "/ping"
    Then the response time should be less than 2 seconds
    And the response status code should be 201


  Scenario: TC_42 - Validate API returns error for invalid endpoint
    Given the API is up
    When I send a GET request to "/invalid"
    Then the response status code should be 404
    And the response message should be "Not Found"