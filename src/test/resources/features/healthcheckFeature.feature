Feature: Health Check (Ping) API

  Scenario Outline: TC_38 → Validate API responds successfully for health check request
    Given the API is up
    When I send a GET request to "<endpoint>"
    Then the response status code should be <status_code>
    And the response message should be "<message>"

    Examples:
      | endpoint | status_code | message |
      | /ping    | 200         | OK      |


  Scenario: TC_39 → Validate API is accessible multiple times without failure (Data Table)
    Given the API is running
    When I send multiple GET requests with following data
      | endpoint | expectedStatus |
      | /ping    | 200            |
      | /ping    | 200            |
      | /ping    | 200            |
    Then all responses should be successful


  Scenario: TC_40 → Validate no authentication is required for ping API
    Given the API is up
    And I do not provide any authentication
    When I send a GET request to "/ping"
    Then the response status code should be 200
    And the response should be successful


  Scenario: TC_41 → Validate API behaviour with uppercase endpoint
    Given the API is up
    When I send a GET request to "/PING"
    Then the response status code should be 200
    And the response should be successful


  Scenario: TC_42 → Validate API returns error for invalid endpoint
    Given the API is up
    When I send a GET request to "/invalid"
    Then the response status code should be 404
    And the response message should be "Not Found"