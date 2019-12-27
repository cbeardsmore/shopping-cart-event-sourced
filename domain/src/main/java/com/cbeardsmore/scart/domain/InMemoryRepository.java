package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.event.EventEnvelope;
import com.cbeardsmore.scart.domain.model.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryRepository implements Repository<Cart> {

    private final Map<UUID, Cart> aggregates = new HashMap<>();
    private final List<EventEnvelope> events = new ArrayList<>();

    public void save(Cart cart) {

    }

    public Cart load(UUID id) {
        return null;
    }
}