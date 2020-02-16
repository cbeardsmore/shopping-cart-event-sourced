package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;

import java.util.UUID;

public class EventEnvelope {

    private final UUID streamId;
    private final String type;
    private final long position;
    private final Event event;

    public EventEnvelope(UUID streamId, String type, long position, Event event) {
        this.streamId = streamId;
        this.type = type;
        this.position = position;
        this.event = event;
    }

    public UUID getStreamId() {
        return streamId;
    }

    public String getType() {
        return type;
    }

    public long getPosition() {
        return position;
    }

    public Event getEvent() {
        return event;
    }
}
