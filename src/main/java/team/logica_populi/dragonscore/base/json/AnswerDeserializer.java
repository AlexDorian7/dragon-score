package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.logic.Answer;

import java.lang.reflect.Type;

/**
 * Used to Deserialize an {@link Answer} object
 */
public class AnswerDeserializer implements JsonDeserializer<Answer> {

    @Override
    public Answer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!jsonElement.isJsonObject()) throw new JsonParseException("Not a json object.");
        JsonObject object = (JsonObject) jsonElement;

        return new Answer(object.get("answer").getAsString(), object.get("correct").getAsBoolean());
    }
}
