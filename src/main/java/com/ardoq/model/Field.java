package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Field {


    @SerializedName("_id")
    private String id;
    private String name;
    private Date created;
    @SerializedName("created-by")
    private String createdBy;
    @SerializedName("last-updated")
    private Date lastUpdated;
    private Integer _version;
    private String model;
    private FieldType type;
    private String label;
    private String componentType;
    private String description;

    public Field(String name, String label, String modelId, String componentType, FieldType type) {
        this.name = name;
        this.label = label;
        this.model = modelId;
        this.componentType = componentType;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (_version != null ? !_version.equals(field._version) : field._version != null) return false;
        if (componentType != null ? !componentType.equals(field.componentType) : field.componentType != null)
            return false;
        if (created != null ? !created.equals(field.created) : field.created != null) return false;
        if (createdBy != null ? !createdBy.equals(field.createdBy) : field.createdBy != null) return false;
        if (description != null ? !description.equals(field.description) : field.description != null) return false;
        if (id != null ? !id.equals(field.id) : field.id != null) return false;
        if (label != null ? !label.equals(field.label) : field.label != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(field.lastUpdated) : field.lastUpdated != null) return false;
        if (model != null ? !model.equals(field.model) : field.model != null) return false;
        if (name != null ? !name.equals(field.name) : field.name != null) return false;
        if (type != field.type) return false;

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
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (componentType != null ? componentType.hashCode() : 0);
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", _version=" + _version +
                ", model='" + model + '\'' +
                ", type=" + type +
                ", label='" + label + '\'' +
                ", componentType='" + componentType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
