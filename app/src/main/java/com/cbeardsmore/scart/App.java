package com.cbeardsmore.scart;

import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.rest.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Shopping Cart Server...");
        final var commandHandler = new CommandHandler();
        final var server = new Server(commandHandler);
        server.serve(8080);
    }
}
