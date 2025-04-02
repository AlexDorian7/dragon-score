package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.logic.generators.QuestionGenerator;
import team.logica_populi.dragonscore.base.registries.QuestionGeneratorRegistry;

import java.lang.reflect.Type;

/**
 * Used to Deserialize an {@link QuestionGenerator} object
 */
public class QuestionGeneratorDeserializer implements JsonDeserializer<QuestionGenerator> {
    @Override
    public QuestionGenerator deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return QuestionGeneratorRegistry.getInstance().getQuestionGenerator(jsonElement.getAsString());
    }
}
