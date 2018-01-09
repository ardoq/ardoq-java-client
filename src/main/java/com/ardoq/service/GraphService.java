package com.ardoq.service;

import com.ardoq.model.GraphPathResult;
import com.ardoq.model.GraphResult;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.mime.TypedString;

public interface GraphService {

    @POST("/api/graph-search")
    @Headers("Content-Type: application/json")
    GraphResult search(@Body TypedString q);

    @POST("/api/graph-search")
    @Headers("Content-Type: application/json")
    GraphPathResult pathSearch(@Body TypedString q);

}
