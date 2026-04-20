package createBookingRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/CreateBookingFeature/CreateFeatureFile.feature",
	    glue = {"CreateBooking","Hooks"},
	    plugin = {"pretty", "html:target/CreateBooking_POST_Report.html"},
	    monochrome = true
	)
public class CreateBookingTest extends AbstractTestNGCucumberTests {

}


