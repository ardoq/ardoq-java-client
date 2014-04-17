package com.ardoq;

import com.ardoq.adapter.ComponentAdapter;
import com.ardoq.adapter.Iso8601Adapter;
import com.ardoq.adapter.ModelAdapter;
import com.ardoq.model.Component;
import com.ardoq.model.Model;
import com.ardoq.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class ArdoqClient {
    private String org;
    private final RestAdapter restAdapter;

    public ArdoqClient(final String endpoint, final String token) {
        if (endpoint == null || token == null) {
            throw new IllegalArgumentException("Endpoint and token must be set correctly!");
        }
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("Authorization", "Token token=" + token.trim());
                requestFacade.addHeader("User-Agent", "ardoq-java-client-" + getVersion());
                if (org != null) {
                    requestFacade.addQueryParam("org", org);
                }
            }
        };

        this.restAdapter = initAdapter(endpoint, requestInterceptor);

    }

    public ArdoqClient(final String endpoint, final String username, final String password) {
        if (endpoint == null || username == null || password == null) {
            throw new IllegalArgumentException("Endpoint, username and password must be set correctly!");
        }

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                String pwd = Base64.encodeBase64String((username + ":" + password).getBytes());
                requestFacade.addHeader("Authorization", "Basic " + pwd);
                requestFacade.addHeader("User-Agent", "ardoq-java-client-" + getVersion());
                if (org != null) {
                    requestFacade.addQueryParam("org", org);
                }
            }
        };

        this.restAdapter = initAdapter(endpoint, requestInterceptor);
    }

    private RestAdapter initAdapter(String endpoint, RequestInterceptor requestInterceptor) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new Iso8601Adapter())
                .registerTypeAdapter(Component.class, new ComponentAdapter())
                .registerTypeAdapter(Model.class, new ModelAdapter())
                .create();

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .build();
    }

    private String getVersion() {
        String version;
        Properties properties = new Properties();
        try {
            properties.load(ArdoqClient.class.getResourceAsStream("/version.properties"));
            version = properties.getProperty("client-version");
        } catch (IOException e) {
            version = "Unknown";
        }
        return version;
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

    public SimpleModelService model() {
        return new SimpleModelService(restAdapter.create(DeprecatedModelService.class));
    }
}
