package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.model.Component;
import com.ardoq.model.Workspace;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class ComponentServiceTest {
    private ComponentService service;
    private Workspace workspace;
    private CallbackTest cb;
    private Component testComponent;

    @Before
    public void before() {
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword"));
        service = client.component();
        workspace = client.workspace().createWorkspace(new Workspace("myWorkspace", "5326fad1e4b0e15cf6c876ae", "Hello world!"));
        cb = new CallbackTest();
        testComponent = new Component("MyComponent", workspace.getId(), "myDescription");
    }

    @Test
    public void getComponentTest() {
        List<Component> allWorkspaces = service.getAllComponents();
        String id = allWorkspaces.get(0).getId();
        assertEquals(id, service.getComponentById(id).getId());
    }

    @Test
    public void getAsyncComponentTest() {
        service.getAllComponents(cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());

        cb = new CallbackTest();
        List<Component> allWorkspaces = service.getAllComponents();
        String id = allWorkspaces.get(0).getId();
        service.getComponentById(id, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void createComponentTest() {
        Component component = service.createComponent(testComponent);
        assertNotNull(component.getId());
    }

    @Test
    public void createComponentAsyncTest() {
        service.createComponent(testComponent, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
    }

    @Test
    public void updateComponentTest() {
        Component component = service.createComponent(testComponent);
        component.setDescription("Updated description");
        Component updatedComponent = service.updateComponent(component.getId(), component);
        assertEquals("Updated description", updatedComponent.getDescription());
        assertEquals(component.get_version(), (Integer) (updatedComponent.get_version() - 1));
    }

    @Test
    public void updateComponentAsyncTest() {
        Component component = service.createComponent(testComponent);
        component.setDescription("Updated description");
        service.updateComponent(component.getId(), component, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void deleteComponentTest() {
        Component result = service.createComponent(testComponent);
        Response response = service.deleteComponent(result.getId());
        assertEquals(204, response.getStatus());
        try {
            service.getComponentById(result.getId());
            fail("Expected the Component to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }

    @Test
    public void deleteComponentAsyncTest() {
        Component result = service.createComponent(testComponent);
        service.deleteComponent(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(204, cb.getResponse().getStatus());
        try {
            service.getComponentById(result.getId());
            fail("Expected the Component to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }

}
