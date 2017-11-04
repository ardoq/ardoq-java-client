package com.ardoq.adapter;

import com.google.gson.*;

import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class Iso8601AdapterTest {

    Iso8601Adapter sut = new Iso8601Adapter();

    @Test
    public void testSerde() {
        Date orig = new Date();
        Date d = sut.deserialize(sut.serialize(orig, null, null), null, null);
        assertEquals(orig, d);
    }

    @Test
    public void testSerialization() {
        Gson gson = new Gson();
        JsonElement orig = gson.fromJson("\"2014-04-07T09:12:06.323+02:00\"", JsonElement.class);
        JsonElement e = sut.serialize(sut.deserialize(orig, null, null), null, null);
        assertEquals(orig.getAsString(), e.getAsString());
    }
}
