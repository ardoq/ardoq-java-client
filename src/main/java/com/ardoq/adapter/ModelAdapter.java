package com.ardoq.adapter;

import com.ardoq.model.Model;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModelAdapter implements JsonDeserializer<Model> {
    @Override
    public Model deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Map<String, Integer> referenceTypes = getReferenceTypes(jsonObject);
        Map<String, String> componentTypes = getComponentTypes(jsonObject.getAsJsonObject("root"));

        return new Model(
                jsonObject.get("_id").getAsString(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString(),
                componentTypes,
                referenceTypes);
    }

    private Map<String, String> getComponentTypes(JsonObject jsonObject) {
        HashMap<String, String> componentTypes = new HashMap<String, String>();
        return componentTypes;
    }

    private Map<String, Integer> getReferenceTypes(JsonObject jsonObject) {
        Map<String, Integer> references = new HashMap<String, Integer>();
        JsonObject referenceTypes1 = jsonObject.getAsJsonObject("referenceTypes");
        if (referenceTypes1 != null) {
            Set<Map.Entry<String, JsonElement>> entries = referenceTypes1.entrySet();

            for (Map.Entry<String, JsonElement> entry : entries) {
                JsonObject value = (JsonObject) entry.getValue();
                references.put(value.get("name").getAsString(), value.get("id").getAsInt());
            }
        }
        return references;
    }

}
