package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.dataaccess.EventReader;
import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.dataaccess.EventEnvelope;
import com.cbeardsmore.scart.rmp.persistence.PostgresRepository;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

public class App {

    private static final String POSTGRES_SERVER = "postgres_db";
    private static final String POSTGRES_USERNAME = System.getenv("POSTGRES_USERNAME");
    private static final String POSTGRES_PASSWORD = System.getenv("POSTGRES_PASSWORD");
    private static final String POSTGRES_DATABASE = System.getenv("POSTGRES_DATABASE");

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting shopping-cart-rmp...");
        final var dataSource = getPostgresDataSource();
        final var bookmark = new Bookmark(dataSource);
        final var repository = new PostgresRepository(bookmark, dataSource);
        final var populator = new ReadModelPopulator(repository);
        final var reader = new EventReader(dataSource);

        try {
            long position = bookmark.get();
            LOGGER.info("Starting Bookmark Positions: {}", position);

            while(true) {
                position = bookmark.get();
                List<EventEnvelope> envelopes = reader.readAll(position);
                envelopes.forEach(populator::dispatch);
                Thread.sleep(500);
            }
        } catch (final Exception ex) {
            LOGGER.error("Exception was caught and Shopping Cart RMP is going down...", ex);
            System.exit(1);
        }
    }

    private static DataSource getPostgresDataSource() {
        final var dataSource = new PGSimpleDataSource();
        dataSource.setPortNumber(5432);
        dataSource.setServerName(POSTGRES_SERVER);
        dataSource.setDatabaseName(POSTGRES_DATABASE);
        dataSource.setUser(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        return dataSource;
    }
}
