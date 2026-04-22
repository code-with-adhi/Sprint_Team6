package stepdefinitions.auth_stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.excelUtility.ExcelUtilityForAuth;

import static org.hamcrest.Matchers.hasKey;

import java.util.Map;

import org.testng.Assert;

import dto.AuthObj;
import hooks.Hooks;

public class CreateDef {
    Response response;
    static String token;
    AuthObj body = Hooks.body;
    Boolean content_type_flag = Hooks.content_type_flag;

    
    @Given("valid username and password")
    public void validCreds(io.cucumber.datatable.DataTable datatable){
        Map<String,String> data= datatable.asMap();
        body.setUsername(data.get("username"));
        body.setPassword(data.get("password"));

    }

    @Given("invalid username {string} or password {string} are given")
    public void invalidCreds(String username, String password){      
        body.setUsername(username);
        body.setPassword(password);
    }

    @Given("the password field is Missing")
    public void the_password_field_is_Missing() {
        body.setUsername("admin");
    }

    @Given("content type header is Missing")
    public void content_type_header_is_Missing() throws Throwable {
        // AuthObj body = new AuthObj();
        String username = ExcelUtilityForAuth.getDataFromExcel("Auth_data", 1, 0);
        String password = ExcelUtilityForAuth.getDataFromExcel("Auth_data", 1, 1);
        body.setPassword(password);
        body.setUsername(username);
        content_type_flag = false;

    }

    @When("POST request is sent to {string} endpoint")
    public void sendRequest(String endpoint){
        RequestSpecification request = RestAssured.given();

        if(content_type_flag == true){
            request.contentType(ContentType.JSON);
        }
        response = request
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @Then("token must be generated")
    public void token_must_be_generated() {
        response.then().body("$",hasKey("token")).log().all();
        token = response.jsonPath().getString("token");
    }

    @Then("reason must be displayed")
    public void reason_must_be_displayed(){
        response.then().body("$",hasKey("reason")).log().all();
        String resaon = response.jsonPath().getString("reason");
        Assert.assertNotNull(resaon);

    }


    @Then("reason must be {string}")
    public void reason_must_be(String s){
        String res = response.jsonPath().getString("reason");
        Assert.assertEquals(res, "Bad credentials");   
    }


    @Then("status code must be {int}")
    public void status_code_must_be(int i) {
        response.then().assertThat().statusCode(i).log().all();
    }

    @Then("token length must be {int}")
    public void token_length_must_be(int i) {
        String token = response.jsonPath().getString("token");
        Assert.assertEquals(token.length(), 15);

    }

    @Then("token must be string")
    public void token_must_be_string() {
        Object token = response.jsonPath().getString("token");
        Assert.assertEquals(token.getClass().getSimpleName(), "String");
    }    
}
