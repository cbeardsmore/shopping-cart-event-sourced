package com.cbeardsmore.scart;

import com.cbeardsmore.scart.dataaccess.PostgresRepository;
import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.rest.CommandEndpoints;
import com.cbeardsmore.scart.rest.QueryEndpoints;
import com.cbeardsmore.scart.rest.Server;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class App {

    private static final int SPARK_PORT = 8080;
    private static final String POSTGRES_USERNAME = System.getenv("POSTGRES_USERNAME");
    private static final String POSTGRES_PASSWORD = System.getenv("POSTGRES_PASSWORD");
    private static final String POSTGRES_DATABASE = System.getenv("POSTGRES_DATABASE");

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Shopping Cart Server...");
        final var dataSource = getPostgresDataSource();
        final var repository = new PostgresRepository(dataSource);
        final var readModelStore = new ReadModelStore(dataSource);
        final var commandHandler = new CommandHandler(repository);
        final var commandEndpoints = new CommandEndpoints(commandHandler);
        final var queryEndpoints = new QueryEndpoints(readModelStore);
        final var server = new Server(commandEndpoints, queryEndpoints);
        server.serve(SPARK_PORT);
    }

    private static DataSource getPostgresDataSource() {
        final var dataSource = new PGSimpleDataSource();
        dataSource.setServerName("postgres_db");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName(POSTGRES_DATABASE);
        dataSource.setUser(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        return dataSource;
    }
}