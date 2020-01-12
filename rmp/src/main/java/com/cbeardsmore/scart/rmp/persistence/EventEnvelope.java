package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;

public class EventEnvelope {

    private final long position;
    private final Event event;

    public EventEnvelope(long position, Event event) {
        this.position = position;
        this.event = event;
    }

    public long getPosition() {
        return position;
    }

    public Event getEvent() {
        return event;
    }
}
