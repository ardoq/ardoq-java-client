package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Date;

public class Workspace {
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
    private Collection<String> components;
    private Collection<String> references;
    private Collection<String> tags;
    private String type;
    private String description;
    private Origin origin;

    public Workspace(String name, String componentModel, String description) {
        this.name = name;
        this.componentModel = componentModel;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workspace workspace = (Workspace) o;

        if (_version != null ? !_version.equals(workspace._version) : workspace._version != null) return false;
        if (componentModel != null ? !componentModel.equals(workspace.componentModel) : workspace.componentModel != null)
            return false;
        if (components != null ? !components.equals(workspace.components) : workspace.components != null) return false;
        if (created != null ? !created.equals(workspace.created) : workspace.created != null) return false;
        if (createdBy != null ? !createdBy.equals(workspace.createdBy) : workspace.createdBy != null) return false;
        if (description != null ? !description.equals(workspace.description) : workspace.description != null)
            return false;
        if (id != null ? !id.equals(workspace.id) : workspace.id != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(workspace.lastUpdated) : workspace.lastUpdated != null)
            return false;
        if (name != null ? !name.equals(workspace.name) : workspace.name != null) return false;
        if (origin != null ? !origin.equals(workspace.origin) : workspace.origin != null) return false;
        if (references != null ? !references.equals(workspace.references) : workspace.references != null) return false;
        if (tags != null ? !tags.equals(workspace.tags) : workspace.tags != null) return false;
        if (type != null ? !type.equals(workspace.type) : workspace.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (componentModel != null ? componentModel.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (_version != null ? _version.hashCode() : 0);
        result = 31 * result + (components != null ? components.hashCode() : 0);
        result = 31 * result + (references != null ? references.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
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

    public String getComponentModel() {
        return componentModel;
    }

    public void setComponentModel(String componentModel) {
        this.componentModel = componentModel;
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

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }
}
