package delete.StepDefinition;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import file_utility.FileUtility;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import file_utility.Token;
public class deleteBooking {

    Response response;
    static String BASE_URL;
    String authToken;
    String bookingId; 
    FileUtility fLib = new FileUtility();

    public void loadUrl() throws IOException {
        if (BASE_URL == null) {
            BASE_URL = fLib.getDataFromPropertiesFile("baseurl");
        }
        RestAssured.baseURI = BASE_URL;
    }
    
    @Given("I have a valid authentication token")
    public void i_have_a_valid_authentication_token() throws IOException {
        loadUrl();

        // Only generate if not already present
        if (Token.getToken() == null) {

            String generatedToken = given()
                    .contentType(ContentType.JSON)
                    .body("{ \"username\" : \"admin\", \"password\" : \"password123\" }")
                    .when()
                    .post("/auth")
                    .jsonPath()
                    .getString("token");

            Token.setToken(generatedToken);
        }
    }
    
    @Given("the API is up and booking exists")
    public void the_api_is_up_and_booking_exists() throws IOException {
        loadUrl();


            response = given()
                .contentType(ContentType.JSON)
                .body("{ \"firstname\": \"John\", \"lastname\": \"Doe\", \"totalprice\": 100, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2024-01-01\", \"checkout\": \"2024-01-02\" }, \"additionalneeds\": \"Breakfast\" }")
                .when()
                .post("/booking");

            bookingId = response.jsonPath().getString("bookingid"); 
        }
    @Given("I do not provide an authentication token")
    public void i_do_not_provide_an_authentication_token() {
        Token.setToken(null);   // ✅ CLEAR GLOBAL TOKEN
    }

    @Given("a booking has already been deleted")
    public void a_booking_has_already_been_deleted() throws IOException {
        loadUrl();
        // Setup for TC_35: Delete the booking first
        given()
            .header("Cookie", "token=" + Token.getToken() )
            .when()
            .delete("/booking/" + bookingId);
    }
    @When("I send a DELETE request for the booking")
    public void i_send_a_delete_request_for_the_booking() throws IOException {
        loadUrl();

        String token = Token.getToken();

        var request = given().contentType(ContentType.JSON);
        if (token != null) {
            request.header("Cookie", "token=" + token);   // ✅ correct
        }

        response = request.when().delete("/booking/" + bookingId);
    }
    @When("I send a GET request for that deleted booking ID")
    public void i_send_a_get_request_for_that_deleted_booking_id() throws IOException {
        loadUrl();
        response = when().get("/booking/" + bookingId);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedCode) {
        assertEquals((int)expectedCode, response.getStatusCode());
    }
    @Then("the response message should be {string}")
    public void the_response_message_should_be(String expectedMsg) {
        String body = response.getBody().asString();
      
        assertTrue(body.contains(expectedMsg));
    }
}