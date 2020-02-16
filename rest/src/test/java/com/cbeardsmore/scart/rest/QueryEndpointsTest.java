package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.domain.model.PopularProduct;
import com.cbeardsmore.scart.rest.response.PopularProductsResponse;
import com.cbeardsmore.scart.rest.response.TotalPriceResponse;
import com.cbeardsmore.scart.rest.utils.ReadModelStoreStub;
import com.cbeardsmore.scart.rest.utils.TestServer;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClient;
import com.despegar.http.client.HttpClientException;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryEndpointsTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final String BASE_URL = "http://localhost:4567/";
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
    void givenGetCartPriceRequestThenCartPriceSsReturned() throws HttpClientException {
        final var expected = new TotalPriceResponse(BigDecimal.ZERO);
        final var get = new GetMethod( BASE_URL + "/cart/" + CART_ID, false);
        final var response = httpClient.execute(get);
        final var body = new String(response.body());
        assertEquals(200, response.code());
        assertEquals(expected, GSON.fromJson(body, TotalPriceResponse.class));
    }

    @Test
    void givenGetTotalCartsPriceRequestThenTotalCartsPriceSsReturned() throws HttpClientException {
        final var expected = new TotalPriceResponse(BigDecimal.ZERO);
        final var get = new GetMethod( BASE_URL + "/carts/total", false);
        final var response = httpClient.execute(get);
        final var body = new String(response.body());
        assertEquals(200, response.code());
        assertEquals(expected, GSON.fromJson(body, TotalPriceResponse.class));
    }

    @Test
    void givenGetPopularProductsRequestThenTop5PopularProductsAreReturned() throws HttpClientException {
        final var expected = createMockProductList();
        final var get = new GetMethod( BASE_URL + "/product/popular", false);
        final var response = httpClient.execute(get);
        final var body = new String(response.body());
        assertEquals(200, response.code());
        assertEquals(expected, GSON.fromJson(body, PopularProductsResponse.class));
    }

    private PopularProductsResponse createMockProductList() {
        final var product = new PopularProduct(ReadModelStoreStub.PRODUCT_ID, 1);
        final var productList = Collections.singletonList(product);
        return new PopularProductsResponse(productList);
    }
}