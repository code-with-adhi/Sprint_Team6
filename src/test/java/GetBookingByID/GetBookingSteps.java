package GetBookingByID;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.io.IOException;

import file_utility.FileUtility;

public class GetBookingSteps {
	Response response;
	long respTime;
	
	
	/*
	@io.cucumber.java.Before
	public void setBaseURL() throws IOException
	{
		
			FileUtility fLib = new FileUtility(); 
		    baseURI = fLib.getDataFromPropertiesFile("baseurl");
		    //System.out.println(baseURI);
	
	}
	
	
	@Given("The Base URI for The Restful Booker is set {string}")
    public void setBaseURL(String url)
   {
	    baseURI = url;	
    }
    */
	
	@When("the user send GET request with {string}")
	public void sendGetRequest(String endpoint)
	{
		response = given()
	            .when()
	            .get(endpoint);
	    //response.then().log().all();
	    respTime=response.getTime();

	}
	
	@Then("the response statuscode for get is {int}")
	public void validateResponseCode(int expcode)
	{
		assertEquals(response.getStatusCode(), expcode);
	}
	
	@Then("the response statusLine for get is {string}")
	public void validateReposeLine(String expLine)
	{
		assertTrue(response.getStatusLine().contains(expLine));
	}
	
	@Then("the response time for get is less than {long} ms")
	public void validateResponseTime(long exptime)
	{
		assertTrue(exptime>respTime);
	}
	
	@Then("the get response should contain Booking Object")
	public void validBookingObjectPresent()
	{
		assertNotNull("booking");
	}
	
	@Then("the get response should contain following mandatory fields firstname, lastname, totalprice, depositpaid, checkin, checkout")
    public void validMandatoryFieldsPresent()
    {
		assertNotNull(response.jsonPath().get("firstname"));
		assertNotNull(response.jsonPath().get("lastname"));
		assertNotNull(response.jsonPath().get("totalprice"));
		assertNotNull(response.jsonPath().get("depositpaid"));
		assertNotNull(response.jsonPath().get("bookingdates.checkin"));
		assertNotNull(response.jsonPath().get("bookingdates.checkout"));
    }
	
	@Then("the fields values should match expected data {string},{string},{int},{string},{string},{string},{string}")
	public void validateResponseData(String firstname,String lastname,int totalprice,String depositpaid,String checkin,String checkout,String additionalneeds)
	{
		
		assertEquals(firstname,response.jsonPath().get("firstname"));
		assertEquals(lastname,response.jsonPath().get("lastname"));
		assertEquals(totalprice,response.jsonPath().getInt("totalprice"));
		assertEquals(Boolean.parseBoolean(depositpaid),response.jsonPath().get("depositpaid"));
		assertEquals(checkin,response.jsonPath().get("bookingdates.checkin"));
		assertEquals(checkout,response.jsonPath().get("bookingdates.checkout"));
	}
}
