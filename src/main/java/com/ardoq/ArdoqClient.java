package com.ardoq;

import com.ardoq.model.Component;
import com.ardoq.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.util.Date;

public class ArdoqClient {
    private String org;
    private RestAdapter restAdapter;

    public ArdoqClient(final String endpoint, final String username, final String password) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                String pwd = Base64.encode((username + ":" + password).getBytes());
                requestFacade.addHeader("Authorization", "Basic " + pwd);
                if (org != null) {
                    requestFacade.addQueryParam("org", org);
                }
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .registerTypeAdapter(Component.class, new ComponentAdapter())
                .create();

        this.restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .build();
    }

    public ArdoqClient setOrganization(String org) {
        this.org = org;
        return this;
    }

    public WorkspaceService workspace() {
        return restAdapter.create(WorkspaceService.class);
    }

    public ComponentService component() {
        return restAdapter.create(ComponentService.class);
    }

    public ReferenceService reference() {
        return restAdapter.create(ReferenceService.class);
    }

    public TagService tag() {
        return restAdapter.create(TagService.class);
    }

    public FieldService field() {
        return restAdapter.create(FieldService.class);
    }

    public AttachmentService attachment() {
        return restAdapter.create(AttachmentService.class);
    }
}
