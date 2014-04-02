package com.ardoq.service;

import com.ardoq.model.Workspace;
import com.ardoq.model.WorkspaceBranch;
import com.ardoq.model.WorkspaceBranchRequest;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

public interface WorkspaceService {

    @GET("/api/workspace")
    List<Workspace> getAllWorkspaces();

    @GET("/api/workspace/{id}")
    Workspace getWorkspaceById(@Path("id") String id);

    @GET("/api/workspace/{id}/branch")
    List<WorkspaceBranch> getBranches(@Path("id") String id);

    @POST("/api/workspace")
    Workspace createWorkspace(@Body Workspace workspace);

    @POST("/api/workspace/{id}/branch/create")
    Workspace branchWorkspace(@Path("id") String id, @Body WorkspaceBranchRequest branch);

    @PUT("/api/workspace/{id}")
    Workspace updateWorkspace(@Path("id") String id, @Body Workspace workspace);

    @DELETE("/api/workspace/{id}")
    Response deleteWorkspace(@Path("id") String id);
}
