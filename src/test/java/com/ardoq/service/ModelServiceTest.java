package com.ardoq.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ardoq.ArdoqClient;
import com.ardoq.TestUtils;
import com.ardoq.model.Model;

public class ModelServiceTest {
    private SimpleModelService service;
    private String modelId;

    @Before
    public void before() {
        ArdoqClient client = new ArdoqClient(System.getenv("ardoqHost"), System.getenv("ardoqUsername"), System.getenv("ardoqPassword")).setOrganization(TestUtils.getTestPropery("organization"));
        service = client.model();
        modelId = TestUtils.getTestPropery("modelId");

    }

    public void getModelByNameTest() {
        Model modelById = service.getModelById(modelId);
        Model modelByName = service.getTemplateByName(modelById.getName());
        assertEquals(modelById.getId(), modelByName.getId());
    }

    @Test
    public void createModelTest() throws Exception {
        Model m = service.findOrCreateTemplate("Maven",getClass().getResourceAsStream("/mavenModel.json"));
        System.out.println(m);
    }

}
