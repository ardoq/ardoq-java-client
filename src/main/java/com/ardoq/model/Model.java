package com.ardoq.model;

import java.util.Collection;
import java.util.Map;

public class Model {
    private final String id;
    private final String name;
    private final String description;
    private final Boolean useAsTemplate;
    private final Map<String, Collection<String>> componentTypes;
    private final Map<String, Integer> referenceTypes;

    public Model(String id, String name, String description, Boolean useAsTemplate, Map<String, Collection<String>> componentTypes, Map<String, Integer> referenceTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.useAsTemplate = useAsTemplate;
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

    public Map<String, Collection<String>> getComponentTypes() {
        return componentTypes;
    }

    public Map<String, Integer> getReferenceTypes() {
        return referenceTypes;
    }

    public Collection<String> getComponentTypesByName(String name) {
        return this.componentTypes.get(name);
    }

    public String getComponentTypeByName(String name) throws IllegalStateException {
        Collection<String> xs = getComponentTypesByName(name);
        switch (xs.size()) {
            case 0 : return null;
            case 1 : return xs.iterator().next();
            default: throw new IllegalStateException("more than one component has name : " + name);
        }
    }

    public Boolean isTemplate() {
        return this.useAsTemplate;
    }
}
