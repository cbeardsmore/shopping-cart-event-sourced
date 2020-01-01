package com.cbeardsmore.scart.rest;

import com.cbeardsmore.scart.domain.exception.CommandValidationException;
import com.cbeardsmore.scart.domain.exception.DuplicateTransactionException;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final String JSON_CONTENT = "application/json";

    private final CommandEndpoints commandEndpoints;
    private final QueryEndpoints queryEndpoints;

    public Server(CommandEndpoints commandEndpoints, QueryEndpoints queryEndpoints) {
        this.commandEndpoints = commandEndpoints;
        this.queryEndpoints = queryEndpoints;
    }

    public void serve(int port) {
        port(port);

        before("/*", this::setAccessControlHeaders);
        options("/*", (request, response) -> 200);
        get("/ping", JSON_CONTENT, ((request, response) -> 200));

        commandEndpoints.setupEndpoints();
        queryEndpoints.setupEndpoints();;

        exception(DuplicateTransactionException.class, (ex, req, res) -> handleDuplicateTransaction(ex, res));
        exception(IllegalStateException.class, (ex, req, res) -> handleBadRequest(ex, res));
        exception(IllegalArgumentException.class, (ex, req, res) -> handleBadRequest(ex, res));
        exception(JsonSyntaxException.class, (ex, req, res) -> handleBadRequest(ex, res));
        exception(CommandValidationException.class, (ex, req, res) -> handleBadRequest(ex, res));
        exception(RuntimeException.class, (ex, req, res) -> handleUnexpected(ex, res));
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

    private void handleDuplicateTransaction(DuplicateTransactionException ex, Response res) {
        LOGGER.error("DuplicateTransactionException caught by server: {}", ex.getMessage());
        res.body("Accepted");
        res.status(202);
    }

    private void handleBadRequest(RuntimeException ex, Response res) {
        LOGGER.error("{} caught by server: {}", ex.getClass(), ex.getMessage());
        res.body("Bad Request");
        res.status(400);
    }
}
