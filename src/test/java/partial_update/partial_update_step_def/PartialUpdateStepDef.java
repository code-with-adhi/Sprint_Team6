package partial_update.partial_update_step_def;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import file_utility.Token;

public class PartialUpdateStepDef {

    static {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    String token;
    int bookingId;
    Response response;

    String firstname;
    String lastname;
    boolean depositpaid;

    @Given("Generate PATCH valid authentication token")
    public void Generate_valid_authentication_token() {
        String body = "{\r\n" + //
                "    \"username\" : \"admin\",\r\n" + //
                "    \"password\" : \"password123\"\r\n" + //
                "}";
        response = RestAssured.given().contentType(ContentType.JSON).body(body)
                .when().post("/auth");

        token = response.jsonPath().getString("token");
        // Token.setToken(token);
    }

    @Given("Create a new booking")
    public void create_booking() {

        String body = "{\n" +
                "  \"firstname\": \"Susan\",\n" +
                "  \"lastname\": \"Jones\",\n" +
                "  \"totalprice\": 500,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2024-01-01\",\n" +
                "    \"checkout\": \"2024-01-05\"\n" +
                "  }\n" +
                "}";

        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/booking");

        bookingId = response.jsonPath().getInt("bookingid");
    }

    // ===== PATCH METHODS =====

    @When("Send PATCH request with firstname only")
    public void patch_firstname() {

        firstname = "Alice";

        String body = "{ \"firstname\": \"Alice\" }";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request with totalprice only")
    public void patch_totalprice() {

        String body = "{ \"totalprice\": 2500 }";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request updating depositpaid")
    public void patch_depositpaid() {

        firstname = "Susan";
        lastname = "Jones";
        depositpaid = false;

        String body = "{ \"depositpaid\": false }";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request with multiple fields")
    public void patch_multiple_fields() {

        firstname = "Robert";
        lastname = "Brown";
        depositpaid = true;

        String body = "{\n" +
                "\"firstname\": \"Robert\",\n" +
                "\"totalprice\": 3000,\n" +
                "\"depositpaid\": true\n" +
                "}";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request with invalid token")
    public void patch_invalid_token() {

        String body = "{ \"firstname\": \"Test\" }";

        response = sendPatch(body, "invalid123");
    }

    public Response sendPatch(String body, String token) {
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)
                .patch("/booking/" + bookingId);
    }

    // ===== VALIDATIONS =====

    @Then("Validate status code should be {int}")
    public void validate_status(int code) {
        assertEquals(code, response.getStatusCode());
    }

    @Then("Validate response time less than {int} ms")
    public void validate_time(int time) {
        assertTrue(response.getTime() < time);
    }

    @Then("Validate status line contains {string}")
    public void validate_status_line(String text) {
        assertTrue(response.getStatusLine().contains(text));
    }

    @Then("Validate firstname")
    public void validate_firstname() {
        assertEquals(firstname, response.jsonPath().getString("firstname"));
    }

    @Then("Validate firstname is {string}")
    public void validate_firstname_direct(String name) {
        assertEquals(name, response.jsonPath().getString("firstname"));
    }

    @Then("Validate lastname")
    public void validate_lastname() {
        assertEquals(lastname, response.jsonPath().getString("lastname"));
    }

    @Then("Validate lastname is {string}")
    public void validate_lastname_direct(String name) {
        assertEquals(name, response.jsonPath().getString("lastname"));
    }

    @Then("Validate depositpaid")
    public void validate_deposit() {
        assertEquals(depositpaid, response.jsonPath().getBoolean("depositpaid"));
    }

    @Then("Validate PATCH depositpaid is {string}")
    public void validate_deposit_direct(String val) {
        assertEquals(Boolean.parseBoolean(val),
                response.jsonPath().getBoolean("depositpaid"));
    }
}