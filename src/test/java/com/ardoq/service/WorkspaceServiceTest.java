package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.TestUtils;
import com.ardoq.model.AggregatedWorkspace;
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
    private String aggregatedWorkspaceId;
    private Workspace testWorkspace;
    private WorkspaceService service;
    private CallbackTest cb;

    @Before
    public void before() {
        aggregatedWorkspaceId = TestUtils.getTestPropery("aggregatedWorkspaceId");
        service = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword")).workspace();
        cb = new CallbackTest();
        testWorkspace = new Workspace("myWorkspace", TestUtils.getTestPropery("modelId"), "Hello world!");
    }

    @Test
    public void getWorkspaceTest() {
        List<Workspace> allWorkspaces = service.getAllWorkspaces();
        String id = allWorkspaces.get(0).getId();
        assertEquals(id, service.getWorkspaceById(id).getId());
    }

    @Test
    public void getAsyncWorkspaceTest() {
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
        Workspace result = service.createWorkspace(testWorkspace);
        assertNotNull(result.getId());
    }

    @Test
    public void createWorkspaceAsyncTest() {
        service.createWorkspace(testWorkspace, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
    }

    @Test
    public void updateWorkspaceTest() {
        Workspace result = service.createWorkspace(testWorkspace);
        result.setName("updatedName");
        Workspace updatedWorkspace = service.updateWorkspace(result.getId(), result);
        assertEquals("updatedName", updatedWorkspace.getName());
    }

    @Test
    public void updateWorkspaceAsyncTest() {
        Workspace result = service.createWorkspace(testWorkspace);
        result.setName("updatedName");
        service.updateWorkspace(result.getId(), result, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void deleteWorkspaceTest() {
        Workspace result = service.createWorkspace(testWorkspace);
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
        Workspace result = service.createWorkspace(testWorkspace);
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
        Workspace result = service.createWorkspace(testWorkspace);
        Workspace myBranch = service.branchWorkspace(result.getId(), new WorkspaceBranchRequest("myBranch"));
        assertEquals("myBranch", myBranch.getName());
        assertEquals(result.getId(), myBranch.getOrigin().getId());
        assertEquals(result.get_version(), myBranch.getOrigin().get_version());
        List<WorkspaceBranch> branches = service.getBranches(result.getId());
        assertEquals(1, branches.size());
        assertEquals(myBranch.getName(), branches.get(0).getBranchName());
    }

    @Test
    public void branchWorkspaceAsyncTest() {
        Workspace result = service.createWorkspace(testWorkspace);
        service.branchWorkspace(result.getId(), new WorkspaceBranchRequest("myBranch"), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());

        cb = new CallbackTest();
        service.getBranches(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void getAggregatedWorkspaceTest() {
        AggregatedWorkspace aggregatedWorkspace = service.getAggregatedWorkspace(aggregatedWorkspaceId);
        assertFalse(aggregatedWorkspace.getComponents().isEmpty());
        assertFalse(aggregatedWorkspace.getReferences().isEmpty());
        assertFalse(aggregatedWorkspace.getTags().isEmpty());
    }

    @Test
    public void getAggregatedWorkspaceAsyncTest() {
        service.getAggregatedWorkspace(aggregatedWorkspaceId, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }
}
