package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

public class WorkspaceBranchRequest {
    @SerializedName("branch-name")
    private String branchName;

    public WorkspaceBranchRequest(String branchName) {
        this.branchName = branchName;
    }
}
