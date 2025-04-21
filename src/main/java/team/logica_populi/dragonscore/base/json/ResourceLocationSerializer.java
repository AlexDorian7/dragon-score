package team.logica_populi.dragonscore.base.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import team.logica_populi.dragonscore.base.ResourceLocation;

import java.lang.reflect.Type;

/**
 * Used to Serialize an {@link ResourceLocation} object
 */
public class ResourceLocationSerializer implements JsonSerializer<ResourceLocation> {
    @Override
    public JsonElement serialize(ResourceLocation resourceLocation, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(resourceLocation.toString());
    }
}
