package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.TestUtils;
import com.ardoq.model.Component;
import com.ardoq.model.Workspace;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.HashMap;
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
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword"), 30, 30).setOrganization(TestUtils.getTestPropery("organization"));
        service = client.component();
        workspace = client.workspace().createWorkspace(new Workspace("myWorkspace",
                TestUtils.getTestPropery("modelId"), "Hello world!"));
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
        await().atMost(40, TimeUnit.SECONDS).untilTrue(cb.done());
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

    @Test
    public void fieldSearchTest() {
        testComponent.setFields(new HashMap<String, Object>() {{
            this.put("myField", "testSearch");
        }});

        Component createdComponent = service.createComponent(testComponent);
        List<Component> results = service.findComponentsByFields(new HashMap<String, String>() {{
            this.put("myField", "testSearch");
            this.put("rootWorkspace", testComponent.getRootWorkspace());
        }});

        assertEquals(1, results.size());
        assertEquals(results.get(0).getId(), createdComponent.getId());
    }

    @Test
    public void fieldSearchEmptyResultTest() {
        testComponent.setFields(new HashMap<String, Object>() {{
            this.put("myField", "testSearch");
        }});
        service.createComponent(testComponent);
        List<Component> results = service.findComponentsByFields(new HashMap<String, String>() {{
            this.put("missingField", "test");
            this.put("rootWorkspace", testComponent.getRootWorkspace());
        }});
        assertEquals(0, results.size());
    }

    @Test
    public void findComponentInWorkspaceByNameTest() {
        Component component = service.createComponent(testComponent);
        List<Component> results = service.findComponentsInWorkspaceByName(component.getRootWorkspace(), component.getName());
        assertEquals(1, results.size());
        assertEquals(component.getName(), results.get(0).getName());
    }

}
