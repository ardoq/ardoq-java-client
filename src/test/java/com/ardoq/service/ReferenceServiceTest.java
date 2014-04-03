package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.model.Component;
import com.ardoq.model.Reference;
import com.ardoq.model.Workspace;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ReferenceServiceTest {
    private ReferenceService service;
    private Workspace workspace;
    private Component source;
    private Component target;

    @Before
    public void before() {
        ArdoqClient client = new ArdoqClient("http://localhost:8080", System.getenv("ardoqUsername"), System.getenv("ardoqPassword"));
        service = client.reference();
        workspace = client.workspace().createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        source = client.component().createComponent(new Component("Source", workspace.getId(), ""));
        target = client.component().createComponent(new Component("Target", workspace.getId(), ""));
    }

    @Test
    public void getReferenceTest() {
        List<Reference> allWorkspaces = service.getAllReferences();
        String id = allWorkspaces.get(0).getId();
        assertEquals(id, service.getReferenceById(id).getId());
    }

    @Test
    public void getAsyncReferenceTest() {
        CallbackTest cb = new CallbackTest();
        service.getAllReferences(cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());

        cb = new CallbackTest();
        List<Reference> allWorkspaces = service.getAllReferences();
        String id = allWorkspaces.get(0).getId();
        service.getReferenceById(id, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void createReferenceTest() {
        Reference reference = service.createReference(new Reference(workspace.getId(), source.getId(), target.getId(), 2));
        assertNotNull(reference.getId());
    }


    @Test
    public void createReferenceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        service.createReference(new Reference(workspace.getId(), source.getId(), target.getId(), 2), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
    }


    @Test
    public void updateReferenceTest() {
        Reference reference = service.createReference(new Reference(workspace.getId(), source.getId(), target.getId(), 2));
        reference.setSource(reference.getTarget());
        reference.setTarget(reference.getSource());
        Reference updatedReference = service.updateReference(reference.getId(), reference);
        assertEquals(reference.getTarget(), updatedReference.getSource());
        assertEquals(reference.get_version(), (Integer) (updatedReference.get_version() - 1));
    }

    @Test
    public void updateReferenceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        Reference reference = service.createReference(new Reference(workspace.getId(), source.getId(), target.getId(), 2));
        reference.setSource(reference.getTarget());
        service.updateReference(reference.getId(), reference, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void deleteReferenceTest() {
        Reference result = service.createReference(new Reference(workspace.getId(), source.getId(), target.getId(), 2));
        Response response = service.deleteReference(result.getId());
        assertEquals(204, response.getStatus());
        try {
            service.getReferenceById(result.getId());
            fail("Expected the reference to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }

    @Test
    public void deleteReferenceAsyncTest() {
        CallbackTest cb = new CallbackTest();
        Reference result = service.createReference(new Reference(workspace.getId(), source.getId(), target.getId(), 2));
        service.deleteReference(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(204, cb.getResponse().getStatus());
        try {
            service.getReferenceById(result.getId());
            fail("Expected the reference to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }
}
