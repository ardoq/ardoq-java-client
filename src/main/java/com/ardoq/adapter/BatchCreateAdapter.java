package com.ardoq.adapter;
import com.ardoq.batch.BatchCreateRequest;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Date;

public class BatchCreateAdapter implements JsonSerializer<BatchCreateRequest> {

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .create();
    }

    public JsonElement serialize(BatchCreateRequest batch, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject data = new JsonObject();
        data.add("components", gson().toJsonTree(batch.getComponents()).getAsJsonArray());
        data.add("references", gson().toJsonTree(batch.getReferences()).getAsJsonArray());

        JsonObject payload = new JsonObject();
        payload.addProperty("op", batch.getOp());
        payload.add("options", gson().toJsonTree(batch.getOptions()));
        payload.add("data", data);

        return payload;
    }
}

