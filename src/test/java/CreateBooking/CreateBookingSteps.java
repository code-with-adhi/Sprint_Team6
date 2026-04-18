package CreateBooking;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;

import excelutility.ExcelUtility;
import io.cucumber.java.en.*;

public class CreateBookingSteps {

	Response response;
	long respTime;
	Map<String, Object> body = new HashMap<>();
	Map<String, String> bookingDates = new HashMap<>();
	//Background
	
	@Given("The Base URL for The Restful Booker is set {string}")
	public void setBaseURL(String url)
	{
		baseURI=url;
	}
	
	//When
	
	@When("the user sends the POST request {string} with tcId {string}")
	public void sendPostRequest(String endpoint, String tcId) throws Exception {

	    ExcelUtility eUtil = new ExcelUtility();

	    int row = eUtil.getRowByTcId("Sheet1", tcId);

	    String firstname = eUtil.getDataFromExcel("Sheet1", row, 1);
	    String lastname = eUtil.getDataFromExcel("Sheet1", row, 2);
	    int totalprice = Integer.parseInt(eUtil.getDataFromExcel("Sheet1", row, 3));
	    boolean depositpaid = Boolean.parseBoolean(eUtil.getDataFromExcel("Sheet1", row, 4));
	    String checkin = eUtil.getDataFromExcel("Sheet1", row, 5);
	    String checkout = eUtil.getDataFromExcel("Sheet1", row, 6);
	    String additionalneeds = eUtil.getDataFromExcel("Sheet1", row, 7);

	   
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
	    response.then().log().all();
	    respTime=response.getTime();
	}
	
	@Then("the response statuscode for post is {int}")
	public void validateStatusCode(int expcode)
	{
		int actual = response.getStatusCode();
		assertEquals(actual,expcode);
	}
	
	@Then("the response statusLine for post is {string}")
	public void validateReposeLine(String expLine)
	{
		assertEquals(response.getStatusLine(), expLine);
	}
	
	@Then("the response time for post is less than {int} ms")
	public void validateResponseTime(long exptime)
	{
		assertTrue(exptime>respTime);
	}
	
	@Then("the post response should contain the bookingid")
	public void validateBookingId()
	{
		assertNotNull(response.jsonPath().get("bookingid"));
	}
	
	@Then("the post response should contain the booking object")
	public void checkBookingObject()
	{
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
