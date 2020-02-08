package com.cbeardsmore.scart;

import com.cbeardsmore.scart.dataaccess.PostgresRepository;
import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.rest.CommandEndpoints;
import com.cbeardsmore.scart.rest.QueryEndpoints;
import com.cbeardsmore.scart.rest.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final int SPARK_PORT = 8080;
    private static final String POSTGRES_USERNAME = System.getenv("POSTGRES_USERNAME");
    private static final String POSTGRES_PASSWORD = System.getenv("POSTGRES_PASSWORD");
    private static final String POSTGRES_DATABASE = System.getenv("POSTGRES_DATABASE");

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Shopping Cart Server...");
        final var connectionUrl = getPostgresConnectionString();
        final var repository = new PostgresRepository(connectionUrl);
        final var readModelStore = new ReadModelStore(connectionUrl);
        final var commandHandler = new CommandHandler(repository);
        final var commandEndpoints = new CommandEndpoints(commandHandler);
        final var queryEndpoints = new QueryEndpoints(readModelStore);
        final var server = new Server(commandEndpoints, queryEndpoints);
        server.serve(SPARK_PORT);
    }

    private static String getPostgresConnectionString() {
        return String.format("jdbc:postgresql://postgres_db:5432/%s?user=%s&password=%s",
            POSTGRES_DATABASE, POSTGRES_USERNAME, POSTGRES_PASSWORD
        );
    }
}