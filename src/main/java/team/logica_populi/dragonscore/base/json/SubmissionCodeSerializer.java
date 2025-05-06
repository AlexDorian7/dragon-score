package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.points.SubmissionSystem;

import java.lang.reflect.Type;

/**
 * Used to Serialize a {@link SubmissionSystem}
 */
public class SubmissionCodeSerializer implements JsonSerializer<SubmissionSystem> {
    @Override
    public JsonElement serialize(SubmissionSystem submissionSystem, Type type, JsonSerializationContext jsonSerializationContext){
        JsonObject object = new JsonObject();
        object.add(submissionSystem.getSubmissions().toString(), new JsonPrimitive(String.valueOf(submissionSystem.getSubmissions())));

        return object;
    }
}
