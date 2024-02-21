package steps;

import java.util.Objects;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;

public interface StepBase extends En {
    default <T> T extract(String jsonKey, ValidatableResponse response) {
        T t = response.extract().jsonPath().get(jsonKey);
        Objects.requireNonNull(t, jsonKey + "key not found in " + response.extract().body().asString());
        return t;
    }

}
