package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.model.Receipt;
import com.cbeardsmore.scart.rest.request.AddProductRequest;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static spark.Spark.*;

public class CommandEndpoints {

    private static final String JSON_CONTENT = "application/json";
    private static final String PATH_PARAM_CART_ID = "cartId";
    private static final String PATH_PARAM_PRODUCT_ID = "productId";

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandEndpoints.class);

    private final CommandHandler commandHandler;
    private final Gson gson;

    public CommandEndpoints(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.gson = new Gson();
    }

    public void setupEndpoints() {
        path("/cart", () -> {
            post("/create", JSON_CONTENT, this::createCart, gson::toJson);
            post("/:cartId", JSON_CONTENT, this::addItem, gson::toJson);
            post("/:cartId/checkout", JSON_CONTENT, this::checkout, gson::toJson);
            delete("/:cartId/product/:productId", JSON_CONTENT, this::removeItem, gson::toJson);
        });
    }

    private Receipt createCart(Request request, Response response) {
        return commandHandler.handle(new CreateCartCommand());
    }

    private Receipt addItem(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        final var payload = gson.fromJson(request.body(), AddProductRequest.class);
        final var command = payload.toCommand(cartId);
        LOGGER.info("Item Added: {}", command);
        return commandHandler.handle(command);
    }

    private Receipt checkout(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        final var command = new CheckoutCommand(cartId);
        LOGGER.info("Checkout: {}", command);
        return commandHandler.handle(command);
    }

    private Receipt removeItem(Request request, Response response) {
        final var cartId = UUID.fromString(request.params(PATH_PARAM_CART_ID));
        final var productId = UUID.fromString(request.params(PATH_PARAM_PRODUCT_ID));
        final var command = new RemoveProductCommand(cartId, productId);
        LOGGER.info("Item Removed: {}", command);
        return commandHandler.handle(command);
    }
}
