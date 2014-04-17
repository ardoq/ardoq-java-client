package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.TestUtils;
import com.ardoq.model.Component;
import com.ardoq.model.Field;
import com.ardoq.model.FieldType;
import com.ardoq.model.Workspace;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class FieldServiceTest {
    private FieldService service;
    private Workspace workspace;
    private Component component;
    private CallbackTest cb;
    private Field testField;

    @Before
    public void before() {
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword")).setOrganization(TestUtils.getTestPropery("organization"));
        String modelId = TestUtils.getTestPropery("modelId");
        service = client.field();
        workspace = client.workspace().createWorkspace(new Workspace("myWorkspace", modelId, "Hello world!"));
        component = client.component().createComponent(new Component("Component", workspace.getId(), ""));
        cb = new CallbackTest();
        testField = new Field("maintainer", "maintainer", modelId, component.getType(), FieldType.EMAIL);
    }

    @Test
    public void getFieldTest() {
        List<Field> allFields = service.getAllFields();
        String id = allFields.get(0).getId();
        assertEquals(id, service.getFieldById(id).getId());
    }

    @Test
    public void getAsyncFieldTest() {
        service.getAllFields(cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());

        cb = new CallbackTest();
        List<Field> allField = service.getAllFields();
        String id = allField.get(0).getId();
        service.getFieldById(id, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void createFieldTest() {
        Field result = service.createField(testField);
        assertNotNull(result.getId());
    }

    @Test
    public void createFieldAsyncTest() {
        service.createField(testField, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
    }

    @Test
    public void updateFieldTest() {
        Field result = service.createField(testField);
        result.setName("updatedName");
        Field updatedField = service.updateField(result.getId(), result);
        assertEquals("updatedName", updatedField.getName());
    }

    @Test
    public void updateFieldAsyncTest() {
        Field result = service.createField(testField);
        result.setName("updatedName");
        service.updateField(result.getId(), result, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void deleteFieldTest() {
        Field result = service.createField(testField);
        Response response = service.deleteField(result.getId());
        assertEquals(204, response.getStatus());
        try {
            service.getFieldById(result.getId());
            fail("Expected the Field to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }

    @Test
    public void deleteFieldAsyncTest() {
        Field result = service.createField(testField);
        service.deleteField(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(204, cb.getResponse().getStatus());
        try {
            service.getFieldById(result.getId());
            fail("Expected the Field to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }
}
