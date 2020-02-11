package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;

public class EventEnvelope {

    private final String streamId;
    private final String type;
    private final long position;
    private final Event event;

    public EventEnvelope(String streamId, String type, long position, Event event) {
        this.streamId = streamId;
        this.type = type;
        this.position = position;
        this.event = event;
    }

    public String getStreamId() {
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
