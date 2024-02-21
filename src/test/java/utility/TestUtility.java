package utility;

import lombok.experimental.UtilityClass;
import models.Pet;
import models.Tag;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@UtilityClass
public class TestUtility {
    public static ScenarioState scenarioState = new ScenarioState();

    //public ScenarioState state;
    //static {state = new ScenarioState();}

    public static JSONObject petBuilder(Pet pet) {
        JSONObject petContext = new JSONObject();
        JSONObject categoryContext = new JSONObject();
        JSONArray tagsContext = new JSONArray();
        JSONArray photoUrlsContext = new JSONArray();

        categoryContext.put("id", pet.getCategory().getId());
        categoryContext.put("name", pet.getCategory().getName());

        for (Tag tag : pet.getTags()) {
            JSONObject tagContext = new JSONObject();
            tagContext.put("id", tag.getId());
            tagContext.put("name", tag.getName());
            tagsContext.appendElement(tagContext);
        }

        for (String photoUrl : pet.getPhotoUrls()) {
            photoUrlsContext.appendElement(photoUrl);
        }

        petContext.put("id", pet.getId());
        petContext.put("category", categoryContext);
        petContext.put("name", pet.getName());
        petContext.put("photoUrls", photoUrlsContext);
        petContext.put("tags", tagsContext);
        petContext.put("status", pet.getStatus().getValue());

        return petContext;
    }
}
