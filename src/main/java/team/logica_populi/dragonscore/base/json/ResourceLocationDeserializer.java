package team.logica_populi.dragonscore.base.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import team.logica_populi.dragonscore.base.ResourceLocation;

import java.lang.reflect.Type;

/**
 * Used to Deserialize an {@link ResourceLocation} object
 */
public class ResourceLocationDeserializer implements JsonDeserializer<ResourceLocation> {
    @Override
    public ResourceLocation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new ResourceLocation(jsonElement.getAsString());
    }
}
