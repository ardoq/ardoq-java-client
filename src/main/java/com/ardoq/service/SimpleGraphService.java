package com.ardoq.service;

import com.ardoq.model.GraphResult;

import retrofit.mime.TypedString;

public class SimpleGraphService {
    GraphService graphService;

    public SimpleGraphService(GraphService graphService) {
        this.graphService = graphService;
    }

    public GraphResult search(String q) {
        Object ret = graphService.search(new TypedString(q));
        return (GraphResult)ret;
    }

}
