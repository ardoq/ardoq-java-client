package com.ardoq.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import com.ardoq.model.Reference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ReferenceAdapter implements JsonDeserializer<Reference>, JsonSerializer<Reference> {

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .create();
    }

    public Reference deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.add("typeName", jsonObject.get("ardoq").getAsJsonObject().get("typeName"));
        jsonObject.add("sourceName", jsonObject.get("ardoq").getAsJsonObject().get("sourceName"));
        jsonObject.add("targetName", jsonObject.get("ardoq").getAsJsonObject().get("targetName"));

        Gson gson = gson();
        Reference reference = gson.fromJson(jsonObject, Reference.class);

        Map<String, Object> fields = (Map<String, Object>) gson.fromJson(jsonElement, Object.class);
        for (Field field : Reference.class.getDeclaredFields()) {
            fields.remove(field.getName());
        }
        reference.setFields(fields);

        return reference;
    }

    public JsonElement serialize(Reference reference, Type type, JsonSerializationContext jsonSerializationContext) {
        Map<String, Object> fields = reference.getFields();
        JsonElement jsonElement = gson().toJsonTree(reference, Reference.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.remove("_fields");
		JsonUtils.removeReservedNullVaules(jsonObject);
		if (fields != null) {
	        for (Map.Entry<String, Object> s : fields.entrySet()) {
	            jsonObject.add(s.getKey(), jsonSerializationContext.serialize(s.getValue()));
			}
		}
        return jsonElement;
    }
}
