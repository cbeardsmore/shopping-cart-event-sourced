package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.persistence.EventEnvelope;
import com.cbeardsmore.scart.rmp.persistence.EventReader;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

public class App {

    private static final String POSTGRES_USERNAME = System.getenv("POSTGRES_USERNAME");
    private static final String POSTGRES_PASSWORD = System.getenv("POSTGRES_PASSWORD");
    private static final String POSTGRES_DATABASE = System.getenv("POSTGRES_DATABASE");

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting shopping-cart-rmp...");
        final var dataSource = createDataSource();
        final var connectionUrl = getPostgresConnectionString();
        final var bookmark = new Bookmark(dataSource);
        final var populator = new ReadModelPopulator(bookmark);
        final var reader = new EventReader(connectionUrl);

        try {
            final var position = bookmark.get();
            LOGGER.info("Starting Bookmark Positions: {}", position);

            while(true) {
                List<EventEnvelope> envelopes = reader.readAll(position);
                envelopes.forEach(populator::dispatch);
                Thread.sleep(500);
            }
        } catch (final Exception ex) {
            LOGGER.error("A fatal error occurred and the Shopping Cart RMP is shutting down.", ex);
            System.exit(1);
        }
    }

    private static String getPostgresConnectionString() {
        return String.format("jdbc:postgresql://postgres_db:5432/%s?user=%s&password=%s",
                POSTGRES_DATABASE, POSTGRES_USERNAME, POSTGRES_PASSWORD
        );
    }

    private static DataSource createDataSource() {
        final var dataSource = new PGSimpleDataSource();
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("shopping_cart");
        dataSource.setUser("postgres");
        dataSource.setServerName("postgres_db");
        dataSource.setPassword("supersecret");
        return dataSource;
    }
}
