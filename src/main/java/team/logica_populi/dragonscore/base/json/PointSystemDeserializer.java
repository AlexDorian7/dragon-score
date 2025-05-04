package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.PointSystem;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to Deserialize an {@link PointSystem} object
 */
public class PointSystemDeserializer implements JsonDeserializer<PointSystem> {
    @Override
    public PointSystem deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException{
        if(!jsonElement.isJsonObject()) throw new JsonParseException("Not a json object!");
        JsonObject object = (JsonObject) jsonElement;
        HashMap<String, HashMap<String, Integer>> system = new HashMap<>();

        for(Map.Entry<String, JsonElement> entry : object.entrySet()){
            String outkey = entry.getKey();
            JsonObject jsonObject = entry.getValue().getAsJsonObject();

            HashMap<String, Integer> map = new HashMap<>();

            for(Map.Entry<String, JsonElement> e : jsonObject.entrySet()){
                String id = e.getKey();
                Integer points = e.getValue().getAsInt();
                map.put(id, points);
            }
            system.put(outkey, map);
        }

        return new PointSystem(system);
    }
}
