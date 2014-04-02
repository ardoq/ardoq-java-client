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
