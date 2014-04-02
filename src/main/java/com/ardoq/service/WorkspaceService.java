package com.ardoq.service;

import com.ardoq.model.Workspace;
import com.ardoq.model.WorkspaceBranch;
import com.ardoq.model.WorkspaceBranchRequest;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

public interface WorkspaceService {

    @GET("/api/workspace")
    List<Workspace> getAllWorkspaces();

    @GET("/api/workspace")
    void getAllWorkspaces(Callback<List<Workspace>> callback);

    @GET("/api/workspace/{id}")
    Workspace getWorkspaceById(@Path("id") String id);

    @GET("/api/workspace/{id}")
    void getWorkspaceById(@Path("id") String id, Callback<Workspace> callback);

    @GET("/api/workspace/{id}/branch")
    List<WorkspaceBranch> getBranches(@Path("id") String id);

    @GET("/api/workspace/{id}/branch")
    void getBranches(@Path("id") String id, Callback<List<WorkspaceBranch>> callback);

    @POST("/api/workspace")
    Workspace createWorkspace(@Body Workspace workspace);

    @POST("/api/workspace")
    void createWorkspace(@Body Workspace workspace, Callback<Workspace> callback);

    @POST("/api/workspace/{id}/branch/create")
    Workspace branchWorkspace(@Path("id") String id, @Body WorkspaceBranchRequest branch);

    @POST("/api/workspace/{id}/branch/create")
    void branchWorkspace(@Path("id") String id, @Body WorkspaceBranchRequest branch, Callback<Workspace> callback);

    @PUT("/api/workspace/{id}")
    Workspace updateWorkspace(@Path("id") String id, @Body Workspace workspace);

    @PUT("/api/workspace/{id}")
    void updateWorkspace(@Path("id") String id, @Body Workspace workspace, Callback<Workspace> callback);

    @DELETE("/api/workspace/{id}")
    Response deleteWorkspace(@Path("id") String id);

    @DELETE("/api/workspace/{id}")
    void deleteWorkspace(@Path("id") String id, Callback<Response> callback);
}
