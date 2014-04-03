package com.ardoq.service;

import com.ardoq.model.Attachment;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

public interface AttachmentService {

    @GET("/api/attachment/{resourceType}/{resourceId}")
    public List<Attachment> getAttachments(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId);

    @GET("/api/attachment/{resourceType}/{resourceId}")
    void getAttachments(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, Callback<List<Attachment>> callback);

    @Multipart
    @POST("/api/attachment/{resourceType}/{resourceId}/upload")
    Attachment uploadAttachment(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, @Part("attachment") TypedFile attachment);

    @Multipart
    @POST("/api/attachment/{resourceType}/{resourceId}/upload")
    void uploadAttachment(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, @Part("attachment") TypedFile attachment, Callback<Attachment> callback);

    @DELETE("/api/attachment/{resourceType}/{resourceId}/{filename}")
    Response deleteAttachment(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, @Path("filename") String filename);

    @DELETE("/api/attachment/{resourceType}/{resourceId}/{filename}")
    void deleteAttachment(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, @Path("filename") String filename, Callback<Response> callback);

    @GET("/api/attachment/{resourceType}/{resourceId}/{filename}")
    Response downloadAttachment(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, @Path("filename") String filename);

    @GET("/api/attachment/{resourceType}/{resourceId}/{filename}")
    void downloadAttachment(@Path("resourceType") String resourceType, @Path("resourceId") String resourceId, @Path("filename") String filename, Callback<Response> callback);
}
