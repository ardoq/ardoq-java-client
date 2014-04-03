package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.model.Workspace;
import com.ardoq.model.WorkspaceBranch;
import com.ardoq.model.WorkspaceBranchRequest;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class WorkspaceServiceTest {
    private WorkspaceService service;

    @Before
    public void before() {
        service = new ArdoqClient("http://localhost:8080", System.getenv("ardoqUsername"), System.getenv("ardoqPassword")).workspace();
    }

    @Test
    public void getWorkspaceTest() {
        List<Workspace> allWorkspaces = service.getAllWorkspaces();
        String id = allWorkspaces.get(0).getId();
        assertEquals(id, service.getWorkspaceById(id).getId());
    }

    @Test
    public void getAsyncWorkspaceTest() {
        CallbackTest cb = new CallbackTest();
        service.getAllWorkspaces(cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());

        cb = new CallbackTest();
        List<Workspace> allWorkspaces = service.getAllWorkspaces();
        String id = allWorkspaces.get(0).getId();
        service.getWorkspaceById(id, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void createWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        assertNotNull(result.getId());
    }

    @Test
    public void createWorkspaceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
    }

    @Test
    public void updateWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        result.setName("updatedName");
        Workspace updatedWorkspace = service.updateWorkspace(result.getId(), result);
        assertEquals("updatedName", updatedWorkspace.getName());
    }

    @Test
    public void updateWorkspaceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        result.setName("updatedName");
        service.updateWorkspace(result.getId(), result, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void deleteWorkspaceTest() {
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        Response response = service.deleteWorkspace(result.getId());
        assertEquals(204, response.getStatus());
        try {
            service.getWorkspaceById(result.getId());
            fail("Expected the workspace to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }

    @Test
    public void deleteWorkspaceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        service.deleteWorkspace(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(204, cb.getResponse().getStatus());
        try {
            service.getWorkspaceById(result.getId());
            fail("Expected the workspace to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
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

    @Test
    public void branchWorkspaceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        Workspace result = service.createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        service.branchWorkspace(result.getId(), new WorkspaceBranchRequest("myBranch"), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());

        cb = new CallbackTest();
        service.getBranches(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }
}
