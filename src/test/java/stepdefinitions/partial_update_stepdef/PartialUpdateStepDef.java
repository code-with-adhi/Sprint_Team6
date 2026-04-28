package stepdefinitions.partial_update_stepdef;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import utils.excelUtility.ExcelUtilityPartialUpdate;

import utils.fileUtility.Token;
import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.Map;

public class PartialUpdateStepDef {

    String token;
    int bookingId;
    Response response;

    String firstname;
    String lastname;
    boolean depositpaid;
    ExcelUtilityPartialUpdate eUtil = new ExcelUtilityPartialUpdate();

    @Given("Generate PATCH valid authentication token")
    public void Generate_valid_authentication_token() {
        String body = "{\r\n" + 
                "    \"username\" : \"admin\",\r\n" + 
                "    \"password\" : \"password123\"\r\n" + 
                "}";
        response = RestAssured.given().contentType(ContentType.JSON).body(body)
                .when().post("/auth");

        token = response.jsonPath().getString("token");

        Token.setToken(token);
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

    @When("Send PATCH request with firstname {string} only")
    public void patch_firstname(String firstname) {

        String body = "{ \"firstname\": \"" + firstname + "\" }";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request with totalprice only")
    public void patch_totalprice(DataTable dataTable) {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);

        String totalprice = row.get("totalprice");

        String body = "{ \"totalprice\": " + totalprice + " }";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request updating depositpaid")
    public void patch_depositpaid() {

        int row = 3;

        depositpaid = Boolean.parseBoolean(
                eUtil.getDataFromExcel("PartialUpdation", row, 2));

        String body = "{ \"depositpaid\": " + depositpaid + " }";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request with multiple fields")
    public void patch_multiple_fields() {

        int row = 4;

        firstname = eUtil.getDataFromExcel("PartialUpdation", row, 1);
        String totalprice = eUtil.getDataFromExcel("PartialUpdation", row, 2);
        depositpaid = Boolean.parseBoolean(
                eUtil.getDataFromExcel("PartialUpdation", row, 3));

        String body = "{\n" +
                "\"firstname\": \"" + firstname + "\",\n" +
                "\"totalprice\": " + totalprice + ",\n" +
                "\"depositpaid\": " + depositpaid + "\n" +
                "}";

        response = sendPatch(body, token);
    }

    @When("Send PATCH request with invalid token")
    public void patch_invalid_token() {

        int row = 5;

        firstname = eUtil.getDataFromExcel("PartialUpdation", row, 0);

        String body = "{ \"firstname\": \"" + firstname + "\" }";

        response = sendPatch(body, "invalid123");
    }

    public Response sendPatch(String body, String token) {
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)
                .patch("/booking/" + bookingId);
    }

  
    @Then("Validate status code should be {int}")
    public void validate_status(int code) {
        assertEquals(response.getStatusCode(), code, "Status code mismatch");
    }

    @Then("Validate response time less than {int} ms")
    public void validate_time(int time) {
        assertTrue(response.getTime() < time, "Response time exceeded limit");
    }

    @Then("Validate status line contains {string}")
    public void validate_status_line(String text) {
        assertTrue(response.getStatusLine().contains(text), "Status line mismatch");
    }

    @Then("Validate firstname is {string}")
    public void validate_firstname_direct(String expectedFirstname) {
        assertEquals(response.jsonPath().getString("firstname"),
                expectedFirstname,
                "Firstname mismatch");
    }

    @Then("Validate lastname is {string}")
    public void validate_lastname_direct(String expectedLastname) {
        assertEquals(response.jsonPath().getString("lastname"),
                expectedLastname,
                "Lastname mismatch");
    }

    @Then("Validate depositpaid")
    public void validate_deposit() {
        assertEquals(response.jsonPath().getBoolean("depositpaid"), depositpaid, "DepositPaid mismatch");
    }

    @Then("Validate PATCH depositpaid is {string}")
    public void validate_deposit_direct(String val) {
        assertEquals(response.jsonPath().getBoolean("depositpaid"),
                Boolean.parseBoolean(val),
                "DepositPaid mismatch");
    }
}