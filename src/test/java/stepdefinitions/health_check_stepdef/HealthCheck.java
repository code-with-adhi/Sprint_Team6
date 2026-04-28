package stepdefinitions.health_check_stepdef;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import utils.fileUtility.FileUtility;

public class HealthCheck {

    Response response;

    FileUtility fUtil = new FileUtility();

    
    @Given("the API is up")
    public void the_api_is_up() throws Exception {
        RestAssured.baseURI = fUtil.getDataFromPropertiesFile("baseurl");
    }

    @Given("the API is running")
    public void the_api_is_running() throws Exception {
        RestAssured.baseURI = fUtil.getDataFromPropertiesFile("baseurl");
    }

    @Given("I do not provide any authentication")
    public void i_do_not_provide_any_authentication() {
    }

  
    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {

        response = given()
                .when()
                .get(endpoint);
    }

    @When("I send multiple GET requests with following data")
    public void i_send_multiple_get_requests_with_following_data(DataTable dataTable) {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : data) {

            String endpoint = row.get("endpoint");
            int expectedStatus = Integer.parseInt(row.get("expectedStatus"));

            Response res = given()
                    .when()
                    .get(endpoint);

            assertEquals(res.getStatusCode(), expectedStatus);
        }
    }

    @Then("all responses should be successful")
    public void all_responses_should_be_successful() {
       
    }

   
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode.intValue());
    }

    @Then("the response message should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        String actual = response.getStatusLine();
        assertTrue(actual.contains(expectedMessage));
    }

    @Then("the response should be successful")
    public void the_response_should_be_successful() {
        assertTrue(response.getStatusCode() == 200);
    }
}