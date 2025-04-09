package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.form.FormField;

import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Used to Deserialize an {@link Form} object
 */
public class FormDeserializer implements JsonDeserializer<Form> {

    private static final TypeToken<HashSet<FormField>> SET_TYPE = new TypeToken<>(){};

    @Override
    public Form deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!jsonElement.isJsonObject()) throw new JsonParseException("Not a json object.");
        JsonObject object = (JsonObject) jsonElement;
        HashSet<FormField> set = jsonDeserializationContext.deserialize(object.get("fields"), SET_TYPE.getType());
        return new Form(object.get("id").getAsString(), object.get("form").getAsString(), set);
    }
}
