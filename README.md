# Restful-Booker API Test Automation Framework

A BDD-based API test automation framework built with Java, RestAssured, and Cucumber (TestNG), covering the full suite of REST operations on the [Restful-Booker](https://restful-booker.herokuapp.com) public API.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Features Covered](#features-covered)
- [Test Cases Overview](#test-cases-overview)
- [Setup & Prerequisites](#setup--prerequisites)
- [Configuration](#configuration)
- [Test Data](#test-data)
- [Running Tests](#running-tests)
- [Reporting](#reporting)

---

## Tech Stack

| Tool / Library         | Purpose                          |
|------------------------|----------------------------------|
| Java                   | Programming language             |
| Maven                  | Build and dependency management  |
| RestAssured            | API testing library              |
| Cucumber (BDD)         | Feature file and step definition management |
| TestNG                 | Test runner and assertions       |
| Apache POI             | Excel test data reading          |
| ExtentReports          | HTML test reporting              |

---

## Project Structure

```
src/
├── main/java/
│   ├── dto/
│   │   └── AuthObj.java                  # Data Transfer Object for auth credentials
│   └── utils/
│       ├── excelUtility/
│       │   ├── ExcelUtilityForAuth.java
│       │   ├── ExcelUtilityForCreate.java
│       │   ├── ExcelUtilityPartialUpdate.java
│       │   └── ExcelUtilityUpdate.java
│       └── fileUtility/
│           ├── FileUtility.java           # Reads .properties config files
│           └── Token.java                 # Singleton token store
│
└── test/
    ├── java/
    │   ├── hooks/
    │   │   └── Hooks.java                 # Before/After scenario setup
    │   ├── runners/
    │   │   ├── auth/AuthTest.java
    │   │   ├── booking/
    │   │   │   ├── CreateBookingTest.java
    │   │   │   ├── DeleteTest.java
    │   │   │   ├── GetAll_IdTest.java
    │   │   │   ├── GetBookingTest.java
    │   │   │   ├── PartialUpdateTest.java
    │   │   │   └── UpdateTest.java
    │   │   └── ping/HealthCheckTest.java
    │   └── stepdefinitions/
    │       ├── auth_stepdef/
    │       ├── create_booking_stepdef/
    │       ├── delete_booking_stepdef/
    │       ├── get_all_ids_stepdef/
    │       ├── get_booking_stepdef/
    │       ├── health_check_stepdef/
    │       ├── partial_update_stepdef/
    │       └── update_stepdef/
    └── resources/
        ├── configurations/
        │   └── ConfigEnvData.properties   # Base URL config
        ├── features/                      # Cucumber feature files
        │   ├── authFeature.feature
        │   ├── createBookingFeature.feature
        │   ├── deleteFeature.feature
        │   ├── getAllBookingIdsFeature.feature
        │   ├── getBookingFeature.feature
        │   ├── healthcheckFeature.feature
        │   ├── partialUpdateFeatue.feature
        │   └── updateFeature.feature
        ├── testdata/                      # Excel test data files
        │   ├── AuthData.xlsx
        │   ├── CreateBookingData.xlsx
        │   ├── PartialUpdationData.xlsx
        │   └── updateBookingData.xlsx
        ├── extent-config.xml
        └── extent.properties
```

---

## Features Covered

| Module          | HTTP Method | Endpoint              |
|-----------------|-------------|-----------------------|
| Authentication  | POST        | `/auth`               |
| Get All Bookings| GET         | `/booking`            |
| Get Booking     | GET         | `/booking/{id}`       |
| Create Booking  | POST        | `/booking`            |
| Update Booking  | PUT         | `/booking/{id}`       |
| Partial Update  | PATCH       | `/booking/{id}`       |
| Delete Booking  | DELETE      | `/booking/{id}`       |
| Health Check    | GET         | `/ping`               |

---

## Test Cases Overview

### Authentication (`authFeature.feature`)
| TC   | Description                          |
|------|--------------------------------------|
| TC1  | Valid credentials generate a token   |
| TC2  | Invalid credentials return a reason  |
| TC3  | Missing password returns 400         |
| TC4  | Missing Content-Type header returns 400 |

### Get All Booking IDs (`getAllBookingIdsFeature.feature`)
| TC   | Description                                      |
|------|--------------------------------------------------|
| TC5  | No filters – returns all bookings                |
| TC6  | Filter by firstname and lastname                 |
| TC7  | Filter by checkin and checkout date (parameterised) |
| TC8  | Invalid name filter – returns empty list         |
| TC9  | Wrong date format – returns 400                  |

### Get Booking (`getBookingFeature.feature`)
| TC     | Description                                  |
|--------|----------------------------------------------|
| TC_10  | Valid booking ID returns 200                 |
| TC_11  | Booking object is present in response        |
| TC_12  | Mandatory fields are present                 |
| TC_13  | Response data matches expected values        |
| TC_14  | Invalid booking ID returns 404               |

### Create Booking (`createBookingFeature.feature`)
| TC     | Description                                  |
|--------|----------------------------------------------|
| TC_15  | Create booking with valid data (DataTable)   |
| TC_16  | Create booking and validate booking ID       |
| TC_17  | Response data matches request data           |
| TC_18  | String value for totalprice returns 400      |
| TC_19  | Invalid date format returns 400              |
| TC_20  | Null mandatory fields return 400             |
| TC_21  | Missing totalprice and depositpaid return 400|

### Update Booking – PUT (`updateFeature.feature`)
| TC     | Description                                  |
|--------|----------------------------------------------|
| TC_22  | Missing lastname returns 400                 |
| TC_23  | Full valid update returns 200                |
| TC_24  | All fields changed – validated in response   |
| TC_25  | Booking dates updated successfully           |
| TC_26  | Missing lastname with valid price – 400      |
| TC_27  | Non-existing booking ID returns 404          |

### Partial Update – PATCH (`partialUpdateFeatue.feature`)
| TC     | Description                                  |
|--------|----------------------------------------------|
| TC_28  | Only firstname updated                       |
| TC_29  | Only totalprice updated                      |
| TC_30  | Only depositpaid updated                     |
| TC_31  | Multiple fields updated simultaneously       |
| TC_32  | Invalid token returns 403                    |

### Delete Booking (`deleteFeature.feature`)
| TC     | Description                                  |
|--------|----------------------------------------------|
| TC_33  | Valid token deletes booking – 204            |
| TC_34  | No token – deletion fails with 403           |
| TC_35  | Deleted booking GET returns 404              |
| TC_36  | Re-deleting returns 405                      |
| TC_37  | No auth provided – returns 403               |

### Health Check (`healthcheckFeature.feature`)
| TC     | Description                                  |
|--------|----------------------------------------------|
| TC_38  | `/ping` returns 200 OK                       |
| TC_39  | Multiple ping requests all succeed           |
| TC_40  | No authentication required for ping          |
| TC_41  | Uppercase `/PING` returns 200                |
| TC_42  | Invalid endpoint `/invalid` returns 404      |

---

## Setup & Prerequisites

- **Java** 11 or higher
- **Maven** 3.6+
- An internet connection to reach `https://restful-booker.herokuapp.com`

Clone the repository and install dependencies:

```bash
git clone <repository-url>
cd <project-directory>
mvn clean install -DskipTests
```

---

## Configuration

The base URL is set in:

```
src/test/resources/configurations/ConfigEnvData.properties
```

```properties
baseurl=https://restful-booker.herokuapp.com
```

Update this value to point to a different environment if needed.

---

## Test Data

Excel files are located under `src/test/resources/testdata/`:

| File                      | Used By                    | Sheet(s)                |
|---------------------------|----------------------------|-------------------------|
| `AuthData.xlsx`           | Auth & GetAllIds tests     | `Auth_data`, `FilterData` |
| `CreateBookingData.xlsx`  | Create Booking tests       | `BookingData`           |
| `PartialUpdationData.xlsx`| Partial Update tests       | `PartialUpdation`       |
| `updateBookingData.xlsx`  | Update (PUT) tests         | `updatBookingData`      |

---

## Running Tests

Run all tests:

```bash
mvn test
```

Run a specific feature/runner class:

```bash
mvn test -Dtest=AuthTest
mvn test -Dtest=CreateBookingTest
mvn test -Dtest=GetBookingTest
mvn test -Dtest=UpdateTest
mvn test -Dtest=PartialUpdateTest
mvn test -Dtest=DeleteTest
mvn test -Dtest=GetAll_IdTest
mvn test -Dtest=HealthCheckTest
```

Run tests by Cucumber tag:

```bash
mvn test -Dcucumber.filter.tags="@TC_23"
```

---

## Reporting

After test execution, the ExtentReports HTML report is generated at:

```
target/ExtentReports/extent-report.html
```

The report theme is configured to **DARK** mode via `extent-config.xml`. Open the HTML file in any browser to view a full breakdown of test results by scenario, including logs and status.

A Cucumber JSON report is also generated at `target/cucumber.json` for integration with CI tools.
