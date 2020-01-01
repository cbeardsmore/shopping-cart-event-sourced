package com.cbeardsmore.scart.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final String JSON_CONTENT = "application/json";

    private final CommandEndpoints commandEndpoints;

    public Server(CommandEndpoints commandEndpoints) {
        this.commandEndpoints = commandEndpoints;
    }

    public void serve(int port) {
        port(port);

        before("/*", this::setAccessControlHeaders);
        options("/*", (request, response) -> 200);
        get("/ping", JSON_CONTENT, ((request, response) -> 200));

        commandEndpoints.setupEndpoints();

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
}
