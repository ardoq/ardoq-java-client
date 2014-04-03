package com.ardoq.service;

import com.ardoq.model.Tag;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

public interface TagService {

    @GET("/api/tag")
    List<Tag> getAllTags();

    @GET("/api/tag")
    void getAllTags(Callback<List<Tag>> callback);

    @GET("/api/tag/{id}")
    Tag getTagById(@Path("id") String id);

    @GET("/api/tag/{id}")
    void getTagById(@Path("id") String id, Callback<Tag> callback);

    @POST("/api/tag")
    Tag createTag(@Body Tag tag);

    @POST("/api/tag")
    void createTag(@Body Tag tag, Callback<Tag> callback);

    @PUT("/api/tag/{id}")
    Tag updateTag(@Path("id") String id, @Body Tag tag);

    @PUT("/api/tag/{id}")
    void updateTag(@Path("id") String id, @Body Tag tag, Callback<Tag> callback);

    @DELETE("/api/tag/{id}")
    Response deleteTag(@Path("id") String id);

    @DELETE("/api/tag/{id}")
    void deleteTag(@Path("id") String id, Callback<Response> callback);
}
