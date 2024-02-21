package steps;

import java.util.List;

import models.Pet;
import models.Status;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;
import static utility.TestUtility.*;

public class PetStepDefinitions implements StepBase {

    @Given("^a new pet with id (.*), name (.*) and status (.*)$")
    public void createNewPet(int id, String name, String status) {
        Pet newPet = new Pet();
        newPet.setId(id);
        newPet.setName(name);
        switch (status) {
            case "available":
                newPet.setStatus(Status.AVAILABLE);
                break;
            case "pending":
                newPet.setStatus(Status.PENDING);
                break;
            case "sold":
                newPet.setStatus(Status.SOLD);
        }
        scenarioState.setData("newPet", newPet, true);
    }

    @When("^adding the new pet to the store$")
    public void addNewPetToStore() {

        Pet pet = scenarioState.getData("newPet");

        var response = RestAssured.given()
                .urlEncodingEnabled(false)
                .contentType(ContentType.JSON)
                .body(petBuilder(pet).toJSONString())
                .when()
                .log().all()
                .post("/pet")
                .then()
                .log().all();

        scenarioState.setData("addPetResponse", response, true);
    }

    @Then("^the pet is added$")
    public void thePetIsAdded() {
        Pet newPet = scenarioState.getData("newPet");
        ValidatableResponse response = scenarioState.getData("addPetResponse");

        response.statusCode(200);
        response.contentType(ContentType.JSON);

        Assert.assertEquals(String.valueOf(newPet.getId()), extract("id", response).toString());
        Assert.assertEquals(newPet.getName(), extract("name", response).toString());
        Assert.assertEquals(newPet.getStatus().getValue(), extract("status", response).toString());
    }


    @When("^looking for a pet with status (.*)$")
    public void looking_for_a_pet_with_status(String status) {
        scenarioState.setData("searchStatus", status, true);
        var response = RestAssured.given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/pet/findByStatus?status=" + status)
                .then()
                .log().all();
        scenarioState.setData("findPetByStatusResponse", response, true);
    }

    @Then("^the pets list are present$")
    public void the_pets_list_are_present() {
        String status = scenarioState.getData("searchStatus");
        ValidatableResponse response = scenarioState.getData("findPetByStatusResponse");

        List<String> statusList = extract("status", response);
        boolean allEqual = statusList.stream().distinct().count() <= 1;

        response.statusCode(200);
        response.contentType(ContentType.JSON);

        if (statusList.size() > 0) {
            Assert.assertEquals(status, statusList.get(0));
            Assert.assertEquals(allEqual, true);
        }
        //Assert.assertEquals(status,extract("status",response).toString());
    }

    @When("^looking for a pet with id (.*)$")
    public void findPet(int id) {
        scenarioState.setData("petId", id, true);
        var response = RestAssured.given()
                .urlEncodingEnabled(false)
                .when()
                .log().all()
                .get("/pet/" + id)
                .then()
                .log().all();
        scenarioState.setData("findPetByIdResponse", response, true);
    }

    @Then("^the pet's information is present$")
    public void findPetResult() {
        int id = scenarioState.getData("petId");
        ValidatableResponse response = scenarioState.getData("findPetByIdResponse");

        response.statusCode(200);
        response.contentType(ContentType.JSON);
        response.body("id", equalTo(scenarioState.getData("petId")));

        Assert.assertEquals(Integer.toString(id), extract("id", response).toString());
    }

    @Given("^an existing pet with id (.*) wants to have name to (.*) and new status to (.*)$")
    public void anExistingPetWithIdIdWantsToHaveNameToNew_nameAndNewStatusToNew_status(int id, String newName, String newStatus) {
        Pet toBeUpdatePet = new Pet();
        toBeUpdatePet.setId(id);
        toBeUpdatePet.setName(newName);
        switch (newStatus)
        {
            case "available":
                toBeUpdatePet.setStatus(Status.AVAILABLE);
                break;
            case "pending":
                toBeUpdatePet.setStatus(Status.PENDING);
                break;
            case "sold":
                toBeUpdatePet.setStatus(Status.SOLD);
        }
        scenarioState.setData("pet", toBeUpdatePet, true);
    }

    @When("updating the pet to store")
    public void updating_the_pet_to_store() {

        Pet pet = scenarioState.getData("pet");

        var response = RestAssured.given()
                .urlEncodingEnabled(false)
                .contentType(ContentType.JSON)
                .body(petBuilder(pet).toJSONString())
                .when()
                .log().all()
                .put("/pet")
                .then()
                .log().all();

        scenarioState.setData("updatePetResponse", response, true);
    }

    @Then("the pet's information is updated")
    public void petsInformationUpdated() {
        Pet updatedPet = scenarioState.getData("pet");
        ValidatableResponse response = scenarioState.getData("updatePetResponse");

        response.statusCode(200);
        response.contentType(ContentType.JSON);
        response.body("id", equalTo(updatedPet.getId()));
        response.body("name", equalTo(updatedPet.getName()));
        response.body("status", equalTo(updatedPet.getStatus().getValue()));
    }
}
