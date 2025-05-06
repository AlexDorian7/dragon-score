package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.SubmissionSystem;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to Deserialize a {@link SubmissionSystem} object
 */
public class SubmissionCodeDeserializer implements JsonDeserializer<SubmissionSystem> {
    @Override
    public SubmissionSystem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException{
        if(!jsonElement.isJsonObject()) throw new JsonParseException("Not a json object!");
        JsonObject object = (JsonObject) jsonElement;
        HashMap<String, HashMap<String, String>> system = new HashMap<>();

        for(Map.Entry<String, JsonElement> entry : object.entrySet()){
            String outkey = entry.getKey();
            JsonObject jsonObject = entry.getValue().getAsJsonObject();

            HashMap<String, String> map = new HashMap<>();

            for(Map.Entry<String, JsonElement> e : jsonObject.entrySet()){
                String id = e.getKey();
                String code = e.getValue().getAsString();
                map.put(id, code);
            }
            system.put(outkey, map);
        }

        return new SubmissionSystem(system);
    }
}
