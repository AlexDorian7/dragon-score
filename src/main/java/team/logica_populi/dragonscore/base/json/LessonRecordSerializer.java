package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.LessonRecord;

import java.lang.reflect.Type;

public class LessonRecordSerializer implements JsonSerializer<LessonRecord> {
    @Override
    public  JsonElement serialize(LessonRecord lessonRecord, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("id", new JsonPrimitive(lessonRecord.getId()));
        object.add("username", new JsonPrimitive(lessonRecord.getUserName()));
        object.add("total_points", new JsonPrimitive(lessonRecord.getTotalPoints()));

        return object;
    }

}
