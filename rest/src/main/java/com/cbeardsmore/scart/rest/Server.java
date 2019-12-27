package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.rest.request.AddProductRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static spark.Spark.*;


public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static final String JSON_CONTENT = "application/json";

    private static final String PATH_PARAM_CART_ID = "cartId";
    private static final String PATH_PARAM_PRODUCT_ID = "productId";

    private final Gson gson;
    private final CommandHandler commandHandler;

    public Server(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.gson = new Gson();
    }

    public void serve(int port) {
        port(port);

        before("/*", this::setAccessControlHeaders);
        options("/*", (request, response) -> 200);
        get("/ping", JSON_CONTENT, ((request, response) -> 200));

        path("/cart", () -> {
            post("/create", JSON_CONTENT, this::createCart, gson::toJson);
            post("/:cartId", JSON_CONTENT, this::addItem, gson::toJson);
            post("/:cartId/checkout", JSON_CONTENT, this::checkout, gson::toJson);
            delete("/:cartId/product/:productId", JSON_CONTENT, this::removeItem, gson::toJson);
        });

        exception(JsonSyntaxException.class, (ex, req, res) -> handleException(ex, res));
        exception(RuntimeException.class, (ex, req, res) -> handleUnexpected(ex, res));
    }

    private Object createCart(Request request, Response response) {
        return commandHandler.handle(new CreateCartCommand());
    }

    private Object addItem(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        final var payload = gson.fromJson(request.body(), AddProductRequest.class);
        final var command = payload.toCommand(cartId);
        return commandHandler.handle(command);
    }

    private Object checkout(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        return commandHandler.handle(new CheckoutCommand(cartId));
    }

    private Object removeItem(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        final var productId = UUID.fromString(request.params(PATH_PARAM_PRODUCT_ID));
        return commandHandler.handle(new RemoveProductCommand(cartId, productId));
    }

    private void handleException(JsonSyntaxException ex, Response res) {
        LOGGER.error("JsonSyntaxException caught by server: {}", ex.getMessage());
        res.body("Bad Request");
        res.status(400);
    }

    private void handleUnexpected(RuntimeException ex, Response res) {
        LOGGER.error("Unexpected Exception caught by server: ", ex);
        res.body("Internal Server Error");
        res.status(500);
    }

    private void setAccessControlHeaders(Request request, Response response) {
        response.header("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS");
        response.header("Access-Control-Allow-Origin", "*");
        response.type("application/json");
    }
}
