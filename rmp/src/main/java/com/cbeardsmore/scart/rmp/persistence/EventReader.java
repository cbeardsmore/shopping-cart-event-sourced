package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventType;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventReader {
    private static final String SELECT_ALL =
            "SELECT event_type, payload, position FROM event_store.events WHERE position > ? LIMIT 20";

    private static final Gson GSON = new Gson();
    private final String connectionUrl;

    public EventReader(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public List<EventEnvelope> readAll(long fromPosition) throws SQLException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            try (PreparedStatement statement = conn.prepareStatement(SELECT_ALL)) {
                statement.setLong(1, fromPosition);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapToEvents(rs);
                }
            }
        }
    }

    private List<EventEnvelope> mapToEvents(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        final List<EventEnvelope> output = new ArrayList<>();
        while (resultSet.next()) {
            final String eventType = resultSet.getString(1);
            final String payload = resultSet.getString(2);
            final long position = resultSet.getLong(3);
            final Class cls = EventType.valueOf(eventType).getClazz();
            final Event event = (Event)GSON.fromJson(payload, cls);
            output.add(new EventEnvelope(position, event));
        }
        return output;
    }
}