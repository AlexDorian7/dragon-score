package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.LessonRecord;

import java.lang.reflect.Type;

public class LessonRecordDeserializer implements JsonDeserializer<LessonRecord> {

    @Override
    public LessonRecord deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException{
        if(!jsonElement.isJsonObject()) throw new JsonParseException("Not a json object!");
        JsonObject object = (JsonObject) jsonElement;
        return new LessonRecord(object.get("id").getAsString(), object.get("username").getAsString(), object.get("total_points").getAsInt());
    }
}
