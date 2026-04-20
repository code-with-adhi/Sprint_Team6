package getBookingRunner;

//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	    features = "src/test/resources/GetBookingfeature/GetFeatureFile.feature",
	    glue = "GetBookingByID",
	    plugin = {"pretty", "html:target/GetBooking_POST_Report.html"},
	    monochrome = true
	)

public class GetBookingRunner {

}

/*
public class GetBookingRunner extends AbstractTestNGCucumberTests {

}
*/