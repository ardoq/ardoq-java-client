package com.ardoq.service;

import com.ardoq.model.Model;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

/**
 * Handles our models. Will change in the future.
 *
 * @deprecated
 */
public interface DeprecatedModelService {

    @GET("/api/model")
    List<Model> getAllModels();

    @GET("/api/model")
    void getAllModels(Callback<List<Model>> callback);

    @GET("/api/model/{id}")
    Model getModelById(@Path("id") String id);

    @GET("/api/model/{id}")
    void getModelById(@Path("id") String id, Callback<Model> callback);
}
