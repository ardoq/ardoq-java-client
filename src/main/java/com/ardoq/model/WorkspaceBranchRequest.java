package com.ardoq.model;

import com.google.gson.annotations.SerializedName;

public class WorkspaceBranchRequest {
    @SerializedName("branch-name")
    private final String branchName;

    public WorkspaceBranchRequest(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "WorkspaceBranchRequest{" +
                "branchName='" + branchName + '\'' +
                '}';
    }
}
