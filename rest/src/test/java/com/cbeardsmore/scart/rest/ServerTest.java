package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.rest.utils.TestServer;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClient;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.OptionsMethod;
import com.despegar.http.client.PostMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

    private static final String ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
    private static final String ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private static final String BASE_URL = "http://localhost:4567/";
    private static final UUID CART_ID = UUID.randomUUID();

    private static HttpClient httpClient;
    private static TestServer server;

    static {
        httpClient = new HttpClient(1);
        server = new TestServer();
    }

    @BeforeEach
    void before() {
        server.init();
    }

    @AfterEach
    void after() {
        server.destroy();
    }

    @Test
    void givenValidPingRequestShouldReturn200() throws HttpClientException {
        final GetMethod get = new GetMethod(BASE_URL + "ping", false);
        HttpResponse response = httpClient.execute(get);
        assertEquals(200, response.code());
    }

    @Test
    void givenValidOptionsRequestShouldReturn200() throws HttpClientException {
        final OptionsMethod options = new OptionsMethod(BASE_URL, false);
        HttpResponse response = httpClient.execute(options);
        assertEquals(200, response.code());
    }

    @Test
    void givenValidOptionsRequestShouldSetAccessControlHeaders() throws HttpClientException {
        final OptionsMethod options = new OptionsMethod(BASE_URL, false);
        HttpResponse response = httpClient.execute(options);
        assertEquals(Collections.singletonList("GET, POST, HEAD, OPTIONS"), response.headers().get(ALLOW_METHODS_HEADER));
        assertEquals(Collections.singletonList("*"), response.headers().get(ALLOW_ORIGIN_HEADER));
    }

    @Test
    void givenBadAddProductRequestWhenJsonSyntaxExceptionIsThrownThenReturn400() throws HttpClientException {
        final PostMethod post = new PostMethod(BASE_URL + "cart/" + CART_ID.toString(), "bad-payload", false);
        server.whenNextCommandThrow(new RuntimeException("Something bad happened."));
        HttpResponse response = httpClient.execute(post);
        assertEquals(400, response.code());
    }

    @Test
    void givenCreateCartRequestWhenRuntimeExceptionIsThrownThenReturn500() throws HttpClientException {
        final PostMethod post = new PostMethod(BASE_URL + "cart/create", "", false);
        server.whenNextCommandThrow(new RuntimeException("Something bad happened."));
        HttpResponse response = httpClient.execute(post);
        assertEquals(500, response.code());
    }
}