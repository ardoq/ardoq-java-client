package com.ardoq.model;

import com.ardoq.adapter.Iso8601Adapter;
import com.ardoq.adapter.ModelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ModelTest {

    @Test
    public void modelDeSerializationTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .registerTypeAdapter(Model.class, new ModelAdapter())
                .create();

        Model model = gson.fromJson(getTestComponentAsJson(), Model.class);
        assertEquals(5, model.getReferenceTypes().size());
        assertEquals(2, (Object) model.getReferenceTypeByName("Implicit"));
        assertFalse(model.getComponentTypes().isEmpty());
    }

    public String getTestComponentAsJson() {
        URL url = this.getClass().getClassLoader().getResource("model.json");
        try {
            return new Scanner(new File(url.toURI()), "UTF8").useDelimiter("\\Z").next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
