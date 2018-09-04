package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Represents an Ardoq component or also called a page.</p>
 * <p>Use the ComponentService to update.</p>
 * <p>NB! Every updates or modifications done via ComponentService results in a new Immutable instance.</p>
 *
 * @see com.ardoq.service.ComponentService
 */
public class Component implements BasicModel {
    @SerializedName("_id")
    private String id;
    private String batchId;
    private String name;
    private String model;
    private Date created;
    @SerializedName("created-by")
    private String createdBy;
    @SerializedName("last-updated")
    private Date lastUpdated;
    private String version;
    private Integer _version;
    private String rootWorkspace;
    private Collection<String> children;
    private String parent;
    private String type;
    private String typeId;
    private String description;
    private Map<String, Object> _fields = new HashMap<String, Object>();

    public Component(String name, String rootWorkspace, String description) {
        this.name = name;
        this.rootWorkspace = rootWorkspace;
        this.description = description;
        this.parent = null;
    }

    public Component(String name, String rootWorkspace, String description, String typeId) {
        this.name = name;
        this.rootWorkspace = rootWorkspace;
        this.description = description;
        this.parent = null;
        this.typeId = typeId;
    }

    public Component(String name, String rootWorkspace, String description, String typeId, String parent) {
        this(name, rootWorkspace, description);
        this.setParent(parent);
        this.typeId = typeId;
    }

    public Component(String name, String description, String typeId, Component parent) {
        this(name, parent.getRootWorkspace(), description);
        this.setParent(parent.getContextualId());
        this.typeId = typeId;
    }


    @Override
    public Object clone() {
        Component c = new Component(name, rootWorkspace, description, typeId, parent);
        c.setModel(new String(model));
        c.setId(new String(id));
        c.setBatchId(new String(batchId));
        c.setCreated(new Date(created.getTime()));
        c.setCreatedBy(new String(createdBy));
        c.setLastUpdated(new Date(lastUpdated.getTime()));
        c.setRootWorkspace(new String(rootWorkspace));
        c.setChildren(children);
        c.setType(new String(type));
        c.setFields(new HashMap<String, Object>(_fields));
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        if (_fields != null ? !_fields.equals(component._fields) : component._fields != null) return false;
        if (_version != null ? !_version.equals(component._version) : component._version != null) return false;
        if (children != null ? !children.equals(component.children) : component.children != null) return false;
        if (created != null ? !created.equals(component.created) : component.created != null) return false;
        if (createdBy != null ? !createdBy.equals(component.createdBy) : component.createdBy != null) return false;
        if (description != null ? !description.equals(component.description) : component.description != null)
            return false;
        if (id != null ? !id.equals(component.id) : component.id != null) return false;
        if (batchId != null ? !batchId.equals(component.batchId) : component.batchId != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(component.lastUpdated) : component.lastUpdated != null)
            return false;
        if (model != null ? !model.equals(component.model) : component.model != null) return false;
        if (name != null ? !name.equals(component.name) : component.name != null) return false;
        if (parent != null ? !parent.equals(component.parent) : component.parent != null) return false;
        if (rootWorkspace != null ? !rootWorkspace.equals(component.rootWorkspace) : component.rootWorkspace != null)
            return false;
        if (type != null ? !type.equals(component.type) : component.type != null) return false;
        if (typeId != null ? !typeId.equals(component.typeId) : component.typeId != null) return false;
        if (version != null ? !version.equals(component.version) : component.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (batchId != null ? batchId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (_version != null ? _version.hashCode() : 0);
        result = 31 * result + (rootWorkspace != null ? rootWorkspace.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (_fields != null ? _fields.hashCode() : 0);
        return result;
    }

    // package private access
    public String getContextualId() {
        if (id != null) {
            return id;
        }
        return batchId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBatchId(String id) {
        this.batchId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Collection<String> getChildren() {
        return children;
    }

    public void setChildren(Collection<String> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

    public Map<String, Object> getFields() {
        return _fields;
    }

    public void setFields(Map<String, Object> fields) {
        this._fields = fields;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id='" + id + '\'' +
                "'batchId='" + batchId + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", version='" + version + '\'' +
                ", _version=" + _version +
                ", rootWorkspace='" + rootWorkspace + '\'' +
                ", children=" + children +
                ", parent='" + parent + '\'' +
                ", type='" + type + '\'' +
                ", typeId='" + typeId + '\'' +
                ", description='" + description + '\'' +
                ", _fields=" + _fields +
                '}';
    }

}

