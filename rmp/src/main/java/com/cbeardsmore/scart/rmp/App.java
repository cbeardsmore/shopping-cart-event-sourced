package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.persistence.EventEnvelope;
import com.cbeardsmore.scart.rmp.persistence.EventReader;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
        final var populator = new ReadModelPopulator(bookmark);
        final var reader = new EventReader("jdbc:postgresql://postgres_db:5432/shopping_cart?user=postgres&password=supersecret");

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
}
