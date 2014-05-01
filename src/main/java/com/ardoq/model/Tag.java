package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Tag implements BasicModel {
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

    public void addReference(String refId) {
        if (null == this.references) {
            this.references = new LinkedList<String>();
        }
        if (!this.references.contains(refId)) {
            this.references.add(refId);
        }
    }

    public void addComponent(String compId) {
        if (null == this.components) {
            this.components = new LinkedList<String>();
        }
        if (!this.components.contains(compId)) {
            this.components.add(compId);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (_version != null ? !_version.equals(tag._version) : tag._version != null) return false;
        if (components != null ? !components.equals(tag.components) : tag.components != null) return false;
        if (created != null ? !created.equals(tag.created) : tag.created != null) return false;
        if (createdBy != null ? !createdBy.equals(tag.createdBy) : tag.createdBy != null) return false;
        if (description != null ? !description.equals(tag.description) : tag.description != null) return false;
        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(tag.lastUpdated) : tag.lastUpdated != null) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        if (references != null ? !references.equals(tag.references) : tag.references != null) return false;
        if (rootWorkspace != null ? !rootWorkspace.equals(tag.rootWorkspace) : tag.rootWorkspace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (_version != null ? _version.hashCode() : 0);
        result = 31 * result + (rootWorkspace != null ? rootWorkspace.hashCode() : 0);
        result = 31 * result + (components != null ? components.hashCode() : 0);
        result = 31 * result + (references != null ? references.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
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

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", _version=" + _version +
                ", rootWorkspace='" + rootWorkspace + '\'' +
                ", components=" + components +
                ", references=" + references +
                ", description='" + description + '\'' +
                '}';
    }
}
