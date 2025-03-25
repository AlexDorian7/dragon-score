package team.logica_populi.dragonscore.base.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import team.logica_populi.dragonscore.base.logic.generators.QuestionGenerator;

import java.lang.reflect.Type;

/**
 * Used to Serialize an {@link QuestionGenerator} object
 */
public class QuestionGeneratorSerializer implements JsonSerializer<QuestionGenerator> {
    @Override
    public JsonElement serialize(QuestionGenerator questionGenerator, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(questionGenerator.getId());
    }
}
