package com.ardoq.service;

import com.ardoq.model.Component;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

public interface ComponentService {

    @GET("/api/component")
    List<Component> getAllComponents();

    @GET("/api/component")
    void getAllComponents(Callback<List<Component>> callback);

    @GET("/api/component/{id}")
    Component getComponentById(@Path("id") String id);

    @GET("/api/component/{id}")
    void getComponentById(@Path("id") String id, Callback<Component> callback);

    @POST("/api/component")
    Component createComponent(@Body Component component);

    @POST("/api/component")
    void createComponent(@Body Component component, Callback<Component> callback);

    @PUT("/api/component/{id}")
    Component updateComponent(@Path("id") String id, @Body Component component);

    @PUT("/api/component/{id}")
    void updateComponent(@Path("id") String id, @Body Component component, Callback<Component> callback);

    @DELETE("/api/component/{id}")
    Response deleteComponent(@Path("id") String id);

    @DELETE("/api/component/{id}")
    void deleteComponent(@Path("id") String id, Callback<Response> callback);
}
