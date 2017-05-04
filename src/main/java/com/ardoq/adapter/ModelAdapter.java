package com.ardoq.adapter;

import com.ardoq.model.Model;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModelAdapter implements JsonDeserializer<Model> {

    public Model deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Map<String, Integer> referenceTypes = getReferenceTypes(jsonObject);
        JsonObject root = jsonObject.getAsJsonObject("root");
        Map<String, Object> document = (Map<String, Object>) new Gson().fromJson(root, Object.class);
        Map<String, String> componentTypes = getComponentTypes(document);

        return new Model(
                jsonObject.get("_id").getAsString(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString(),
                jsonObject.get("useAsTemplate").getAsBoolean(),
                componentTypes,
                referenceTypes);
    }

    private Map<String, String> getComponentTypes(Map<String, Object> document) {
        HashMap<String, String> componentTypes = new HashMap<String, String>();
        for (Map.Entry<String, Object> entries : document.entrySet()) {
            Map<String, Object> value = (Map<String, Object>) entries.getValue();
            componentTypes.put((String) value.get("name"), entries.getKey());
            componentTypes.putAll(getComponentTypes((Map<String, Object>) value.get("children")));
        }
        return componentTypes;
    }

    private Map<String, Integer> getReferenceTypes(JsonObject jsonObject) {
        Map<String, Integer> references = new HashMap<String, Integer>();
        JsonElement referenceTypes = jsonObject.get("referenceTypes");
        if (referenceTypes instanceof JsonObject) {
            JsonObject document = (JsonObject) referenceTypes;
            if (document != null) {
                Set<Map.Entry<String, JsonElement>> entries = document.entrySet();
                for (Map.Entry<String, JsonElement> entry : entries) {
                    JsonObject value = (JsonObject) entry.getValue();
                    references.put(value.get("name").getAsString(), value.get("id").getAsInt());
                }
            }
        }
        return references;
    }

}
