package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.LessonRecord;

import java.lang.reflect.Type;

/**
 * Used to Deserialize an {@link LessonRecord} object
 */
public class LessonRecordDeserializer implements JsonDeserializer<LessonRecord> {

    @Override
    public LessonRecord deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException{
        LessonRecord record = LessonRecord.loadRecord(jsonElement.getAsString());
        if (record == null) throw new JsonParseException("Failed to load Record!");
        return record;
    }
}
