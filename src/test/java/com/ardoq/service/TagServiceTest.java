package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.TestUtils;
import com.ardoq.model.Tag;
import com.ardoq.model.Workspace;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class TagServiceTest {
    private Tag testTag;
    private TagService service;
    private Workspace workspace;
    private CallbackTest cb;

    @Before
    public void before() {
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword")).setOrganization(TestUtils.getTestPropery("organization"));
        service = client.tag();
        workspace = client.workspace().createWorkspace(new Workspace("myWorkspace", "Hello world!").withComponentModel(TestUtils.getTestPropery("modelId")));
        testTag = new Tag("myTag", workspace.getId(), "Hello world!");
        cb = new CallbackTest();
    }

    @Test
    public void getTagTest() {
        List<Tag> allTags = service.getAllTags();
        String id = allTags.get(0).getId();
        assertEquals(id, service.getTagById(id).getId());
    }

    @Test
    public void getAsyncTagTest() {
        service.getAllTags(cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());

        cb = new CallbackTest();
        List<Tag> allTags = service.getAllTags();
        String id = allTags.get(0).getId();
        service.getTagById(id, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void createTagTest() {
        Tag result = service.createTag(new Tag("myTag", workspace.getId(), ""));
        assertNotNull(result.getId());
    }

    @Test
    public void createTagAsyncTest() {
        service.createTag(new Tag("myTag", workspace.getId(), ""), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
    }

    @Test
    public void updateTagTest() {
        Tag result = service.createTag(testTag);
        result.setName("updatedName");
        Tag updatedTag = service.updateTag(result.getId(), result);
        assertEquals("updatedName", updatedTag.getName());
    }

    @Test
    public void updateTagAsyncTest() {
        Tag result = service.createTag(testTag);
        result.setName("updatedName");
        service.updateTag(result.getId(), result, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void deleteTagTest() {
        Tag result = service.createTag(testTag);
        Response response = service.deleteTag(result.getId());
        assertEquals(204, response.getStatus());
        try {
            service.getTagById(result.getId());
            fail("Expected the tag to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }

    @Test
    public void deleteTagAsyncTest() {
        Tag result = service.createTag(testTag);
        service.deleteTag(result.getId(), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(204, cb.getResponse().getStatus());
        try {
            service.getTagById(result.getId());
            fail("Expected the tag to be deleted.");
        } catch (RetrofitError e) {
            assertEquals(404, e.getResponse().getStatus());
        }
    }
}
