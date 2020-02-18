package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventType;
import com.google.gson.Gson;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventReader {

    private static final String SELECT_ALL =
            "SELECT stream_id, event_type, payload, position FROM event_store.events WHERE position > ? LIMIT 20";

    private static final String SELECT_STREAM_ID =
            "SELECT event_type, payload FROM event_store.events WHERE stream_id = (?) ORDER BY version ASC";

    private static final Gson GSON = new Gson();
    private final DataSource dataSource;

    public EventReader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<EventEnvelope> readAll(long fromPosition) throws SQLException, ClassNotFoundException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(SELECT_ALL)) {
                statement.setLong(1, fromPosition);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapToEventEnvelopes(rs);
                }
            }
        }
    }

    final List<? extends Event> readStreamInstance(UUID streamId) {
        try (final var conn = dataSource.getConnection()) {
            try (final var statement = conn.prepareStatement(SELECT_STREAM_ID)) {
                statement.setObject(1, streamId);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapToEvents(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<EventEnvelope> mapToEventEnvelopes(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        final List<EventEnvelope> output = new ArrayList<>();
        while (resultSet.next()) {
            final UUID streamId = resultSet.getObject(1, UUID.class);
            final String eventType = resultSet.getString(2);
            final String payload = resultSet.getString(3);
            final long position = resultSet.getLong(4);
            final Class cls = EventType.valueOf(eventType).getClazz();
            final Event event = (Event)GSON.fromJson(payload, cls);
            output.add(new EventEnvelope(streamId, eventType, position, event));
        }
        return output;
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