Feature: Delete Booking Module

  Scenario: TC_33 - Successful booking deletion with valid credentials
    Given the API is up and booking exists
    And I have a valid authentication token
    When I send a DELETE request for the booking
    Then the response status code should be 201
    And the response message should be "Created"

  Scenario: TC_34 - Deletion fails when authentication token is missing
    Given the API is up and booking exists
    And I do not provide an authentication token
    When I send a DELETE request for the booking
    Then the response status code should be 403
    And the response message should be "Forbidden"

  Scenario: TC_35 - Verify deleted booking cannot be retrieved via GET
    Given a booking has already been deleted
    When I send a GET request for that deleted booking ID
    Then the response status code should be 404
    And the response message should be "Not Found"

  Scenario: TC_36 - System allows deletion of an existing booking ID
    Given the API is up and booking exists
    And I have a valid authentication token
    When I send a DELETE request for the booking
    Then the response status code should be 201
    And the response message should be "Created"

  Scenario: TC_37 - Verify 403 error when no authentication is provided
    Given the API is up and booking exists
    And I do not provide an authentication token
    When I send a DELETE request for the booking
    Then the response status code should be 403
    And the response message should be "Forbidden"