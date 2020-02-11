package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.persistence.EventEnvelope;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ReadModelPopulator {

    private final Bookmark bookmark;
    private final Map<String, Consumer<EventEnvelope>> handlers;

    public ReadModelPopulator(Bookmark bookmark) {
        this.bookmark = bookmark;
        handlers = new HashMap<>();

        handlers.put("CART_CREATED", this::cartCreated);
        handlers.put("PRODUCT_ADDED", this::productAdded);
        handlers.put("PRODUCT_REMOVED", this::productRemoved);
    }

    public void dispatch(EventEnvelope envelope) {
        handlers.getOrDefault(envelope.getType(), t -> {}).accept(envelope);
    }

    private void cartCreated(EventEnvelope envelope) {
        final var cartId = envelope.getStreamId();
        bookmark.put(envelope.getPosition());
    }

    private void productAdded(EventEnvelope envelope) {
        final var cartId = envelope.getStreamId();
        bookmark.put(envelope.getPosition());
    }

    private void productRemoved(EventEnvelope envelope) {
        final var cartId = envelope.getStreamId();
        bookmark.put(envelope.getPosition());
    }
}
