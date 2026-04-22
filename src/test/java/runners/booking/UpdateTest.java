package runners.booking;

// import org.junit.runner.RunWith;
// import io.cucumber.junit.Cucumber;
// import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/updateFeature.feature", glue = {
                "stepdefinitions.update_stepdef",
                "hooks" }, plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                }, monochrome = true)
public class UpdateTest extends AbstractTestNGCucumberTests {
}