package UpdateRunner;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(
        features = "src/test/resources/UpdateFeature",
        glue = "UpdateStepDefinition",
        plugin = {"pretty", "html:target/sprint-report.html"},
        monochrome = true
)

public class updateRunner {
}