package com.ardoq.service;

import com.ardoq.batch.BatchCreateRequest;
import retrofit.http.Body;
import retrofit.http.POST;

import java.util.Map;

public interface BatchService {

    @POST("/api/batch")
    Map<String, Map<String,String>> create(@Body BatchCreateRequest batch);

}
