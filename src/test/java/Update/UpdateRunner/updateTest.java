package Update.UpdateRunner;

// import org.junit.runner.RunWith;
// import io.cucumber.junit.Cucumber;
// import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/UpdateFeature/updateFeature.feature", glue = {"Update.UpdateStepDefinition","Hooks"}, plugin = {
        "pretty", "html:target/sprint-report.html" }, monochrome = true)
public class updateTest extends AbstractTestNGCucumberTests {
}