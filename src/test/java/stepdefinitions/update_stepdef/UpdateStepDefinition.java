package stepdefinitions.update_stepdef;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.Map;
import io.cucumber.datatable.DataTable;
import java.util.List;
import utils.excelUtility.ExcelUtilityUpdate;
//import file_utility.Token;

public class UpdateStepDefinition {

    ExcelUtilityUpdate eUtil = new ExcelUtilityUpdate();

    String token;
    int bookingId;
    Response response;

    // store values for validation
    String firstname;
    String lastname;
    boolean depositpaid;

    @Given("Generate valid authentication token")
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

    @When("Send PUT request without lastname field using {string} {string} {string}")
    public void put_without_lastname(String firstname, String totalprice, String depositpaid) {

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": " + depositpaid + ",\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-06-01\",\n" +
                "    \"checkout\": \"2026-06-15\"\n" +
                "  }\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request with complete valid body")
    public void send_put_request_with_complete_valid_body(DataTable dataTable) {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);

        firstname = row.get("firstname");
        lastname = row.get("lastname");
        String totalprice = row.get("totalprice");
        depositpaid = Boolean.parseBoolean(row.get("depositpaid"));
        String checkin = row.get("checkin");
        String checkout = row.get("checkout");
        String additionalneeds = row.get("additionalneeds");

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"lastname\": \"" + lastname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": " + depositpaid + ",\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"" + checkin + "\",\n" +
                "    \"checkout\": \"" + checkout + "\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"" + additionalneeds + "\"\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request with completely different values")
    public void put_all_fields_changed() throws Exception {

        int row = 3;

        firstname = eUtil.getDataFromExcel("updatBookingData", row, 1);
        lastname = eUtil.getDataFromExcel("updatBookingData", row, 2);
        String totalprice = eUtil.getDataFromExcel("updatBookingData", row, 3);
        depositpaid = Boolean.parseBoolean(eUtil.getDataFromExcel("updatBookingData", row, 4));

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"lastname\": \"" + lastname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": " + depositpaid + ",\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-06-01\",\n" +
                "    \"checkout\": \"2026-06-15\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Dinner\"\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request updating booking dates")
    public void put_update_dates() throws Exception {

        int row = 4;

        firstname = eUtil.getDataFromExcel("updatBookingData", row, 1);
        lastname = eUtil.getDataFromExcel("updatBookingData", row, 2);
        depositpaid = Boolean.parseBoolean(eUtil.getDataFromExcel("updatBookingData", row, 4));

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"lastname\": \"" + lastname + "\",\n" +
                "  \"totalprice\": 832,\n" +
                "  \"depositpaid\": " + depositpaid + ",\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-07-01\",\n" +
                "    \"checkout\": \"2026-07-20\"\n" +
                "  }\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request without lastname but with valid price and deposit")
    public void put_missing_lastname_price_valid() throws Exception {

        int row = 5;

        firstname = eUtil.getDataFromExcel("updatBookingData", row, 1);
        depositpaid = Boolean.parseBoolean(eUtil.getDataFromExcel("updatBookingData", row, 4));
        String totalprice = eUtil.getDataFromExcel("updatBookingData", row, 3);

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": " + depositpaid + ",\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-08-01\",\n" +
                "    \"checkout\": \"2026-08-05\"\n" +
                "  }\n" +
                "}";

        response = sendPutRequest(body, bookingId);
    }

    @When("Send PUT request with non existing booking id")
    public void put_non_existing_id() throws Exception {

        int row = 6;

        firstname = eUtil.getDataFromExcel("updatBookingData", row, 1);
        lastname = eUtil.getDataFromExcel("updatBookingData", row, 2);
        String totalprice = eUtil.getDataFromExcel("updatBookingData", row, 3);
        depositpaid = Boolean.parseBoolean(eUtil.getDataFromExcel("updatBookingData", row, 4));

        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"lastname\": \"" + lastname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": " + depositpaid + ",\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-05-01\",\n" +
                "    \"checkout\": \"2026-05-10\"\n" +
                "  }\n" +
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
        assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch");
    }

    @Then("Validate response time less than {int} ms")
    public void validate_response_time(int time) {
        assertTrue(response.getTime() < time, "Response time exceeded limit");
    }

    @Then("Validate status line contains {string}")
    public void validate_status_line(String text) {
        assertTrue(response.getStatusLine().contains(text), "Status line mismatch");
    }

    @Then("Validate firstname")
    public void validate_firstname() {
        assertEquals(response.jsonPath().getString("firstname"), firstname, "Firstname mismatch");
    }

    @Then("Validate lastname")
    public void validate_lastname() {
        assertEquals(response.jsonPath().getString("lastname"), lastname, "Lastname mismatch");
    }

    @Then("Validate depositpaid")
    public void validate_depositpaid() {
        assertEquals(response.jsonPath().getBoolean("depositpaid"), depositpaid, "DepositPaid mismatch");
    }
}