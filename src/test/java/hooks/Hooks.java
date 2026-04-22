package hooks;

import io.cucumber.java.Before;

import java.io.IOException;

import dto.AuthObj;
import utils.fileUtility.FileUtility;
import io.cucumber.java.After;
import io.restassured.RestAssured;

public class Hooks {

    public static AuthObj body;
    public static Boolean content_type_flag;

    @Before
    public void beforeScenario() throws IOException {
        System.out.println("=== Test Started ===");

        // Set Base URI globally
        // RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        FileUtility fLib = new FileUtility();
        RestAssured.baseURI = fLib.getDataFromPropertiesFile("baseurl");

        body = new AuthObj();
        content_type_flag = true;
    }

    @After
    public void afterScenario() {
        System.out.println("=== Test Finished ===");
    }
}