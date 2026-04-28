Feature: Delete Booking Module

  Scenario Outline: TC_33 → Validate booking is deleted with valid ID & token
    Given the API is up and booking exists
    And <auth_condition>
    When I send a DELETE request for the booking
    Then the response status code should be <status_code>

    Examples:
      | auth_condition                      | status_code |
      | I have a valid authentication token |         204 |

  Scenario: TC_34 → Deletion fails when authentication token is missing (Data Table)
    Given the API is up and booking exists
    When I perform delete operation with following data
      | auth    | expectedStatus | expectedMessage |
      | invalid |            403 | Forbidden       |
    Then I should validate all responses

  Scenario: TC_35 → Verify deleted booking cannot be retrieved via GET
    Given the API is up and booking exists
    And I have a valid authentication token
    And I delete the booking
    When I send a GET request for that deleted booking ID
    Then the response status code should be 404
    And the response message should be "Not Found"

Scenario: TC_36 → Validate error when deleting an already deleted booking
  Given the API is up and booking exists
  And I have a valid authentication token
  And I delete the booking
  When I send a DELETE request for the same booking again
  Then the response status code should be 405

  Scenario: TC_37 → Verify 403 error when no authentication is provided
    Given the API is up and booking exists
    And I do not provide an authentication token
    When I send a DELETE request for the booking
    Then the response status code should be 403
    And the response message should be "Forbidden"
