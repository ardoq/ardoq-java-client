package com.ardoq.batch;

import com.ardoq.model.Component;
import com.ardoq.model.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchCreateRequest {

    private final BatchOp op = BatchOp.create;
    private List<Component> components;
    private List<Reference> references;
    private BatchOptions options;

    public BatchCreateRequest(BatchOptions options) {
        this.components = new ArrayList<Component>();
        this.references = new ArrayList<Reference>();
        this.options = options;
    }

    public BatchCreateRequest() {
        this(new BatchOptions());
    }

    public String getOp() {
        return op.toString();
    }

    public BatchOptions getOptions() {
        return this.options;
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public List<Reference> getReferences() {
        return this.references;
    }

    public void addComponent(Component c) {
        this.components.add(c);
    }

    public void addComponents(Component ... c) {
        this.components.addAll(Arrays.asList(c));
    }

    public void addReference(Reference r) {
        this.references.add(r);
    }

    public void addReferences(Reference ... r) {
        this.references.addAll(Arrays.asList(r));
    }


}

