package runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features/",
        glue = {"steps"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        tags = "")
//tags = "@add")
public class TestNgRunner extends AbstractTestNGCucumberTests {
}
