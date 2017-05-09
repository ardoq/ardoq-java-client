package com.ardoq.adapter;

import java.lang.reflect.Type;
import java.util.Date;

import com.ardoq.model.Workspace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class WorkspaceAdapter implements JsonSerializer<Workspace> {

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .create();
    }

    public JsonElement serialize(Workspace reference, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonElement = gson().toJsonTree(reference, Workspace.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonUtils.removeReservedNullVaules(jsonObject);
        return jsonElement;
    }
}
