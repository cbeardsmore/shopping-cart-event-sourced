package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.rest.response.PopularProductsResponse;
import com.cbeardsmore.scart.rest.response.TotalPriceResponse;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static spark.Spark.*;

public class QueryEndpoints {

    private static final String JSON_CONTENT = "application/json";
    private static final String PATH_PARAM_CART_ID = "cartId";

    private final Gson gson;
    private final ReadModelStore store;

    public QueryEndpoints(ReadModelStore store) {
        this.gson = new Gson();
        this.store = store;
    }

    public void setupEndpoints() {
        get("/carts/total", JSON_CONTENT, this::getTotalCartsPrice, gson::toJson);
        get("/cart/:cartId", JSON_CONTENT, this::getCartPrice, gson::toJson);
        get("/product/popular", JSON_CONTENT, this::getPopularProducts, gson::toJson);
    }

    private TotalPriceResponse getCartPrice(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        return new TotalPriceResponse(store.getCartPrice(cartId));
    }

    private TotalPriceResponse getTotalCartsPrice(Request request, Response response) {
        return new TotalPriceResponse(store.getTotalCartsPrice());
    }

    private PopularProductsResponse getPopularProducts(Request request, Response response) {
        return new PopularProductsResponse(store.getPopularProducts());

    }
}
