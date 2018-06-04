package com.ardoq.service;

import com.ardoq.model.AggregatedWorkspace;
import com.ardoq.model.Component;
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

    @GET("/api/workspace/{id}/aggregated")
    AggregatedWorkspace getAggregatedWorkspace(@Path("id") String id);

    @GET("/api/workspace/{id}/aggregated")
    void getAggregatedWorkspace(@Path("id") String id, Callback<AggregatedWorkspace> callback);

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

    @GET("/api/workspace/by-name")
    List<Workspace> findWorkspacesByName(@Query("name") String workspaceName);

    Workspace createWorkspaceWithModel(String workspaceName, String modelID, String description);

    Workspace createWorkspaceFromTemplate(String workspaceName, String templateID, String decription);

    @GET("/api/workspace/{id}/component")
    List<Component> getAllComponents(@Path("id") String id);

    @GET("/api/workspace/{id}/component")
    void getAllComponents(@Path("id") String id, Callback<List<Component>> callback);
}
