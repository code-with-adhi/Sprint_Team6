package stepdefinitions.create_booking_stepdef;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

import utils.excelUtility.ExcelUtilityForCreate;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

public class CreateBookingStepDef {

    Response response;
    long respTime;
    Map<String, Object> body;
    Map<String, String> bookingDates;

    @When("the user sends the POST request {string} with following details")
    public void sendPostRequest_TC15(String endpoint, DataTable dataTable) {

        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> data : dataList) {

            bookingDates = new HashMap<>();
            bookingDates.put("checkin", data.get("checkin"));
            bookingDates.put("checkout", data.get("checkout"));

            body = new HashMap<>();
            body.put("firstname", data.get("firstname"));
            body.put("lastname", data.get("lastname"));
            body.put("totalprice", Integer.parseInt(data.get("totalprice")));
            body.put("depositpaid", Boolean.parseBoolean(data.get("depositpaid")));
            body.put("bookingdates", bookingDates);
            body.put("additionalneeds", data.get("additionalneeds"));

            response = given()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post(endpoint);

            respTime = response.getTime();
        }
    }

    @When("the user sends the POST request {string} with {string} {string} {int} {string} {string} {string} {string}")
    public void sendPostRequest_TC16(String endpoint, String firstname, String lastname,
                                     int totalprice, String depositpaid,
                                     String checkin, String checkout, String additionalneeds) {

        bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        body = new HashMap<>();
        body.put("firstname", firstname);
        body.put("lastname", lastname);
        body.put("totalprice", totalprice);
        body.put("depositpaid", Boolean.parseBoolean(depositpaid));
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", additionalneeds);

        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);

        respTime = response.getTime();
    }

    @When("the user sends the POST request {string} with tcId {string}")
    public void sendPostRequest_FromExcel(String endpoint, String tcId) throws Exception {

        ExcelUtilityForCreate eUtil = new ExcelUtilityForCreate("CreateBookingData.xlsx");

        int row = eUtil.getRowByScenario("BookingData", tcId);

        String firstname = eUtil.getDataFromExcel("BookingData", row, 1);
        String lastname = eUtil.getDataFromExcel("BookingData", row, 2);
        String totalprice = eUtil.getDataFromExcel("BookingData", row, 3);
        String depositpaid = eUtil.getDataFromExcel("BookingData", row, 4);
        String checkin = eUtil.getDataFromExcel("BookingData", row, 5);
        String checkout = eUtil.getDataFromExcel("BookingData", row, 6);
        String additionalneeds = eUtil.getDataFromExcel("BookingData", row, 7);

        bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        body = new HashMap<>();
        body.put("firstname", firstname);
        body.put("lastname", lastname);

        if (tcId.equals("TC_18")) {
            body.put("totalprice", totalprice);
            body.put("depositpaid", Boolean.parseBoolean(depositpaid));
        } 
        else if (tcId.equals("TC_20")) {
            body.put("totalprice", null);
            body.put("depositpaid", null);
        } 
        else {
            body.put("totalprice", Integer.parseInt(totalprice));
            body.put("depositpaid", Boolean.parseBoolean(depositpaid));
        }

        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", additionalneeds);

        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);

        respTime = response.getTime();
    }

    @When("the user sends the POST request {string} with missing fields {string},{string},{string},{string},{string}")
    public void sendPostRequest_TC21(String endpoint, String firstname, String lastname,
                                     String checkin, String checkout, String additionalneeds) {

        bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        body = new HashMap<>();
        body.put("firstname", firstname);
        body.put("lastname", lastname);
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", additionalneeds);

        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);

        respTime = response.getTime();
    }

    @Then("the response statuscode for post is {int}")
    public void validateStatusCode(int expcode) {
        assertEquals(response.getStatusCode(), expcode);
    }

    @Then("the response statusLine for post is {string}")
    public void validateStatusLine(String expLine) {
        assertTrue(response.getStatusLine().contains(expLine));
    }

    @Then("the response time for post is less than {int} ms")
    public void validateResponseTime(long exptime) {
        assertTrue(respTime < exptime);
    }

    @Then("the post response should contain the bookingid")
    public void validateBookingId() {
        assertNotNull(response.jsonPath().get("bookingid"));
    }

    @Then("the post response should contain the booking object")
    public void validateBookingObject() {
        assertNotNull(response.jsonPath().get("booking"));
    }

    @Then("Validate the post response matches request data")
    public void validateResponseMatchesRequest() {

        assertEquals(body.get("firstname"), response.jsonPath().getString("booking.firstname"));
        assertEquals(body.get("lastname"), response.jsonPath().getString("booking.lastname"));

        assertEquals(body.get("totalprice"), response.jsonPath().get("booking.totalprice"));
        assertEquals(body.get("depositpaid"), response.jsonPath().get("booking.depositpaid"));

        assertEquals(bookingDates.get("checkin"),
                response.jsonPath().getString("booking.bookingdates.checkin"));

        assertEquals(bookingDates.get("checkout"),
                response.jsonPath().getString("booking.bookingdates.checkout"));
    }
}