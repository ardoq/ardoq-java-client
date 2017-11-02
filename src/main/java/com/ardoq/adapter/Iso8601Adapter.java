package com.ardoq.adapter;

import com.google.gson.*;

import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.DateTime;
import java.lang.reflect.Type;
import java.util.Date;

public class Iso8601Adapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DateTime dt = ISODateTimeFormat.dateTimeParser().parseDateTime(jsonElement.getAsString());
        return dt.toDate();
    }

    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        String s = ISODateTimeFormat.dateTime().print(new DateTime(date));
        return new JsonPrimitive(s);
    }
}
