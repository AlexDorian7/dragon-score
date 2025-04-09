package team.logica_populi.dragonscore.base.json;

import com.google.gson.*;
import team.logica_populi.dragonscore.base.form.Form;
import team.logica_populi.dragonscore.base.form.FormField;

import java.lang.reflect.Type;

/**
 * Used to Serialize an {@link team.logica_populi.dragonscore.base.form.Form} object
 */
public class FormSerializer implements JsonSerializer<Form> {
    @Override
    public JsonElement serialize(Form form, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("id", new JsonPrimitive(form.getId()));
        object.add("form", new JsonPrimitive(form.getForm()));
        JsonArray array = new JsonArray(form.getFields().size());
        form.getFields().forEach((String id, FormField field) -> {
            array.add(jsonSerializationContext.serialize(field));
        });
        object.add("fields", array);
        return object;
    }
}
