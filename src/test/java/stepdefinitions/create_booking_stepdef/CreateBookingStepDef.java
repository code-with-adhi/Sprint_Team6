package stepdefinitions.create_booking_stepdef;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;

import utils.excelUtility.ExcelUtilityForCreate;
import utils.fileUtility.*;
import io.cucumber.java.en.*;

public class CreateBookingStepDef {

	Response response;
	long respTime;
	Map<String, Object> body;
	Map<String, String> bookingDates;
	
	
	//Scenario Outline
	@When("the user sends the POST request {string} with {string} {string} {string} {boolean} {string} {string} {string}")
	public void sendPostRequest_tc15(String endpoint, String firstname, String lastname, String totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds)
	{
		String requestBody = "{\n" +
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
		
		response=given().contentType(ContentType.JSON)
		.body(req).when().post(endpoint);
		 respTime = response.getTime();

		
	}

	@When("the user sends the POST request {string} with tcId")
	public void sendPostRequest(String endpoint, String tcId) throws Exception {

		ExcelUtilityForCreate eUtil = new ExcelUtilityForCreate("CreateBookingData.xlsx");
		int row = eUtil.getRowByTcId("Sheet1", tcId);

		String firstname = eUtil.getDataFromExcel("Sheet1", row, 1);
		String lastname = eUtil.getDataFromExcel("Sheet1", row, 2);
		int totalprice = Integer.parseInt(eUtil.getDataFromExcel("Sheet1", row, 3));
		boolean depositpaid = Boolean.parseBoolean(eUtil.getDataFromExcel("Sheet1", row, 4));
		String checkin = eUtil.getDataFromExcel("Sheet1", row, 5);
		String checkout = eUtil.getDataFromExcel("Sheet1", row, 6);
		String additionalneeds = eUtil.getDataFromExcel("Sheet1", row, 7);

		body = new HashMap<>();
		bookingDates = new HashMap<>();

		bookingDates.put("checkin", checkin);
		bookingDates.put("checkout", checkout);

		body.put("firstname", firstname);
		body.put("lastname", lastname);
		body.put("totalprice", totalprice);
		body.put("depositpaid", depositpaid);
		body.put("bookingdates", bookingDates);
		body.put("additionalneeds", additionalneeds);

		response = given()
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.post(endpoint);
		// response.then().log().all();
		respTime = response.getTime();
	}

	@Then("the response statuscode for post is {int}")
	public void validateStatusCode(int expcode) {
		int actual = response.getStatusCode();
		assertEquals(actual, expcode);

	}

	@Then("the response statusLine for post is {string}")
	public void validateReposeLine(String expLine) {
		assertTrue(response.getStatusLine().contains(expLine));
	}

	@Then("the response time for post is less than {int} ms")
	public void validateResponseTime(long exptime) {
		assertTrue(exptime > respTime);
	}

	@Then("the post response should contain the bookingid")
	public void validateBookingId() {
		assertNotNull(response.jsonPath().get("bookingid"));
	}

	@Then("the post response should contain the booking object")
	public void checkBookingObject() {
		assertNotNull(response.jsonPath().get("booking"));
	}

	@Then("Validate the post response matches request data")
	public void validateResponseMatchesRequest() {

		assertEquals(body.get("firstname"), response.jsonPath().getString("booking.firstname"));
		assertEquals(body.get("lastname"), response.jsonPath().getString("booking.lastname"));

		assertEquals((int) body.get("totalprice"), response.jsonPath().getInt("booking.totalprice"));
		assertEquals((boolean) body.get("depositpaid"), response.jsonPath().getBoolean("booking.depositpaid"));

		assertEquals(bookingDates.get("checkin"), response.jsonPath().getString("booking.bookingdates.checkin"));
		assertEquals(bookingDates.get("checkout"), response.jsonPath().getString("booking.bookingdates.checkout"));
	}

}