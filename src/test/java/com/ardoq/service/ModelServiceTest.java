package com.ardoq.service;

import com.ardoq.ArdoqClient;
import com.ardoq.TestUtils;
import com.ardoq.model.Model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelServiceTest {
    private ModelService service;
    private String modelId;

    @Before
    public void before() {
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword"));
        service = client.model();
        modelId = TestUtils.getTestPropery("modelId");

    }

    @Test
    public void getModelByNameTest() {
        Model modelById = service.getModelById(modelId);
        Model modelByName = service.getModelByName(modelById.getName());
        assertEquals(modelById.getId(), modelByName.getId());
    }
}
