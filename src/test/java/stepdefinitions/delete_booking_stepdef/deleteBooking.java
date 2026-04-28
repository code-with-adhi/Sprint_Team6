package stepdefinitions.delete_booking_stepdef;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import utils.fileUtility.Token;

public class deleteBooking {

    Response response;
    String bookingId;

    // =========================================
    // PRECONDITION → CREATE BOOKING (DYNAMIC)
    // =========================================
    @Given("the API is up and booking exists")
    public void the_api_is_up_and_booking_exists() {

        response = given()
                .contentType(ContentType.JSON)
                .body("{ \"firstname\": \"John\", " +
                        "\"lastname\": \"Doe\", " +
                        "\"totalprice\": 100, " +
                        "\"depositpaid\": true, " +
                        "\"bookingdates\": { \"checkin\": \"2024-01-01\", \"checkout\": \"2024-01-02\" }, " +
                        "\"additionalneeds\": \"Breakfast\" }")
                .when()
                .post("/booking");

        bookingId = response.jsonPath().getString("bookingid");
    }

    // =========================================
    // AUTH TOKEN
    // =========================================
    @Given("I have a valid authentication token")
    public void i_have_a_valid_authentication_token() {

        if (Token.getToken() == null) {
            String token = given()
                    .contentType(ContentType.JSON)
                    .body("{ \"username\" : \"admin\", \"password\" : \"password123\" }")
                    .post("/auth")
                    .jsonPath()
                    .getString("token");

            Token.setToken(token);
        }
    }

    @Given("I do not provide an authentication token")
    public void i_do_not_provide_an_authentication_token() {
        Token.setToken(null);
    }

    // =========================================
    // DELETE REQUEST
    // =========================================
    @When("I send a DELETE request for the booking")
    public void i_send_a_delete_request_for_the_booking() {

        var request = given();

        if (Token.getToken() != null) {
            request.header("Cookie", "token=" + Token.getToken());
        }

        response = request.when().delete("/booking/" + bookingId);
    }

    // =========================================
    // DELETE AGAIN (TC_36)
    // =========================================
    @When("I send a DELETE request for the same booking again")
    public void i_send_delete_again() {

        response = given()
                .header("Cookie", "token=" + Token.getToken())
                .when()
                .delete("/booking/" + bookingId);
    }

    // =========================================
    // DATA TABLE (TC_34)
    // =========================================
    @When("I perform delete operation with following data")
    public void i_perform_delete_operation_with_following_data(DataTable dataTable) {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : data) {

            String auth = row.get("auth");
            int expectedStatus = Integer.parseInt(row.get("expectedStatus"));
            String expectedMessage = row.get("expectedMessage");

            if (auth.equalsIgnoreCase("valid")) {
                i_have_a_valid_authentication_token();
            } else {
                Token.setToken(null);
            }

            var request = given();

            if (Token.getToken() != null) {
                request.header("Cookie", "token=" + Token.getToken());
            }

            Response res = request.when().delete("/booking/" + bookingId);

            assertEquals(res.getStatusCode(), expectedStatus);
            assertTrue(res.getBody().asString().contains(expectedMessage));
        }
    }

    @Then("I should validate all responses")
    public void i_should_validate_all_responses() {
        // already validated
    }

    // =========================================
    // DELETE + VERIFY (TC_35)
    // =========================================
    @Given("I delete the booking")
    public void i_delete_the_booking() {

        given()
            .header("Cookie", "token=" + Token.getToken())
        .when()
            .delete("/booking/" + bookingId);
    }

    @When("I send a GET request for that deleted booking ID")
    public void i_send_a_get_request_for_that_deleted_booking_id() {

        response = when().get("/booking/" + bookingId);
    }

    // =========================================
    // VALIDATIONS
    // =========================================
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedCode) {
        assertEquals(response.getStatusCode(), expectedCode.intValue());
    }

    @Then("the response message should be {string}")
    public void the_response_message_should_be(String expectedMsg) {
        String body = response.getBody().asString();
        assertTrue(body.contains(expectedMsg));
    }
}