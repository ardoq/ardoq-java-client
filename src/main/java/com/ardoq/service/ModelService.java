package com.ardoq.service;

import java.util.List;

import com.ardoq.model.Model;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.TypedString;

public interface ModelService {


    @GET("/api/model")
    List<Model> getAllModels();

    @GET("/api/model?includeCommon=true")
    List<Model> getAllModelsIncludingCommonTemplate();

    @GET("/api/model/{id}")
    Model getModelById(@Path("id") String id);

    @GET("/api/model/{id}")
    Model getTemplateById(@Path("id") String id);

    Model getTemplateByName(String name);

    Model findOrCreateTemplate(String name, String modelJson);

    @POST("/api/model")
    @Headers("Content-Type: application/json")
    Model createModel(@Body TypedString model);


}
