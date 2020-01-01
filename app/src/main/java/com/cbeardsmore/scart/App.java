package com.cbeardsmore.scart;

import com.cbeardsmore.scart.dataaccess.PostgresRepository;
import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.rest.CommandEndpoints;
import com.cbeardsmore.scart.rest.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Shopping Cart Server...");
        final var repository = new PostgresRepository("jdbc:postgresql://postgres_db:5432/shopping_cart?user=postgres&password=supersecret");
        final var commandHandler = new CommandHandler(repository);
        final var commandEndpoints = new CommandEndpoints(commandHandler);
        final var server = new Server(commandEndpoints);
        server.serve(8080);
    }
}