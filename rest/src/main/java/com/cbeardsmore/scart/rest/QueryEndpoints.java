package com.cbeardsmore.scart.rest;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;

import static spark.Spark.*;

public class QueryEndpoints {

    private static final String JSON_CONTENT = "application/json";

    private final Gson gson;

    public QueryEndpoints() {
        this.gson = new Gson();
    }

    public void setupEndpoints() {
        get("/carts/total", JSON_CONTENT, this::getTotalCartsPrice, gson::toJson);
        get("/cart/:cartId", JSON_CONTENT, this::getCartPrice, gson::toJson);
        get("/product/popular", JSON_CONTENT, this::getPopularProducts, gson::toJson);
    }

    private BigDecimal getCartPrice(Request request, Response response) {
        return BigDecimal.ZERO;
    }

    private BigDecimal getTotalCartsPrice(Request request, Response response) {
        return BigDecimal.ZERO;
    }

    private String getPopularProducts(Request request, Response response) {
        return null;
    }
}
