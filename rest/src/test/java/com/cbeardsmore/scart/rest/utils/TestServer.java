package com.cbeardsmore.scart.rest.utils;

import com.cbeardsmore.scart.domain.command.Command;
import com.cbeardsmore.scart.rest.CommandEndpoints;
import com.cbeardsmore.scart.rest.QueryEndpoints;
import com.cbeardsmore.scart.rest.Server;
import spark.servlet.SparkApplication;

import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

public class TestServer implements SparkApplication {

    private CommandHandlerStub commandHandler;

    public TestServer() {}

    @Override
    public void init() {
        commandHandler = new CommandHandlerStub();
        final var store = new ReadModelStoreStub();
        final var commandEndpoints = new CommandEndpoints(commandHandler);
        final var queryEndpoints = new QueryEndpoints(store);
        final var server = new Server(commandEndpoints, queryEndpoints);

        while (true) {
            try {
                server.serve(4567);
                awaitInitialization();
                return;
            } catch (IllegalStateException ignored) {
            }
        }
    }

    @Override
    public void destroy() {
        stop();
    }

    public Command getLastCommand() {
        return commandHandler.getLastCommand();
    }

    public void whenNextCommandThrow(RuntimeException e) {
        commandHandler.whenNextCommandThrow(e);
    }
}