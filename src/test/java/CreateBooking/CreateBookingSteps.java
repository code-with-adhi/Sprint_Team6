package CreateBooking;


import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import io.cucumber.java.en.*;

public class CreateBookingSteps {

	Response response;
	long respTime;
	
	//Background
	
	@Given("The Base URL for The Restful Booker is set {String}")
	public void setBaseURL(String url)
	{
		baseURI=url;
	}
	
	
	
	
	@When("the user sends POST request {String}")
	public void sendPostRequest(String endpoint)
	{
		response=given().when().post(endpoint);
		respTime=response.getTime();
	}
	
	@Then("the response statuscode is {int}")
	public void validateStatusCode(int expcode)
	{
		assertEquals("exp",response.getStatusCode());
	}
	
	@Then("the response time is less than {long} ms")
	public void validateResponseTime(long exptime)
	{
		assertTrue(exptime>respTime);
	}
	
	@Then("the response should contain the booking object")
    public void validateBookingObject() {
        assertNotNull(response.jsonPath().get("booking"));
    }

    @Then("the response should contain the bookingid")
    public void validateBookingId() {
        assertNotNull(response.jsonPath().get("bookingid"));
    }

	
}
