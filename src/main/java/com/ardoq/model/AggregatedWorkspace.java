package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Date;

public class AggregatedWorkspace {
    @SerializedName("_id")
    private String id;
    private String name;
    private String componentModel;
    private Date created;
    @SerializedName("created-by")
    private String createdBy;
    @SerializedName("last-updated")
    private Date lastUpdated;
    private Integer _version;
    private Collection<Component> components;
    private Collection<Reference> references;
    private Collection<Tag> tags;
    private String type;
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComponentModel() {
        return componentModel;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Integer get_version() {
        return _version;
    }

    public Collection<Component> getComponents() {
        return components;
    }

    public Collection<Reference> getReferences() {
        return references;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
