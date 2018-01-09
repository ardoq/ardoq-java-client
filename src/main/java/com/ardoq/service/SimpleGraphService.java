package com.ardoq.service;

import com.ardoq.model.GraphPathResult;
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

    public GraphPathResult pathSearch(String q) {
        Object ret = graphService.pathSearch(new TypedString(q));
        return (GraphPathResult)ret;
    }

}
