package com.ardoq.adapter;

import com.ardoq.model.Component;
import com.ardoq.model.Reference;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

public class ReferenceAdapter implements JsonDeserializer<Reference>, JsonSerializer<Reference> {

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .create();
    }

    public Reference deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = gson();
        Reference reference = gson.fromJson(jsonElement, Reference.class);
        Map<String, Object> fields = (Map<String, Object>) gson.fromJson(jsonElement, Object.class);
        for (Field field : Component.class.getDeclaredFields()) {
            fields.remove(field.getName());
        }
        reference.setFields(fields);
        return reference;
    }

    public JsonElement serialize(Reference reference, Type type, JsonSerializationContext jsonSerializationContext) {
        Map<String, Object> fields = reference.getFields();
        JsonElement jsonElement = gson().toJsonTree(reference, Component.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.remove("_fields");
        JsonUtils.removeReservedNullVaules(jsonObject);
        for (Map.Entry<String, Object> s : fields.entrySet()) {
            jsonObject.add(s.getKey(), jsonSerializationContext.serialize(s.getValue()));
        }
        return jsonElement;
    }
}
