package com.cbeardsmore.scart.rmp.utils;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import java.io.IOException;
import org.flywaydb.core.Flyway;

public class PostgresDatabase {

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "supersecret";
    private static final String DB_NAME = "postgres";

    private static String connectionUrl;
    private static Flyway flyway;

    public static String provide() throws IOException {
        if (connectionUrl == null) {
            connectionUrl = EmbeddedPostgres.start().getJdbcUrl(USERNAME, DB_NAME);
            flyway = new Flyway();
            flyway.setDataSource(connectionUrl, USERNAME, PASSWORD);
            flyway.setLocations("/db");
            flyway.setSchemas("event_store", "shopping_cart");
        } else {
            flyway.clean();
        }
        flyway.migrate();
        return connectionUrl;
    }


}