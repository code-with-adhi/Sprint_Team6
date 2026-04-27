package stepdefinitions.get_all_ids_stepdef;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.excelUtility.ExcelUtilityForAuth;

public class GetAll_IdDef {
    Response R;
    Response checkinresp;
    Response checkoutresp;

    @When("no parameters to filter")
    public void withoutFilter() {
        R = RestAssured.given()
        .when().get("/booking");
    }

    @When("filter with firstname and lastname")
    public void nameFilter(DataTable DT) {
       Map<String, String> dates = DT.asMap();
        R = RestAssured.given()
                .queryParam("firstname", dates.get("Firstname" ))
                .queryParam("lastname", dates.get("Lastname" ))
                .when()
                .log().all()
                .get("/booking");
    }
    
    @When("filtering with checkin {string} date and checkout {string} date")
    public void dateFilter(String CIdate,String COdate) {
       
        checkinresp = RestAssured.given().queryParam("checkin",CIdate)
        // .log().all()
        .when().get("/booking");


        checkoutresp = RestAssured.given().queryParam("checkout",COdate)
        // .log().all()
        .when().get("/booking");     

        R = RestAssured.given()
        .queryParam("checkin",CIdate)
        .queryParam("checkout",COdate)
        //    .log().all()
        .when()
        .get("/booking");
        
    }
    
    @When("date from {string} in excel format is wrong")
    public void wrongFormat(String row) throws NumberFormatException, Throwable {
        String checkin = ExcelUtilityForAuth.getDataFromExcel("FilterData", Integer.parseInt(row),2);
        String checkout = ExcelUtilityForAuth.getDataFromExcel("FilterData", Integer.parseInt(row),3);

        R = RestAssured.given()
        .queryParam("checkin",checkin)
        .queryParam("checkout",checkout)
        .when().get("/booking");
    }
    
    @When("no valid data is present for {string} row in excel")
    public void invalidFilter(String row) throws Throwable {
        String firstname = ExcelUtilityForAuth.getDataFromExcel("FilterData", Integer.parseInt(row),0);
        String lastname = ExcelUtilityForAuth.getDataFromExcel("FilterData", Integer.parseInt(row),1);
        R = RestAssured.given()
        .queryParam("firstname",firstname)
        .queryParam("lastname", lastname)
        .when().get("/booking");        
        
    }
    
    @Then("status code must be {int}")
    public void StatusCheck(int i){
        R.then().assertThat().statusCode(i).log().all();
    }


    @Then("response should have multiple json objects")
    public void multipleObj() {
        R.then().body("size()", greaterThan(0));
    }

    @Then("response should have no data")
    public void emptyCheck() {
        R.then().body("size()", lessThan(1));
    }

    @Then("response must be proper for checkout dates")
    public void checkOut_val() {
        checkoutresp.then().body("size()", greaterThan(0));
        
    }

    @Then("response must be proper for checkin dates")
    public void checkIn_val() {
        checkinresp.then().body("size()", greaterThan(0));
        
    }

    @Then("response must be proper for checkin and checkout dates")
    public void CheckIn_Out_val() {
        R.then().body("size()", greaterThan(0));
    }

}
