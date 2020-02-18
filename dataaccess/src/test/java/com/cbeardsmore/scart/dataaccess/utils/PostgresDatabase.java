package com.cbeardsmore.scart.dataaccess.utils;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import java.io.IOException;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class PostgresDatabase {

    private static DataSource dataSource;
    private static Flyway flyway;

    public static DataSource provide() throws IOException {
        if (dataSource == null) {
            dataSource = EmbeddedPostgres.start().getPostgresDatabase();
            flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.setLocations("/db");
            flyway.setSchemas("event_store", "shopping_cart");
        } else {
            flyway.clean();
        }
        flyway.migrate();
        return dataSource;
    }
}