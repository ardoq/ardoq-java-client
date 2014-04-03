package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Tag {
    @SerializedName("_id")
    private String id;
    private String name;
    private Date created;
    @SerializedName("created-by")
    private String createdBy;
    @SerializedName("last-updated")
    private Date lastUpdated;
    private Integer _version;
    private String rootWorkspace;
    private Collection<String> components;
    private Collection<String> references;
    private String description;

    public Tag(String name, String rootWorkspace, String description) {
        this.name = name;
        this.rootWorkspace = rootWorkspace;
        this.description = description;
    }

    public Tag(String name, String rootWorkspace, String description, List<String> components, List<String> references) {
        this(name, rootWorkspace, description);
        setComponents(components);
        setReferences(references);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer get_version() {
        return _version;
    }

    public void set_version(Integer _version) {
        this._version = _version;
    }

    public String getRootWorkspace() {
        return rootWorkspace;
    }

    public void setRootWorkspace(String rootWorkspace) {
        this.rootWorkspace = rootWorkspace;
    }

    public Collection<String> getComponents() {
        return components;
    }

    public void setComponents(Collection<String> components) {
        this.components = components;
    }

    public Collection<String> getReferences() {
        return references;
    }

    public void setReferences(Collection<String> references) {
        this.references = references;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
