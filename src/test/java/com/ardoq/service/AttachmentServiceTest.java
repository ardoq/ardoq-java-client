package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.CallbackTest;
import com.ardoq.TestUtils;
import com.ardoq.model.Attachment;
import com.ardoq.model.Workspace;
import org.junit.Before;
import org.junit.Test;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AttachmentServiceTest {
    private String mimeType;
    private String filename;
    private String workspaceId;

    private Workspace workspace;
    private CallbackTest cb;
    private AttachmentService service;

    @Before
    public void before() throws IOException {
        mimeType = TestUtils.getTestPropery("mimeType");
        filename = TestUtils.getTestPropery("filename");
        workspaceId = TestUtils.getTestPropery("workspaceIdWithAttachments");
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword")).setOrganization("ardoq");
        service = client.attachment();
        workspace = client.workspace().createWorkspace(new Workspace("myWorkspace", TestUtils.getTestPropery("modelId"), "Hello world!"));
        cb = new CallbackTest();
    }

    @Test
    public void getAttachmentTest() {
        List<Attachment> attachments = service.getAttachments("workspace", workspaceId);
        for (Attachment attachment : attachments) {
            assertNotNull(attachment.getId());
        }
    }

    @Test
    public void getAttachmentAsyncTest() {
        service.getAttachments("workspace", workspaceId, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
    }

    @Test
    public void uploadAttachmentTest() {
        String filename = this.filename;
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(filename).getPath());
        Attachment attachment = service.uploadAttachment("workspace", workspace.getId(), new TypedFile(mimeType, file));
        assertEquals(attachment.getFilename(), filename);
        Response deleteResponse = service.deleteAttachment("workspace", workspace.getId(), attachment.getFilename());
        assertEquals(204, deleteResponse.getStatus());
    }

    @Test
    public void uploadAttachmentAsyncTest() {
        String filename = this.filename;
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(filename).getPath());
        service.uploadAttachment("workspace", workspace.getId(), new TypedFile(mimeType, file), cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(201, cb.getResponse().getStatus());
        cb = new CallbackTest();
        service.deleteAttachment("workspace", workspace.getId(), filename, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(204, cb.getResponse().getStatus());
    }

    @Test
    public void downloadAttachmentTest() {
        String filename = this.filename;
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(filename).getPath());
        service.uploadAttachment("workspace", workspace.getId(), new TypedFile(mimeType, file));
        Response download = service.downloadAttachment("workspace", workspace.getId(), filename);
        assertTrue(download.getBody().mimeType().startsWith(mimeType));
        assertEquals(file.length(), download.getBody().length());
    }

    @Test
    public void downloadAttachmentAsyncTest() {
        String filename = this.filename;
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(filename).getPath());
        service.uploadAttachment("workspace", workspace.getId(), new TypedFile(mimeType, file));
        service.downloadAttachment("workspace", workspace.getId(), filename, cb);
        await().atMost(4, TimeUnit.SECONDS).untilTrue(cb.done());
        assertEquals(200, cb.getResponse().getStatus());
        assertTrue(cb.getResponse().getBody().mimeType().startsWith(mimeType));

    }
}
