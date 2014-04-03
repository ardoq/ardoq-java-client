package com.ardoq.service;

import com.ardoq.model.Field;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

public interface FieldService {

    @GET("/api/field")
    List<Field> getAllFields();

    @GET("/api/field")
    void getAllFields(Callback<List<Field>> callback);

    @GET("/api/field/{id}")
    Field getFieldById(@Path("id") String id);

    @GET("/api/field/{id}")
    void getFieldById(@Path("id") String id, Callback<Field> callback);

    @POST("/api/field")
    Field createField(@Body Field Field);

    @POST("/api/field")
    void createField(@Body Field Field, Callback<Field> callback);

    @PUT("/api/field/{id}")
    Field updateField(@Path("id") String id, @Body Field Field);

    @PUT("/api/field/{id}")
    void updateField(@Path("id") String id, @Body Field Field, Callback<Field> callback);

    @DELETE("/api/field/{id}")
    Response deleteField(@Path("id") String id);

    @DELETE("/api/field/{id}")
    void deleteField(@Path("id") String id, Callback<Response> callback);
}
