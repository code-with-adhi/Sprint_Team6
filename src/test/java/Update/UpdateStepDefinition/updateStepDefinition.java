package Update.UpdateStepDefinition;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import ExcelUtility.excelUtility;

public class updateStepDefinition {

    static {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    excelUtility eUtil = new excelUtility();

    String token;
    int bookingId;
    Response response;

    // store values for validation
    String firstname;
    String lastname;
    boolean depositpaid;

    @Given("Create a new booking")
    public void create_booking() {

        String body = "{\n" +
                "  \"firstname\": \"Jim\",\n" +
                "  \"lastname\": \"Brown\",\n" +
                "  \"totalprice\": 111,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-01-01\",\n" +
                "    \"checkout\": \"2026-01-02\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/booking");

        bookingId = response.jsonPath().getInt("bookingid");
    }

    @Given("Generate valid authentication token")
    public void generate_valid_token() {

        String authBody = "{ \"username\":\"admin\", \"password\":\"password123\" }";

        response = given()
                .contentType(ContentType.JSON)
                .body(authBody)
                .post("/auth");

        token = response.jsonPath().getString("token");
    }

    @When("Send PUT request with complete valid body")
    public void send_put_request_with_complete_valid_body() throws Exception {

        firstname  = eUtil.getDataFromExcel("updatBookingData", 1, 1);
        lastname   = eUtil.getDataFromExcel("updatBookingData", 1, 2);
        String totalprice = eUtil.getDataFromExcel("updatBookingData", 1, 3);

        depositpaid = true;

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"lastname\": \"" + lastname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-05-01\",\n" +
                "    \"checkout\": \"2026-05-10\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request without lastname field")
    public void put_without_lastname() {

        firstname = "Sally";
        depositpaid = false;

        String body = "{\n" +
                "  \"firstname\": \"Sally\",\n" +
                "  \"totalprice\": 2000,\n" +
                "  \"depositpaid\": false,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-06-01\",\n" +
                "    \"checkout\": \"2026-06-15\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Dinner\"\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request with completely different values")
    public void put_all_fields_changed() {

        firstname = "Sally";
        lastname = "Vidhya";
        depositpaid = false;

        String body = "{\n" +
                "  \"firstname\": \"Sally\",\n" +
                "  \"lastname\": \"Vidhya\",\n" +
                "  \"totalprice\": 2000,\n" +
                "  \"depositpaid\": false,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-06-01\",\n" +
                "    \"checkout\": \"2026-06-15\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Dinner\"\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request updating booking dates")
    public void put_update_dates() {

        firstname = "Jim";
        lastname = "Smith";
        depositpaid = true;

        String body = "{\n" +
                "  \"firstname\": \"Jim\",\n" +
                "  \"lastname\": \"Smith\",\n" +
                "  \"totalprice\": 832,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-07-01\",\n" +
                "    \"checkout\": \"2026-07-20\"\n" +
                "  }\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request without lastname but with valid price and deposit")
    public void put_missing_lastname_price_valid() {

        firstname = "John";
        depositpaid = false;

        String body = "{\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"totalprice\": 3000,\n" +
                "  \"depositpaid\": false,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-08-01\",\n" +
                "    \"checkout\": \"2026-08-05\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"WiFi\"\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request with non existing booking id")
    public void put_non_existing_id() {

        firstname = "John";
        lastname = "Doe";

        String body = "{\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"totalprice\": 1200,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-05-01\",\n" +
                "    \"checkout\": \"2026-05-10\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        response = sendPutRequest(body, 999999);
    }

    public Response sendPutRequest(String body, int id) {

        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)
                .put("/booking/" + id);
    }

    // ===== VALIDATIONS =====

    @Then("Validate status code should be {int}")
    public void validate_status_code(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("Validate response time less than {int} ms")
    public void validate_response_time(int time) {
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

    @Then("Validate lastname")
    public void validate_lastname() {
        assertEquals(lastname, response.jsonPath().getString("lastname"));
    }

    @Then("Validate depositpaid")
    public void validate_depositpaid() {
        assertEquals(depositpaid, response.jsonPath().getBoolean("depositpaid"));
    }
}