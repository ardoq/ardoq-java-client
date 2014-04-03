package com.ardoq.service;

import com.ardoq.model.Reference;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

public interface ReferenceService {

    @GET("/api/reference")
    List<Reference> getAllReferences();

    @GET("/api/reference")
    void getAllReferences(Callback<List<Reference>> callback);

    @GET("/api/reference/{id}")
    Reference getReferenceById(@Path("id") String id);

    @GET("/api/reference/{id}")
    void getReferenceById(@Path("id") String id, Callback<Reference> callback);

    @POST("/api/reference")
    Reference createReference(@Body Reference reference);

    @POST("/api/reference")
    void createReference(@Body Reference reference, Callback<Reference> callback);

    @PUT("/api/reference/{id}")
    Reference updateReference(@Path("id") String id, @Body Reference reference);

    @PUT("/api/reference/{id}")
    void updateReference(@Path("id") String id, @Body Reference reference, Callback<Reference> callback);

    @DELETE("/api/reference/{id}")
    Response deleteReference(@Path("id") String id);

    @DELETE("/api/reference/{id}")
    void deleteReference(@Path("id") String id, Callback<Response> callback);
}
