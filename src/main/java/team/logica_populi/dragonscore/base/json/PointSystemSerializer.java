package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.PointSystem;

import java.lang.reflect.Type;

/**
 * Used to Serialize an {@link PointSystem} object
 */
public class PointSystemSerializer implements JsonSerializer<PointSystem>{
    @Override
    public JsonElement serialize(PointSystem pointSystem, Type type, JsonSerializationContext jsonSerializationContext){
        JsonObject object = new JsonObject();
        object.add(pointSystem.getLessonRecords().toString(), new JsonPrimitive(String.valueOf(pointSystem.getLessonRecords())));

        return object;
    }
}
