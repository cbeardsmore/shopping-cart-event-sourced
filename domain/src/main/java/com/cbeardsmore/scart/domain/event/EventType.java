package com.cbeardsmore.scart.domain.event;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
    CART_CREATED(CartCreatedEvent.class),
    PRODUCT_ADDED(ProductAddedEvent.class),
    PRODUCT_REMOVED(ProductRemovedEvent.class),
    CHECKOUT_COMPLETED(CheckoutCompletedEvent.class);

    private final Class clazz;
    private static final Map<Class, EventType> lookup = new HashMap<>();

    static {
        for(EventType type : EventType.values())
            lookup.put(type.getClazz(), type);
    }

    EventType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public static EventType fromClass(Class cls) {
        return lookup.get(cls);
    }
}