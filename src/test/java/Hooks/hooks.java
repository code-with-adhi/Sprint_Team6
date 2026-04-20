package Hooks;

import io.cucumber.java.Before;



import java.io.IOException;

import file_utility.FileUtility;
import io.cucumber.java.After;
import io.restassured.RestAssured;

public class hooks {

    @Before
    public void beforeScenario() throws IOException {
        System.out.println("=== Test Started ===");

        // Set Base URI globally
       // RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        FileUtility fLib = new FileUtility(); 
	    RestAssured.baseURI = fLib.getDataFromPropertiesFile("baseurl");
    }

    @After
    public void afterScenario() {
        System.out.println("=== Test Finished ===");
    }
}