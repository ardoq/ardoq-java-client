package com.ardoq.batch;

import com.ardoq.model.Component;

import java.util.UUID;


public class BatchComponent extends Component {

    public BatchComponent(String name, String rootWorkspace, String description) {
        super(name, rootWorkspace, description);
        this.setBatchId(UUID.randomUUID().toString());
    }

    public BatchComponent(String name, String rootWorkspace, String description, String typeId) {
        super(name, rootWorkspace, description, typeId);
        this.setBatchId(UUID.randomUUID().toString());
    }

    public BatchComponent(String name, String description, String typeId, Component parent) {
        super(name, parent.getRootWorkspace(), description, typeId, parent.getContextualId());
        this.setBatchId(UUID.randomUUID().toString());
    }

    public BatchComponent(String name, String description, String typeId, Component parent, String batchId) {
        super(name, parent.getRootWorkspace(), description, typeId, parent.getContextualId());
        this.setBatchId(batchId);
    }

    public BatchComponent(String name, String rootWorkspace, String description, String typeId, String parent) {
        super(name, rootWorkspace, description, typeId, parent);
        this.setBatchId(UUID.randomUUID().toString());
    }

    public BatchComponent(String name, String rootWorkspace, String description, String typeId, String parent, String batchId) {
        super(name, rootWorkspace, description, typeId, parent);
        this.setBatchId(batchId);
    }

}

