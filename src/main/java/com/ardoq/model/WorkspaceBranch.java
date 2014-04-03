package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WorkspaceBranch {
    @SerializedName("_id")
    private String id;
    private Date created;
    @SerializedName("created-by")
    private String createdBy;
    @SerializedName("workspace-id")
    private String workspaceId;
    @SerializedName("branch-name")
    private String branchName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkspaceBranch that = (WorkspaceBranch) o;

        if (branchName != null ? !branchName.equals(that.branchName) : that.branchName != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (workspaceId != null ? !workspaceId.equals(that.workspaceId) : that.workspaceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (workspaceId != null ? workspaceId.hashCode() : 0);
        result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public String getBranchName() {
        return branchName;
    }
}
