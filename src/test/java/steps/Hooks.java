package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;

public class Hooks {
    @Before
    public void BeforeScenario(Scenario scenario) {
        System.out.println("Before Scenario: Set RestAssured baseUri");
        System.out.println("Scenario name: " + scenario.getName());
        System.out.println("Scenario id: " + scenario.getId());
        System.out.println("Scenario status: " + scenario.getStatus());
        System.out.println("Scenario uri: " + scenario.getUri());
        System.out.println("Scenario source tags: " + scenario.getSourceTagNames());
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @After
    public void AfterScenario() {
        System.out.println("After scenario: Teardown");
    }
}
