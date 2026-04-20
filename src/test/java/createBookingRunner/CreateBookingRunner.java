package createBookingRunner;

//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	    features = "src/test/resources/CreateBookingFeature/CreateFeatureFile.feature",
	    glue = "CreateBooking",
	    plugin = {"pretty", "html:target/CreateBooking_POST_Report.html"},
	    monochrome = true
	)

public class CreateBookingRunner {

}


/*
public class CreateBookingRunner extends AbstractTestNGCucumberTests {

}
*/