package stepdefinitions.get_all_ids_stepdef;

import java.util.HashMap;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAll_IdDef {
    Response R;
    @Given("Base URI is set to retrieve data")
    public void getAll(){
        // RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    @When("no parameters to filter")
    public void no_parameters_to_filter() {
        R = RestAssured.given()
        .when().get("/booking");
    }

    
    @When("fliter with name")
    public void fliter_with_name() {
        HashMap<String, String> hp = new HashMap<>();
        hp.put("firstname", "Sally");
        hp.put("lastname", "Brown");
        R = RestAssured.given().queryParams(hp)
        .when().get("/booking");
    }
    
    @When("filtering with date")
    public void filtering_with_date() {
        // HashMap<String, String> hp = new HashMap<>();
        // hp.put("checkin", "2014-03-16");

        R = RestAssured.given().queryParam("checkin","2014-03-16")
        .when().get("/booking");
    }
    
    @When("date format is wrong")
    public void date_format_is_wrong() {
        R = RestAssured.given().queryParam("checkin","16-2014-03")
        .when().get("/booking");
    }
    
    @When("no valid data is present for the filter")
    public void no_valid_data_is_present_for_the_filter() {
        R = RestAssured.given().queryParam("firstname","adhi")
        .when().get("/booking");        
    }
    
    @Then("status code must be {int}")
    public void StatusCheck(int i){
        R.then().assertThat().statusCode(i).log().all();
    }


}
