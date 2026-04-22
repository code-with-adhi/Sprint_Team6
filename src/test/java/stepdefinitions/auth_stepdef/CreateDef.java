package stepdefinitions.auth_stepdef;

// import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.excelUtility.ExcelUtilityForAuth;

import static org.hamcrest.Matchers.hasKey;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import dto.AuthObj;

// import file_utility.Token;

public class CreateDef {
    Response response;
    static String token;

    @Given("Base URI is set to create the token")
    public void setURI(){
        // RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @When("the {string} and {string} are given properly")
    public void createToken(String username, String password){
        AuthObj body = new AuthObj();

        body.setUsername(username);
        body.setPassword(password);
        
        response = RestAssured.given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }
    
    @When("the password is wrong")
    public void the_password_is_wrong(io.cucumber.datatable.DataTable datatable) {
        Map<String,String> data= datatable.asMap();
        
        AuthObj body = new AuthObj();

        body.setUsername(data.get("username"));
        body.setPassword(data.get("password"));

        response = RestAssured.given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @When("the password field is Missing")
    public void the_password_field_is_Missing() {
        HashMap<String,String> body= new HashMap<>();

        body.put("username", "admin");

        response = RestAssured.given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @When("content type header is Missing")
    public void content_type_header_is_Missing() throws Throwable {
        AuthObj body = new AuthObj();
        
        String username = ExcelUtilityForAuth.getDataFromExcel("Auth_data", 1, 0);

        String password = ExcelUtilityForAuth.getDataFromExcel("Auth_data", 1, 1);


        body.setPassword(password);

        body.setUsername(username);

        // String body = "{\r\n" + //
        //                 "    \"username\" : \"admin\",\r\n" + //
        //                 "    \"password\" : \"password123\"\r\n" + //
        //                 "}";

        response = RestAssured.given()
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @Then("token must be generated")
    public void token_must_be_generated() {
        response.then().body("$",hasKey("token")).log().all();
        token = response.jsonPath().getString("token");
        // Token.setToken(token);
        // Assert.assertEquals(token.length(), 15);
        // response.then().log().all();
    }

    @Then("reason must be displayed")
    public void reason_must_be_displayed(){
        response.then().body("$",hasKey("reason")).log().all();
        String resaon = response.jsonPath().getString("reason");
        Assert.assertNotNull(resaon);

    }


    @Then("reason must be {string}")
    public void reason_must_be(String s){
        // response.then().assertThat().statusCode(i).body("$",hasKey("reason")).log().all();
        String res = response.jsonPath().getString("reason");
        Assert.assertEquals(res, "Bad credentials");   
    }

    @Then("status code must be {int}")
    public void status_code_must_be(int i) {
        response.then().assertThat().statusCode(i).log().all();
        // response.then().log().all();
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
