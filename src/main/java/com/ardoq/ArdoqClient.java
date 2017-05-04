package com.ardoq;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.ardoq.adapter.ReferenceAdapter;
import com.ardoq.model.Reference;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ardoq.adapter.ComponentAdapter;
import com.ardoq.adapter.Iso8601Adapter;
import com.ardoq.adapter.ModelAdapter;
import com.ardoq.model.Component;
import com.ardoq.model.Model;
import com.ardoq.service.AttachmentService;
import com.ardoq.service.ComponentService;
import com.ardoq.service.FieldService;
import com.ardoq.service.ModelService;
import com.ardoq.service.ReferenceService;
import com.ardoq.service.SimpleModelService;
import com.ardoq.service.SimpleWorkspaceService;
import com.ardoq.service.TagService;
import com.ardoq.service.WorkspaceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.client.Client;
import retrofit.converter.GsonConverter;

/**
 * ArdoqClient connects to your Ardoq installation via it's REST-apis.
 * You can connect with token authentication or username and password.
 *
 * @author Erik Bakstad
 */
public class ArdoqClient {
    private String org;
    private RestAdapter restAdapter;
    private String endpoint;
    private RequestInterceptor requestInterceptor;
    private Client client;

    private RequestInterceptor getRequestInterceptor(String endpoint, final String token) {
        if (endpoint == null || token == null) {
            throw new IllegalArgumentException("Endpoint and token must be set correctly!");
        }
        return new RequestInterceptor() {
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("Authorization", "Token token=" + token.trim());
                requestFacade.addHeader("User-Agent", "ardoq-java-client-" + getVersion());
                if (org != null) {
                    requestFacade.addQueryParam("org", org);
                }
            }
        };
    }

    /**
     * Connects to your Ardoq installation with token authentication.
     *
     * @param endpoint The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param token    The token generated via Profile -&gt; APIS token that you wish to authenticate with
     */
    public ArdoqClient(final String endpoint, final String token) {
        this.restAdapter = initAdapter(endpoint, getRequestInterceptor(endpoint, token));
    }

    /**
     * Connects to Ardoq with token authentication, and a custom client
     *
     * @param endpoint
     * @param token
     * @param client
     */
    public ArdoqClient(final String endpoint, final String token, final HttpClient client) {
        this.restAdapter = initAdapter(endpoint, getRequestInterceptor(endpoint, token), new ApacheClient(client));
    }

    /**
     * Connects to your Ardoq installation with token authentication.
     *
     * @param endpoint                 The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param token                    The token generated via Profile -&gt; APIS token that you wish to authenticate with
     * @param connectionTimeoutSeconds HttpClient connection timeout in seconds (defaults to 15s)
     * @param readTimeoutSeconds       HttpClient read timeout in seconds (defaults to 20s)
     */
    public ArdoqClient(final String endpoint, final String token, final long connectionTimeoutSeconds, final long readTimeoutSeconds) {
        ApacheClient client = getHttpClient(connectionTimeoutSeconds, readTimeoutSeconds);
        this.restAdapter = initAdapter(endpoint, getRequestInterceptor(endpoint, token), client);
    }

    /**
     * Connects to your Ardoq installation with token authentication and a custom request configuration.
     *
     * @param endpoint             The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param token                The token generated via Profile -&gt; APIS token that you wish to authenticate with
     * @param defaultRequestConfig User provided org.apache.http.client.config.RequestConfig (for setting i.e. proxy settings etc.)
     */
    public ArdoqClient(final String endpoint, final String token, final RequestConfig defaultRequestConfig) {
        ApacheClient client = new ApacheClient(HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build());
        this.restAdapter = initAdapter(endpoint, getRequestInterceptor(endpoint, token), client);
    }

    private ApacheClient getHttpClient(long connectionTimeoutSeconds, long readTimeoutSeconds) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(coereceToInt(connectionTimeoutSeconds * 1000))
                .setConnectionRequestTimeout(coereceToInt(readTimeoutSeconds * 100))
                .build();
        CloseableHttpClient build = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        return new ApacheClient(build);
    }

    /**
     * Connects to Ardoq with username and password
     * **We Strongly suggest that you connect with a token instead**
     *
     * @param endpoint The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param username Your username
     * @param password Your password
     */
    public ArdoqClient(final String endpoint, final String username, final String password) {
        this.restAdapter = initAdapter(endpoint, getRequestInterceptorBasicAuth(endpoint, username, password));
    }

    /**
     * Connects to Ardoq with username and password, and a custom HTTP client
     * **We Strongly suggest that you connect with a token instead**
     *
     * @param endpoint The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param username Your username
     * @param password Your password
     */
    public ArdoqClient(final String endpoint, final String username, final String password, final HttpClient client) {
        this.restAdapter = initAdapter(endpoint, getRequestInterceptorBasicAuth(endpoint, username, password), new ApacheClient(client));
    }

    /**
     * Connects to Ardoq with username and password and a custom request configuration
     * **We Strongly suggest that you connect with a token instead**
     *
     * @param endpoint             The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param username             Your username
     * @param password             Your password
     * @param defaultRequestConfig User provided org.apache.http.client.config.RequestConfig (for setting i.e. proxy settings etc.)
     */
    public ArdoqClient(final String endpoint, final String username, final String password, final RequestConfig defaultRequestConfig) {
        ApacheClient client = new ApacheClient(HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build());
        this.restAdapter = initAdapter(endpoint, getRequestInterceptorBasicAuth(endpoint, username, password), client);
    }

    /**
     * Connects to Ardoq with username and password
     * **We Strongly suggest that you connect with a token instead**
     *
     * @param endpoint                 The Ardoq installation you wish to connect to (e.g. https://app.ardoq.com)
     * @param username                 Your username
     * @param password                 Your password
     * @param connectionTimeoutSeconds HttpClient connection timeout in seconds (defaults to 15s)
     * @param readTimeoutSeconds       HttpClient read timeout in seconds (defaults to 20s)
     */
    public ArdoqClient(final String endpoint, final String username, final String password, final long connectionTimeoutSeconds, final long readTimeoutSeconds) {
        ApacheClient httpClient = getHttpClient(connectionTimeoutSeconds, readTimeoutSeconds);
        this.restAdapter = initAdapter(endpoint, getRequestInterceptorBasicAuth(endpoint, username, password), httpClient);
    }

    private RequestInterceptor getRequestInterceptorBasicAuth(String endpoint, final String username, final String password) {
        if (endpoint == null || username == null || password == null) {
            throw new IllegalArgumentException("Endpoint, username and password must be set correctly!");
        }

        return new RequestInterceptor() {
            public void intercept(RequestFacade requestFacade) {
                String pwd = Base64.encodeBase64String((username + ":" + password).getBytes());
                requestFacade.addHeader("Authorization", "Basic " + pwd);
                requestFacade.addHeader("User-Agent", "ardoq-java-client-" + getVersion());
                if (org != null) {
                    requestFacade.addQueryParam("org", org);
                }
            }
        };
    }

    private static int coereceToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    /**
     * Set log level
     *
     * @param level
     */
    public void setLogLevel(RestAdapter.LogLevel level) {
        this.restAdapter.setLogLevel(level);
    }

    /**
     * Configures the restAdapter to serialize null values.
     * The primary use case for this is to reset component field values.
     * Without this setting, nulling a component field value would cause it not to be transfered, causing the API to think that the value is unchanged.
     *
     * @param serializeNulls
     */
    public void setSerializeNulls(boolean serializeNulls) {
        if (this.client != null) {
            this.restAdapter = initAdapter(this.endpoint, this.requestInterceptor, this.client, serializeNulls);
        } else {
            this.restAdapter = initAdapter(this.endpoint, this.requestInterceptor, serializeNulls);
        }
    }

    private RestAdapter.Builder builderDefaults(String endpoint, RequestInterceptor requestInterceptor, boolean serializeNulls) {
        GsonBuilder gsonBuilder =
                new GsonBuilder()
                        .registerTypeAdapter(Date.class, new Iso8601Adapter())
                        .registerTypeAdapter(Component.class, new ComponentAdapter())
                        .registerTypeAdapter(Reference.class, new ReferenceAdapter())
                        .registerTypeAdapter(Model.class, new ModelAdapter());

        if (serializeNulls) {
            gsonBuilder.serializeNulls();
        }

        Gson gson = gsonBuilder.create();

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor);
    }

    private RestAdapter initAdapter(String endpoint, RequestInterceptor requestInterceptor) {
        return initAdapter(endpoint, requestInterceptor, false);
    }

    private RestAdapter initAdapter(String endpoint, RequestInterceptor requestInterceptor, Client client) {
        return initAdapter(endpoint, requestInterceptor, client, false);
    }

    private RestAdapter initAdapter(String endpoint, RequestInterceptor requestInterceptor, Client client, boolean serializeNulls) {
        this.endpoint = endpoint;
        this.requestInterceptor = requestInterceptor;
        this.client = client;
        return builderDefaults(endpoint, requestInterceptor, serializeNulls).setClient(client).build();
    }

    private RestAdapter initAdapter(String endpoint, RequestInterceptor requestInterceptor, boolean serializeNulls) {
        this.endpoint = endpoint;
        this.requestInterceptor = requestInterceptor;
        return builderDefaults(endpoint, requestInterceptor, serializeNulls).build();
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

    /**
     * Sets the organization you wish to work on. If you have a private organization account, please use this!
     *
     * @param org The organization account, default is the global Ardoq account (Personal)
     * @return this ArdoqClient
     */
    public ArdoqClient setOrganization(String org) {
        this.org = org;
        return this;
    }

    /**
     * Creates a workspaceService that allows you to do operations with our Workspace API
     *
     * @return WorkspaceService
     */
    public WorkspaceService workspace() {
        return new SimpleWorkspaceService(restAdapter.create(WorkspaceService.class));
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
        return new SimpleModelService(restAdapter.create(ModelService.class));
    }
}
