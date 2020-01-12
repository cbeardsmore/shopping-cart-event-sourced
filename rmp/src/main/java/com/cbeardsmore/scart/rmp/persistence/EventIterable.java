package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;

import java.util.Iterator;

public class EventIterable implements Iterable<Event> {

    private final PostgresEventStore eventStore;
    private final long position;

    public EventIterable(PostgresEventStore eventStore, long position) {
        this.eventStore = eventStore;
        this.position = position;
    }

    @Override
    public Iterator<Event> iterator() {
        return new EventIterator(eventStore, position);
    }
}
