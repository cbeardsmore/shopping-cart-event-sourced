package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventType;
import com.google.gson.Gson;
import org.postgresql.util.PGobject;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class EventAppender {

    private static final String INSERT_SQL =
            "INSERT INTO event_store.events (stream_id, version, event_type, payload) VALUES (?, ?, ?, ?)";

    private static final Gson GSON = new Gson();

    private final DataSource dataSource;

    public EventAppender(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void append(UUID streamId, int expectedVersion, List<Event> events) {
        int version = expectedVersion + 1;

        try (final var conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            for (var event : events) {
                final var statement = conn.prepareStatement(INSERT_SQL);
                final var payloadJson = toPgJson(event);
                final var eventType = EventType.fromClass(event.getClass()).name();
                statement.setObject(1, streamId);
                statement.setInt(2, version);
                statement.setString(3, eventType);
                statement.setObject(4, payloadJson);
                statement.executeUpdate();
                version++;
            }
            conn.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private PGobject toPgJson(Object source) throws SQLException {
        final var jsonObject = new PGobject();
        jsonObject.setValue(GSON.toJson(source));
        jsonObject.setType("json");
        return jsonObject;
    }
}