package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventType;
import com.google.gson.Gson;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class EventReader {
    private static final String SELECT_SQL_BY_STREAM_ID =
            "SELECT event_type, payload FROM event_store.events WHERE stream_type = (?) AND stream_id = (?) ORDER BY version ASC";

    private static final Gson GSON = new Gson();
    private final String connectionUrl;

    public EventReader(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    final List<? extends Event> readStreamInstance(String streamType, String streamId) {
        try (final var conn = DriverManager.getConnection(connectionUrl)) {
            try (final var statement = conn.prepareStatement(SELECT_SQL_BY_STREAM_ID)) {
                statement.setString(1, streamType);
                statement.setString(2, streamId);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapToEvents(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<? extends Event> mapToEvents(ResultSet resultSet) throws SQLException {
        final List<Event> output = new ArrayList<>();
        while (resultSet.next()) {
            final String eventType = resultSet.getString(1);
            final String payload = resultSet.getString(2);
            final Class cls = EventType.valueOf(eventType).getClazz();
            final Event event = (Event) GSON.fromJson(payload, cls);
            output.add(event);
        }
        return output;
    }
}