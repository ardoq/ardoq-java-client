package com.ardoq.service;

import java.util.List;

import com.ardoq.model.AggregatedWorkspace;
import com.ardoq.model.Workspace;
import com.ardoq.model.WorkspaceBranch;
import com.ardoq.model.WorkspaceBranchRequest;

import retrofit.Callback;
import retrofit.client.Response;

public class SimpleWorkspaceService implements WorkspaceService{

    private final WorkspaceService workspaceService;

    public SimpleWorkspaceService(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaceService.getAllWorkspaces();
    }

    public void getAllWorkspaces(Callback<List<Workspace>> callback) {
        workspaceService.getAllWorkspaces(callback);
    }

    public Workspace getWorkspaceById(String id) {
        return workspaceService.getWorkspaceById(id);
    }

    public void getWorkspaceById(String id, Callback<Workspace> callback) {
        workspaceService.getWorkspaceById(id, callback);
    }

    public List<WorkspaceBranch> getBranches(String id) {
        return workspaceService.getBranches(id);
    }

    public void getBranches(String id, Callback<List<WorkspaceBranch>> callback) {
        workspaceService.getBranches(id, callback);
    }

    public AggregatedWorkspace getAggregatedWorkspace(String id) {
        return workspaceService.getAggregatedWorkspace(id);
    }

    public void getAggregatedWorkspace(String id, Callback<AggregatedWorkspace> callback) {
        workspaceService.getAggregatedWorkspace(id, callback);
    }

    public Workspace createWorkspace(Workspace workspace) {
        return workspaceService.createWorkspace(workspace);
    }

    public void createWorkspace(Workspace workspace, Callback<Workspace> callback) {
        workspaceService.createWorkspace(workspace, callback);
    }

    public Workspace branchWorkspace(String id, WorkspaceBranchRequest branch) {
        return workspaceService.branchWorkspace(id, branch);
    }

    public void branchWorkspace(String id, WorkspaceBranchRequest branch, Callback<Workspace> callback) {
        workspaceService.branchWorkspace(id, branch, callback);
    }

    public Workspace updateWorkspace(String id, Workspace workspace) {
        return workspaceService.updateWorkspace(id, workspace);
    }

    public void updateWorkspace(String id, Workspace workspace, Callback<Workspace> callback) {
        workspaceService.updateWorkspace(id, workspace, callback);
    }

    public Response deleteWorkspace(String id) {
        return workspaceService.deleteWorkspace(id);
    }

    public void deleteWorkspace(String id, Callback<Response> callback) {
        workspaceService.deleteWorkspace(id, callback);
    }

    public List<Workspace> findWorkspacesByName(String workspaceName) {
        return workspaceService.findWorkspacesByName(workspaceName);
    }

    public Workspace createWorkspaceWithModel(String workspaceName, String modelID, String description) {
        return workspaceService.createWorkspace(new Workspace(workspaceName,description).withComponentModel(modelID));
    }

    public Workspace createWorkspaceFromTemplate(String workspaceName, String templateID, String decription) {
        return workspaceService.createWorkspace(new Workspace(workspaceName,templateID,decription));
    }
}
