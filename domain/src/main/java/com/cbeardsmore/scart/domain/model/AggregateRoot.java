package com.cbeardsmore.scart.domain.model;

import com.cbeardsmore.scart.domain.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

abstract class AggregateRoot {

    private final UUID id;
    private final Map<Class, Consumer> handlerRegistry = new HashMap<>();
    private final List<Event> events = new ArrayList<>();
    private int version;

    protected AggregateRoot(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getVersion() {
        return version;
    }

    protected <T> void registerHandler(Class<T> payloadType, Consumer<T> handler) {
        handlerRegistry.put(payloadType, handler);
    }

    protected  void addEvent(Event event) {
        events.add(event);
        apply(event);
    }

    public void apply(Event event) {
        final var handler = handlerRegistry.get(event.getClass());
        handler.accept(event);
        version++;
    }
}