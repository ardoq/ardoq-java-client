package com.ardoq.adapter;

import com.ardoq.model.Component;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

public class ComponentAdapter implements JsonDeserializer<Component>, JsonSerializer<Component> {


    @Override
    public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = gson();
        Component component = gson.fromJson(jsonElement, Component.class);
        Map<String, Object> fields = (Map<String, Object>) gson.fromJson(jsonElement, Object.class);
        for (Field field : Component.class.getDeclaredFields()) {
            fields.remove(field.getName());
        }
        component.setFields(fields);
        return component;
    }

    @Override
    public JsonElement serialize(Component component, Type type, JsonSerializationContext jsonSerializationContext) {
        Map<String, Object> fields = component.getFields();
        JsonElement jsonElement = gson().toJsonTree(component, Component.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.remove("_fields");
        for (Map.Entry<String, Object> s : fields.entrySet()) {
            jsonObject.add(s.getKey(), jsonSerializationContext.serialize(s.getValue()));
        }
        return jsonElement;
    }

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .create();
    }
}
