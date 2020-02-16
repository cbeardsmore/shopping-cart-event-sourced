package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventType;
import com.google.gson.Gson;
import org.postgresql.util.PGobject;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

final class EventAppender {

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
                final PreparedStatement statement = conn.prepareStatement(INSERT_SQL);
                final PGobject payloadJson = toPgJson(event);
                statement.setObject(1, streamId);
                statement.setInt(2, version);
                statement.setString(3, EventType.fromClass(event.getClass()).name());
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
        final String json = GSON.toJson(source);
        final PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(json);
        return jsonObject;
    }
}