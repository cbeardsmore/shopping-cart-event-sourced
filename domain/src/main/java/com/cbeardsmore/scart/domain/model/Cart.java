package com.cbeardsmore.scart.domain.model;

import java.util.UUID;

public class Cart extends AggregateRoot {

    private final UUID id;

    public Cart(UUID id) {
        this.id = id;
    }
}
