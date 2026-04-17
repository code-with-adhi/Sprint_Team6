package Update.UpdateStepDefinition;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;



import ExcelUtility.excelUtility;

public class updateStepDefinition {

    excelUtility eUtil = new excelUtility();

    String token;
    int bookingId = 1;
    Response response;

    @Given("Generate valid authentication token")
    public void generate_valid_token() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String authBody = "{ \"username\":\"admin\", \"password\":\"password123\" }";

        response = given()
                .contentType(ContentType.JSON)
                .body(authBody)
                .post("/auth");

        token = response.jsonPath().getString("token");
    }

    @When("Send PUT request with complete valid body")
    public void send_put_request_with_complete_valid_body() throws Exception {

        String firstname = eUtil.getDataFromExcel("Sheet1", 1, 0);
        String lastname = eUtil.getDataFromExcel("Sheet1", 1, 1);
        String totalprice = eUtil.getDataFromExcel("Sheet1", 1, 2);

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

    @Then("Validate status code should be {int}")
    public void validate_status_code(int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }
}