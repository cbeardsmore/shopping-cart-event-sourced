package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.rest.request.AddProductRequest;
import com.cbeardsmore.scart.rest.utils.TestServer;
import com.despegar.http.client.DeleteMethod;
import com.despegar.http.client.HttpClient;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartEndpointsTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final String NAME = "Samsung TV";
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final int QUANTITY = 2;

    private static final String BASE_URL = "http://localhost:4567/cart/";
    private static final Gson GSON = new Gson();

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
    void givenCreateCartRequestThenCreateCommandIsPassedToHandler() throws HttpClientException {
        final PostMethod post = new PostMethod(BASE_URL + "create", "", false);
        HttpResponse response = httpClient.execute(post);
        assertEquals(CreateCartCommand.class, server.getLastCommand().getClass());
        assertEquals(200, response.code());
    }

    @Test
    void givenAddProductRequestThenAddProductCommandIsPassedToHandler() throws HttpClientException {
        final var addProductRequest = new AddProductRequest(PRODUCT_ID, NAME, PRICE, QUANTITY);
        final PostMethod post = new PostMethod(BASE_URL + CART_ID.toString(), GSON.toJson(addProductRequest), false);
        HttpResponse response = httpClient.execute(post);

        final var expectedCommand = new AddProductCommand(CART_ID, PRODUCT_ID, NAME, PRICE, QUANTITY);
        final var actualCommand = server.getLastCommand();
        assertEquals(expectedCommand, actualCommand);
        assertEquals(200, response.code());
    }

    @Test
    void givenRemoveProductRequestThenRemoveProductCommandIsPassedToHandler() throws HttpClientException {
        final String FULL_URL = BASE_URL + CART_ID + "/product/" + PRODUCT_ID;
        final DeleteMethod delete = new DeleteMethod(FULL_URL, false);
        HttpResponse response = httpClient.execute(delete);

        final var expectedCommand = new RemoveProductCommand(CART_ID, PRODUCT_ID);
        final var actualCommand = server.getLastCommand();
        assertEquals(expectedCommand, actualCommand);
        assertEquals(200, response.code());
    }

    @Test
    void givenCheckoutRequestThenCheckoutCommandIsPassedToHandler() throws HttpClientException {
        final PostMethod post = new PostMethod(BASE_URL + CART_ID.toString() + "/checkout", "", false);
        HttpResponse response = httpClient.execute(post);

        final var expectedCommand = new CheckoutCommand(CART_ID);
        final var actualCommand = server.getLastCommand();
        assertEquals(expectedCommand, actualCommand);
        assertEquals(200, response.code());
    }
}