package com.ardoq.batch;

import com.ardoq.model.Component;
import com.ardoq.model.Reference;

import java.util.UUID;

public class BatchReference extends Reference {

    public BatchReference(String description, Component source, Component target, int type) {
        super(source.getRootWorkspace(), description, source.getContextualId(), target.getContextualId(), type);
        this.setBatchId(UUID.randomUUID().toString());
        this.setTargetWorkspace(target.getRootWorkspace());
    }

    public BatchReference(String rootWorkspace, String description, Component source, Component target, int type, String batchId) {
        super(rootWorkspace, description, source.getContextualId(), target.getContextualId(), type);
        this.setBatchId(batchId);
        this.setTargetWorkspace(target.getRootWorkspace());
    }

    public BatchReference(String rootWorkspace, String targetWorkspace, String description, String source, String target, int type, String batchId) {
        super(rootWorkspace, description, source, target, type);
        this.setBatchId(batchId);
        this.setTargetWorkspace(targetWorkspace);
    }
}



