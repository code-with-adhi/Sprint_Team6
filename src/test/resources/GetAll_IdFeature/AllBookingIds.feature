Feature: Get all booking IDs

Background: 
  Given Base URI is set to retrieve data

Scenario: TC5
  When no parameters to filter
  Then status code must be 200

Scenario: TC6
  When fliter with name
  Then status code must be 200

Scenario: TC7
  When filtering with date
  Then status code must be 200

Scenario: TC8
  When no valid data is present for the filter
  Then status code must be 200

Scenario: TC9
  When date format is wrong
  Then status code must be 400
