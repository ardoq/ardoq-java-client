package com.ardoq.model;

import java.util.Map;

public class Model {
    private final String id;
    private final String name;
    private final String description;
    private final Map<String, String> componentTypes;
    private final Map<String, Integer> referenceTypes;

    public Model(String id, String name, String description, Map<String, String> componentTypes, Map<String, Integer> referenceTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.componentTypes = componentTypes;
        this.referenceTypes = referenceTypes;
    }

    public Integer getReferenceTypeByName(String name) {
        return referenceTypes.get(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getComponentTypes() {
        return componentTypes;
    }

    public Map<String, Integer> getReferenceTypes() {
        return referenceTypes;
    }

    public String getComponentTypeByName(String name) {
        return this.componentTypes.get(name);
    }
}
