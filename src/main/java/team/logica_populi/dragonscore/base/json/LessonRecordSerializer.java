package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.logic.Answer;
import team.logica_populi.dragonscore.base.points.LessonRecord;

import java.lang.reflect.Type;

/**
 * Used to Serialize an {@link LessonRecord} object
 */
public class LessonRecordSerializer implements JsonSerializer<LessonRecord> {
    @Override
    public  JsonElement serialize(LessonRecord lessonRecord, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(lessonRecord.makeRecord());
    }

}
