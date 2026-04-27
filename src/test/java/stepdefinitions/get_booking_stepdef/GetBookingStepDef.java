package stepdefinitions.get_booking_stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.Map;

import java.util.*;

public class GetBookingStepDef {
	Response response;
	long respTime;
	int id;

	@When("the user send GET request with {string}")
	public void sendGetRequest(String endpoint) {

		String body = "{ \"firstname\":\"Raj\", \"lastname\":\"Test\", \"totalprice\":100, \"depositpaid\":true, \"bookingdates\":{ \"checkin\":\"2024-01-01\", \"checkout\":\"2024-01-02\" }}";

		Response res = given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		id = res.jsonPath().getInt("bookingid");

		System.out.println("Generated ID in Hook: " + id);

		response = given()
				.when()
				.get(endpoint + "/" + id);
		// response.then().log().all();
		respTime = response.getTime();

	}

	@When("the user send GET request with {string} with invalid Id")
	public void sendGetRequestForInvalidId(String endpoint,DataTable datatable) {
		 List<Map<String, String>> dataList = datatable.asMaps(String.class, String.class);

    for (Map<String, String> data : dataList) {

        String id = data.get("id");

		response = given()
				.when()
				.get(endpoint+"/"+id);
		// response.then().log().all();
		respTime = response.getTime();

	}
}

	@Then("the response statuscode for get is {int}")
	public void validateResponseCode(int expcode) {
		assertEquals(response.getStatusCode(), expcode);
	}

	@Then("the response statusLine for get is {string}")
	public void validateReposeLine(String expLine) {
		assertTrue(response.getStatusLine().contains(expLine));
	}

	@Then("the response time for get is less than {long} ms")
	public void validateResponseTime(long exptime) {
		assertTrue(exptime > respTime);
	}

	@Then("the get response should contain Booking Object")
	public void validBookingObjectPresent() {
		assertNotNull(response.getBody());
	}

	@Then("the get response should contain following mandatory fields firstname, lastname, totalprice, depositpaid, checkin, checkout")
	public void validMandatoryFieldsPresent() {
		assertNotNull(response.jsonPath().get("firstname"));
		assertNotNull(response.jsonPath().get("lastname"));
		assertNotNull(response.jsonPath().get("totalprice"));
		assertNotNull(response.jsonPath().get("depositpaid"));
		assertNotNull(response.jsonPath().get("bookingdates.checkin"));
		assertNotNull(response.jsonPath().get("bookingdates.checkout"));
	}

	@Then("the fields values should match expected data {string},{string},{int},{string},{string},{string}")
	public void validateResponseData(String firstname, String lastname, int totalprice, String depositpaid,
			String checkin, String checkout) {

		assertEquals(firstname, response.jsonPath().get("firstname"));
		assertEquals(lastname, response.jsonPath().get("lastname"));
		assertEquals(totalprice, response.jsonPath().getInt("totalprice"));
		assertEquals(Boolean.parseBoolean(depositpaid), response.jsonPath().get("depositpaid"));
		assertEquals(checkin, response.jsonPath().get("bookingdates.checkin"));
		assertEquals(checkout, response.jsonPath().get("bookingdates.checkout"));
	}
}