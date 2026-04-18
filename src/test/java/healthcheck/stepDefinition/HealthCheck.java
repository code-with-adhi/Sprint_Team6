package healthcheck.stepDefinition;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import file_utility.FileUtility;
import io.restassured.response.Response;
import io.cucumber.java.en.*;
public class HealthCheck {
	    Response response;
	    long responseTime;

	    String baseurl;

	   FileUtility fUtil = new FileUtility();

	    // ---------- GIVEN ----------

	    @Given("the API is up")
	    public void the_api_is_up() throws Exception {
	        baseurl = fUtil.getDataFromPropertiesFile("baseurl");
	        baseURI = baseurl;
	    }

	    @Given("the API is running")
	    public void the_api_is_running() throws Exception {
	        baseurl = fUtil.getDataFromPropertiesFile("baseurl");
	        baseURI = baseurl;
	    }

	    @Given("I do not provide any authentication")
	    public void i_do_not_provide_any_authentication() {
	        
	    }

	    // ---------- WHEN ----------

	    @When("I send a GET request to {string}")
	    public void i_send_a_get_request_to(String endpoint) {
	        response = given()
	                    .when()
	                    .get(endpoint);

	        responseTime = response.getTime();
	    }

	    @When("I send multiple GET requests to {string}")
	    public void i_send_multiple_get_requests_to(String endpoint) {

	        for (int i = 0; i < 3; i++) {
	            response = given()
	                        .when()
	                        .get(endpoint);

	            assertEquals(201, response.getStatusCode());
	        }
	    }

	    // ---------- THEN ----------

	    @Then("the response status code should be {int}")
	    public void the_response_status_code_should_be(Integer expectedStatusCode) {
	        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
	    }

	    @Then("the response message should be {string}")
	    public void the_response_message_should_be(String expectedMessage) {
	        String actual = response.getStatusLine();
	        assertTrue(actual.contains(expectedMessage));
	    }

	    @Then("the response should be successful")
	    public void the_response_should_be_successful() {
	        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 201);
	    }

	    @Then("all responses should have status code {int}")
	    public void all_responses_should_have_status_code(Integer expectedStatusCode) {
	        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
	    }

	    @Then("the response should always be successful")
	    public void the_response_should_always_be_successful() {
	        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 201);
	    }

	    @Then("the response time should be less than {int} seconds")
	    public void the_response_time_should_be_less_than_seconds(Integer seconds) {
	        long timeInSeconds = responseTime / 1000;
	        assertTrue(timeInSeconds < seconds);
	    }
	}
