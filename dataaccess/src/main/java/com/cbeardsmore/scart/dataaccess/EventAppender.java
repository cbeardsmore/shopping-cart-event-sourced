package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventType;
import com.google.gson.Gson;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.List;

final class EventAppender {

    private static final String INSERT_SQL =
        "INSERT INTO event_store.events (stream_type, stream_id, version, event_type, payload) VALUES (?, ?, ?, ?, ?)";

    private static final Gson GSON = new Gson();

    private final String connectionUrl;

    EventAppender(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void append(String streamType, String streamId, int expectedVersion, List<Event> events) throws SQLException {
        int version = expectedVersion + 1;

        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(false);
            for (var event : events) {
                final PreparedStatement statement = conn.prepareStatement(INSERT_SQL);
                final PGobject payloadJson = toPgJson(event);
                statement.setString(1, streamType);
                statement.setString(2, streamId);
                statement.setInt(3, version);
                statement.setString(4, EventType.fromClass(event.getClass()).name());
                statement.setObject(5, payloadJson);
                statement.executeUpdate();
                version++;
            }
            conn.commit();
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