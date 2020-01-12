package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.domain.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventIterator implements Iterator<Event> {
    private final PostgresEventStore eventStore;
    private List<? extends Event> page;
    private long position;
    private int index;

    public EventIterator(PostgresEventStore eventStore, long position) {
        this.eventStore = eventStore;
        this.page = new ArrayList<>();
        this.position = position;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Event next() {
        if (index == page.size()) {
            loadPage();
            index = 0;
        }
        return page.get(index++);
    }

    private void loadPage() {
        page = eventStore.readPage(position);
        position += page.size();
        if (page.isEmpty()) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
