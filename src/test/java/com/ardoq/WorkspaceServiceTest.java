package com.ardoq;

import com.ardoq.model.Workspace;
import com.ardoq.model.WorkspaceBranch;
import com.ardoq.model.WorkspaceBranchRequest;
import com.ardoq.service.WorkspaceService;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

import static org.junit.Assert.*;

public class WorkspaceServiceTest {
    WorkspaceService service;

    @Before
    public void before() {
        service = new ArdoqClient("http://localhost:8080", "", "").workspace();
    }

    @Test
    public void getWorkspaceTest() {
        List<Workspace> allWorkspaces = service.getAllWorkspaces();
        String id = allWorkspaces.get(0).getId();
        service.getWorkspaceById(id);
        assertEquals(id, service.getWorkspaceById(id).getId());
    }

    @Test
    public void createWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        assertTrue(result.getId() != null);
    }

    @Test
    public void updateWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        result.setName("updatedName");
        Workspace updatedWorkspace = service.updateWorkspace(result.getId(), result);
        assertEquals("updatedName", updatedWorkspace.getName());
    }

    @Test
    public void deleteWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        Response response = service.deleteWorkspace(result.getId());
        assertEquals(204, response.getStatus());
        try {
            Workspace workspaceById = service.getWorkspaceById(result.getId());
            fail("Expected failure");
        } catch (RetrofitError e) {
            int status = e.getResponse().getStatus();
            assertEquals(404, status);
        }
    }

    @Test
    public void branchWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        Workspace myBranch = service.branchWorkspace(result.getId(), new WorkspaceBranchRequest("myBranch"));
        assertEquals("myBranch", myBranch.getName());
        List<WorkspaceBranch> branches = service.getBranches(result.getId());
        assertEquals(1, branches.size());
        assertEquals(myBranch.getName(), branches.get(0).getBranchName());
    }
}
