package Hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.restassured.RestAssured;

public class hooks {

    @Before
    public void beforeScenario() {
        System.out.println("=== Test Started ===");

        // Set Base URI globally
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @After
    public void afterScenario() {
        System.out.println("=== Test Finished ===");
    }
}