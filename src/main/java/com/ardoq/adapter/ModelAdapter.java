package com.ardoq.adapter;

import com.ardoq.model.Model;
import com.ardoq.model.Workspace;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class ModelAdapter implements JsonDeserializer<Model>, JsonSerializer<Model> {

    public Model deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Map<String, Integer> referenceTypes = getReferenceTypes(jsonObject);
        JsonObject root = jsonObject.getAsJsonObject("root");
        Map<String, Object> document = (Map<String, Object>) new Gson().fromJson(root, Object.class);
        Map<String, Collection<String>> componentTypes = getComponentTypeNames(document);

        return new Model(
                jsonObject.get("_id").getAsString(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("description") != null ? jsonObject.get("description").getAsString() : "",
                jsonObject.get("useAsTemplate") != null ? jsonObject.get("useAsTemplate").getAsBoolean() : false,
                componentTypes,
                referenceTypes);
    }

    private Map<String, Collection<String>> getComponentTypeNames(Map<String, Object> document) {
        return getComponentTypeNamesRecursive(document, new HashMap<String, Collection<String>>());
    }

    private Map<String, Collection<String>> getComponentTypeNamesRecursive
            (Map<String, Object> document, Map<String,Collection<String>> extracted) {
        HashMap<String, Collection<String>> componentTypes = new HashMap<String, Collection<String>>();
        String component_name;
        String component_id;
        Collection<String> xs;
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            Map<String, Object> value = (Map<String, Object>) entry.getValue();
            component_name = (String)(value.get("name"));
            component_id   = entry.getKey();
            if (extracted.containsKey(component_name)) {
                extracted.get(component_name).add(component_id);
            } else {
                xs = new ArrayList<String>();
                xs.add(component_id);
                extracted.put(component_name, xs);
            }
            extracted = getComponentTypeNamesRecursive((Map<String, Object>) value.get("children"), extracted);
        }
        return extracted;
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

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .create();
    }

    public JsonElement serialize(Model reference, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonElement = gson().toJsonTree(reference, Model.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonUtils.removeReservedNullVaules(jsonObject);
        return jsonElement;
    }

}
