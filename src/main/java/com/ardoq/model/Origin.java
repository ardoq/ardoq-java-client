package com.ardoq.model;

public class Origin {
    private String id;
    private Integer _version;

    public String getId() {
        return id;
    }

    public Integer get_version() {
        return _version;
    }

    @Override
    public String toString() {
        return "Origin{" +
                "id='" + id + '\'' +
                ", _version=" + _version +
                '}';
    }
}
