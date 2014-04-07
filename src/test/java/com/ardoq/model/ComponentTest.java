package com.ardoq.model;

import com.ardoq.ComponentAdapter;
import com.ardoq.Iso8601Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComponentTest {

    @Test
    public void dynamicFieldsDeSerializationTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .registerTypeAdapter(Component.class, new ComponentAdapter())
                .create();

        Component component = gson.fromJson(getTestComponentAsJson(), Component.class);
        Map<String, Object> fields = component.getFields();

        for (String s : new String[]{"endpoint", "maintainer", "environment", "wsdl"}) {
            assertTrue(fields.containsKey(s));
        }
        assertEquals(fields.get("maintainer"), "erik@ardoq.com");
    }

    @Test
    public void dynamicFieldsSerializationTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .registerTypeAdapter(Component.class, new ComponentAdapter())
                .create();

        Component component = new Component("name", "rootWorkspace", "description");
        component.setFields(new HashMap<String, Object>() {{
            put("maintainer", "erik@ardoq.com");
            put("endpoint", "http://www.vg.no");
        }});

        String s = gson.toJson(component);
        Map<String, Object> o = (Map<String, Object>) gson.fromJson(s, Object.class);
        assertEquals(o.get("maintainer"), "erik@ardoq.com");
        assertEquals(o.get("endpoint"), "http://www.vg.no");

    }

    public String getTestComponentAsJson() {
        URL url = this.getClass().getClassLoader().getResource("component.json");
        try {
            return new Scanner(new File(url.toURI()), "UTF8").useDelimiter("\\Z").next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
