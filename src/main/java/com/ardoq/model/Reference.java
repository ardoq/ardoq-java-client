package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Reference implements BasicModel {
    @SerializedName("_id")
    private String id;
    private Date created;
    @SerializedName("created-by")
    private String createdBy;
    @SerializedName("last-updated")
    private Date lastUpdated;
    private Integer _version;
    private String rootWorkspace;
    private Integer type;
    private String source;
    private String target;
    private String description;
    private String displayText;
    private String returnValue;
    private String targetWorkspace;
    private Integer order;


    public Reference(String rootWorkspace, String description, String source, String target, int type) {
        this.rootWorkspace = rootWorkspace;
        this.description = description;
        this.source = source;
        this.target = target;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (_version != null ? !_version.equals(reference._version) : reference._version != null) return false;
        if (created != null ? !created.equals(reference.created) : reference.created != null) return false;
        if (createdBy != null ? !createdBy.equals(reference.createdBy) : reference.createdBy != null) return false;
        if (description != null ? !description.equals(reference.description) : reference.description != null)
            return false;
        if (displayText != null ? !displayText.equals(reference.displayText) : reference.displayText != null)
            return false;
        if (id != null ? !id.equals(reference.id) : reference.id != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(reference.lastUpdated) : reference.lastUpdated != null)
            return false;
        if (order != null ? !order.equals(reference.order) : reference.order != null) return false;
        if (returnValue != null ? !returnValue.equals(reference.returnValue) : reference.returnValue != null)
            return false;
        if (rootWorkspace != null ? !rootWorkspace.equals(reference.rootWorkspace) : reference.rootWorkspace != null)
            return false;
        if (source != null ? !source.equals(reference.source) : reference.source != null) return false;
        if (target != null ? !target.equals(reference.target) : reference.target != null) return false;
        if (targetWorkspace != null ? !targetWorkspace.equals(reference.targetWorkspace) : reference.targetWorkspace != null)
            return false;
        if (type != null ? !type.equals(reference.type) : reference.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (_version != null ? _version.hashCode() : 0);
        result = 31 * result + (rootWorkspace != null ? rootWorkspace.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (displayText != null ? displayText.hashCode() : 0);
        result = 31 * result + (returnValue != null ? returnValue.hashCode() : 0);
        result = 31 * result + (targetWorkspace != null ? targetWorkspace.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTargetWorkspace(String targetWorkspace) {
        this.targetWorkspace = targetWorkspace;
    }

    public String getTargetWorkspace() {
        return targetWorkspace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", _version=" + _version +
                ", rootWorkspace='" + rootWorkspace + '\'' +
                ", type=" + type +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", description='" + description + '\'' +
                ", returnValue='" + returnValue + '\'' +
                ", targetWorkspace='" + targetWorkspace + '\'' +
                ", order=" + order +
                '}';
    }
}
