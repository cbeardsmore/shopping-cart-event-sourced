package com.cbeardsmore.scart.rmp.utils;

import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.EventType;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;
import com.cbeardsmore.scart.dataaccess.EventEnvelope;

import java.util.UUID;

public class EventWrapper {

    private int position;
    private UUID cartId;

    public EventWrapper(UUID cartId) {
        this.position = 0;
        this.cartId = cartId;
    }

    public EventEnvelope wrap(CartCreatedEvent event) {
        position++;
        return new EventEnvelope(cartId, EventType.CART_CREATED.name(), position, event);
    }

    public EventEnvelope wrap(ProductAddedEvent event) {
        position++;
        return new EventEnvelope(cartId, EventType.PRODUCT_ADDED.name(), position, event);
    }

    public EventEnvelope wrap(ProductRemovedEvent event) {
        position++;
        return new EventEnvelope(cartId, EventType.PRODUCT_REMOVED.name(), position, event);
    }
}
