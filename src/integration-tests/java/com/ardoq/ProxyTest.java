package com.ardoq;

import net.lightbody.bmp.proxy.ProxyServer;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProxyTest {

    private ProxyServer server;
    private RequestConfig localhost;

    @Before
    public void setup() {
        server = new ProxyServer(9091);
        localhost = RequestConfig.custom().setProxy(new HttpHost("127.0.0.1", 9091)).build();
    }

    @Test
    public void testUsesProxy() {
        ArdoqClient client = new ArdoqClient(
                "http://127.0.0.1:3000",
                System.getenv("ardoqUsername"),
                System.getenv("ardoqPassword"), localhost).setOrganization(TestUtils.getTestPropery("organization"));

        try {
            client.field().getAllFields();
            fail("Should have thrown a network exception since proxy is not running");
        } catch (RetrofitError e) {
            assertEquals(e.getKind(), RetrofitError.Kind.NETWORK);
        }
    }

    @Test
    public void testValidProxy() {
        ArdoqClient client = new ArdoqClient(
                "http://127.0.0.1:3000",
                System.getenv("ardoqUsername"),
                System.getenv("ardoqPassword"), localhost).setOrganization(TestUtils.getTestPropery("organization"));
        server.start();
        client.field().getAllFields();
        server.stop();
    }
}
