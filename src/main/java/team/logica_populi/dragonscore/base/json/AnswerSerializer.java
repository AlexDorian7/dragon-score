package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.logic.Answer;

import java.lang.reflect.Type;

/**
 * Used to Serialize an {@link Answer} object
 */
public class AnswerSerializer implements JsonSerializer<Answer> {
    @Override
    public JsonElement serialize(Answer answer, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("answer", new JsonPrimitive(answer.getText()));
        object.add("correct", new JsonPrimitive(answer.isCorrect()));
        return object;
    }
}
