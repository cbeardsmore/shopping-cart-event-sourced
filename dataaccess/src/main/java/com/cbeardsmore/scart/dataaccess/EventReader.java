package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.event.Event;
import com.google.gson.Gson;

import java.sql.*;
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

    final List<? extends Event> readStreamInstance(String streamType, String streamId) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            try (PreparedStatement statement = conn.prepareStatement(SELECT_SQL_BY_STREAM_ID)) {
                statement.setString(1, streamType);
                statement.setString(2, streamId);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapToEvents(rs);
                }
            }
        }
    }

    private List<? extends Event> mapToEvents(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        final List<Event> output = new ArrayList<>();
        while (resultSet.next()) {
            final String eventType = resultSet.getString(1);
            final String payload = resultSet.getString(2);
            final Class cls = Class.forName(eventType);
            final Event event = (Event)GSON.fromJson(payload, cls);
            output.add(event);
        }
        return output;
    }
}