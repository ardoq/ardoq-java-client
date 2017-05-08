package com.ardoq.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Field implements BasicModel {


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
    private String defaultValue;
    private FieldType type;
    private String label;
    private List<String> referenceType;
    private List<String> componentType;
    private Boolean global;
    private Boolean globalref;
    private String description;

    public Field(String name, String label, String modelId, FieldType type) {
        this(name, label, modelId, new LinkedList<String>(), new LinkedList<String>(), type, "");
        this.setGlobal(true);
        this.setGlobalref(true);
    }

    public Field(String name, String label, String modelId, List<String> componentType, FieldType type) {
        this(name, label, modelId, componentType, new LinkedList<String>(), type, "");
    }

    public Field(String name, String label, String modelId, List<String> componentType, List<String> referenceType, FieldType type,String defaultValue) {
        this.name = name;
        this.label = label;
        this.model = modelId;
        this.componentType = componentType;
        this.referenceType = referenceType;
        this.type = type;
        this.defaultValue = defaultValue;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<String> getComponentType() {
        return componentType;
    }

    public void setComponentType(List<String> componentType) {
        this.componentType = componentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(List<String> referenceType) {
        this.referenceType = referenceType;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Boolean getGlobalref() {
        return globalref;
    }

    public void setGlobalref(Boolean globalref) {
        this.globalref = globalref;
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
                ", referenceType='" + referenceType + '\'' +
                ", global='" + global + '\'' +
                ", globalRef='" + globalref + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
