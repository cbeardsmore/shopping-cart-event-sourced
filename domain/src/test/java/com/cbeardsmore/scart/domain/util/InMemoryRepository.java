package com.cbeardsmore.scart.domain.util;

import com.cbeardsmore.scart.domain.Repository;
import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryRepository implements Repository<Cart> {

    private final List<Event> events;

    InMemoryRepository() {
        this.events = new ArrayList<>();
    }

    @Override
    public Cart load(UUID id) {
        final var cart = new Cart(id);
        for (var event: events) {
            cart.apply(event);
        }
        return cart;
    }

    @Override
    public void save(Cart cart) {
        events.addAll(cart.getEvents());
    }

    void append(Event event) {
        events.add(event);
    }

    Event read(int eventCursor) {
        return events.get(eventCursor);
    }

    List<Event> readAll(int eventCursor) {
        return events.subList(eventCursor, events.size());
    }
}