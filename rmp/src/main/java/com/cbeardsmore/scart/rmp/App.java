package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting shopping-cart-rmp...");

        final var eventsDataSource = new PGSimpleDataSource();
        eventsDataSource.setPortNumber(5432);
        eventsDataSource.setDatabaseName("shopping_cart");
        eventsDataSource.setUser("postgres");
        eventsDataSource.setServerName("postgres_db");
        eventsDataSource.setPassword("supersecret");

        final var bookmark = new Bookmark(eventsDataSource);
        final var populator = new ReadModelPopulator();

        try {
            final var position = bookmark.get();
            LOGGER.info("Starting Bookmark Positions: {}", position);
            final var eventIterator = reader.readAll(position);
            eventIterator.forEach(populator::dispatch);
        } catch (final Exception ex) {
            LOGGER.error("A fatal error occurred and the Shopping Cart RMP is shutting down.", ex);
            System.exit(1);
        }
    }
}
