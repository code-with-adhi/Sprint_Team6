package hooks;

import io.cucumber.java.Before;

import java.io.IOException;

import utils.fileUtility.FileUtility;
import io.cucumber.java.After;
import io.restassured.RestAssured;

public class Hooks {

    @Before
    public void beforeScenario() throws IOException {
        System.out.println("=== Test Started ===");
        FileUtility fLib = new FileUtility();
        RestAssured.baseURI = fLib.getDataFromPropertiesFile("baseurl");
    }

    @After
    public void afterScenario() {
        System.out.println("=== Test Finished ===");
    }
}