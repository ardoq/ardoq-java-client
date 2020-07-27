package com.ardoq.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

public class JsonUtils {

    /**
     * Removes keys from json object if they have null values. Won't touch fields as users may want to explicitly set them to null
     *
     * @param jsonObject json object to be cleaned
     */
    public static void removeReservedNullVaules(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            if (entry.getValue().isJsonNull()) {
                jsonObject.remove(entry.getKey());
            }
        }
    }
}