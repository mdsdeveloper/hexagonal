package cucumber.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-reports/report.json", "html:target/cucumber-reports/report.html"},
features = "classpath:cucumber/features")
public class RunCucumberTest {

}
