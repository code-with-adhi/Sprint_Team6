package Auth.StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateDef {
    Response response;

    @Given("Base URI is set to create the token")
    public void setURI(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @When("username and password are given properly")
    public void createToken(){
        String body = "{\r\n" + //
                        "    \"username\" : \"admin\",\r\n" + //
                        "    \"password\" : \"password123\"\r\n" + //
                        "}";

        response = RestAssured.given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }
    
    @When("the password is wrong")
    public void the_password_is_wrong() {
        String body = "{\r\n" + //
                        "    \"username\" : \"admin\",\r\n" + //
                        "    \"password\" : \"password\"\r\n" + //
                        "}";

        response = RestAssured.given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @When("the password field is Missing")
    public void the_password_field_is_Missing() {
        String body = "{\r\n" + //
                        "    \"username\" : \"admin\"\r\n" + //
                        "}";

        response = RestAssured.given().contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @When("content type header is Missing")
    public void content_type_header_is_Missing() {
        String body = "{\r\n" + //
                        "    \"username\" : \"admin\",\r\n" + //
                        "    \"password\" : \"password123\"\r\n" + //
                        "}";

        response = RestAssured.given()
                .body(body)
                .log().all()
                .when()
                .post("/auth");
    }

    @Then("token must be generated")
    public void token_must_be_generated() {
        response.then().log().all();
    }

    @Then("status code must be {int} and reason must be displayed")
    public void status_code_must_be_and_reason_must_be_displayed(int i) {
        response.then().log().all();
    }


    @Then("status code must be {int}")
    public void status_code_must_be(int i) {
        response.then().log().all();
    }

    
}
