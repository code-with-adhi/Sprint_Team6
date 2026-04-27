Feature: Get all booking IDs

Scenario: TC5
  When no parameters to filter
  Then status code must be 200
  # And response is an array
  And response should have multiple json objects

Scenario: TC6
  When filter with firstname and lastname
  |Firstname|Jim  |
  |Lastname |Brown|
  Then status code must be 200
  And response should have multiple json objects

Scenario Outline: TC7
  When filtering with checkin "<CIdate>" date and checkout "<COdate>" date

  Then status code must be 200
  And response must be proper for checkin dates
  And response must be proper for checkout dates
  And response must be proper for checkin and checkout dates
Examples:
  |CIdate|COdate|
  |2014-12-11|2026-04-20|
  |2014-12-12|2026-04-21|
  |2014-12-13|2026-04-22|
  |2014-12-14|2026-04-23|
  |2014-12-15|2026-04-24|

Scenario Outline: TC8
  When no valid data is present for "<Rownumber>" row in excel
  Then status code must be 200
  And response should have no data
  Examples:
  | Rownumber |
  | 2         |
  | 3         |
  | 4         |
  | 5         |

Scenario: TC9
  When date from "<row>" in excel format is wrong
  Then status code must be 400

  Examples:
  | row       |
  | 2         |
  | 3         |
  | 4         |
  | 5         |