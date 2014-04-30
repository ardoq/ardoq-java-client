package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.Date;

/**
 * AggregatedWorkspace represents the aggregated data related to a Workspace, except Attachments.
 * Use the getAggregatedWorkspace in WorkspaceService to retrieve it.
 *
 * @see com.ardoq.service.WorkspaceService
 * @see com.ardoq.model.Component
 * @see com.ardoq.model.Reference
 * @see com.ardoq.model.Tag
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AggregatedWorkspace that = (AggregatedWorkspace) o;

        if (_version != null ? !_version.equals(that._version) : that._version != null) return false;
        if (componentModel != null ? !componentModel.equals(that.componentModel) : that.componentModel != null)
            return false;
        if (components != null ? !components.equals(that.components) : that.components != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(that.lastUpdated) : that.lastUpdated != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (references != null ? !references.equals(that.references) : that.references != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

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
        return result;
    }

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

    /**
     * Returns all components (pages) in the workspace.
     * @return A list of all components
     */
    public Collection<Component> getComponents() {
        return components;
    }

    /**
     * Returns all references between components (pages) in the workspace.
     * @return a list of all references
     */
    public Collection<Reference> getReferences() {
        return references;
    }

    /**
     * Returns all tags belonging to this workspace.
     * @return a list of all tags
     */
    public Collection<Tag> getTags() {
        return tags;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "AggregatedWorkspace{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", componentModel='" + componentModel + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", _version=" + _version +
                ", components=" + components +
                ", references=" + references +
                ", tags=" + tags +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
