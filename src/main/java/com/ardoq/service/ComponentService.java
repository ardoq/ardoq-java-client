package com.ardoq.service;

import com.ardoq.model.Component;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;
import java.util.Map;

public interface ComponentService {

    @GET("/api/component")
    List<Component> getAllComponents();

    @GET("/api/component")
    void getAllComponents(Callback<List<Component>> callback);

    @GET("/api/component/{id}")
    Component getComponentById(@Path("id") String id);

    @GET("/api/component/{id}")
    void getComponentById(@Path("id") String id, Callback<Component> callback);

    @GET("/api/component/{id}")
    Component getComponentById(@Path("id") String id, @QueryMap Map<String, String> queryParams);

    @GET("/api/component/{id}")
    void getComponentById(@Path("id") String id, @QueryMap Map<String, String> queryParams, Callback<Component> callback);

    /**
     * Find components by one or more fields. A field matches if the field equals the query (partial matches are not returned).
     * If multiple fields are passed it will return components where all fields match.
     *
     * @param fields Map&lt;String,String&gt; fieldName -&gt; expectedFieldValue
     * @return list of components 
     */
    @GET("/api/component/fieldsearch")
    List<Component> findComponentsByFields(@QueryMap Map<String, String> fields);

    @GET("/api/component/fieldsearch")
    List<Component> findComponentsByFields(@QueryMap Map<String, String> fields, Callback<Component> callback);

    @GET("/api/component/search")
    List<Component> findComponentsInWorkspaceByName(@Query("workspace") String workspaceId,
                                                    @Query("name") String name);

    @GET("/api/component/search")
    List<Component> findComponentsInWorkspaceByName(@Query("workspace") String workspaceId,
                                                    @Query("name") String name,
                                                    Callback<Component> callback);

    @POST("/api/component")
    Component createComponent(@Body Component component);

    @POST("/api/component")
    void createComponent(@Body Component component, Callback<Component> callback);

    /**
     * Updates a component and returns a NEW immutable component.
     *
     * @param id        The ID of the component to update
     * @param component The component's data that you wish to update
     * @return updated and synced component
     * @see com.ardoq.model.Component
     */
    @PUT("/api/component/{id}")
    Component updateComponent(@Path("id") String id, @Body Component component);

    @PUT("/api/component/{id}")
    void updateComponent(@Path("id") String id, @Body Component component, Callback<Component> callback);

    @DELETE("/api/component/{id}")
    Response deleteComponent(@Path("id") String id);

    @DELETE("/api/component/{id}")
    void deleteComponent(@Path("id") String id, Callback<Response> callback);
}
