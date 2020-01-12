package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.List;

public class PostgresEventStore {

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM event_store.events WHERE position > :position ORDER BY position ASC LIMIT :limit";

    private final Sql2o sql2o;

    public PostgresEventStore(DataSource dataSource) {
        this.sql2o = new Sql2o(dataSource);
    }

    public List<? extends Event> readPage(long fromPosition) {
        try (final var connection = sql2o.open()) {
            try (var query = connection.createQuery(SELECT_ALL_SQL)) {
                return query
                        .addParameter("position", fromPosition)
                        .addParameter("limit", 10)
                        .executeAndFetch(Event.class);
            }
        }
    }
}
