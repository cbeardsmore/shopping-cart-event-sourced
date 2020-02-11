package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.persistence.EventEnvelope;
import com.cbeardsmore.scart.rmp.persistence.PostgresRepository;
import com.cbeardsmore.scart.rmp.projection.CartPriceProjection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ReadModelPopulator {

    private final Bookmark bookmark;
    private final PostgresRepository repository;
    private final Map<String, Consumer<EventEnvelope>> handlers;

    public ReadModelPopulator(Bookmark bookmark, PostgresRepository repository) {
        this.bookmark = bookmark;
        this.repository = repository;
        handlers = new HashMap<>();

        handlers.put("CART_CREATED", this::cartCreated);
        handlers.put("PRODUCT_ADDED", this::productAdded);
        handlers.put("PRODUCT_REMOVED", this::productRemoved);
    }

    public void dispatch(EventEnvelope envelope) {
        handlers.getOrDefault(envelope.getType(), t -> {}).accept(envelope);
    }

    private void cartCreated(EventEnvelope envelope) {
        final var cartId = UUID.fromString(envelope.getStreamId());
        final var projection = new CartPriceProjection(cartId, 0L);
        repository.put(projection, envelope.getPosition());
        bookmark.put(envelope.getPosition());
    }

    private void productAdded(EventEnvelope envelope) {
        final var cartId = envelope.getStreamId();
        //TODO: increase cartPrice, totalPrice and popularProducts
        bookmark.put(envelope.getPosition());
    }

    private void productRemoved(EventEnvelope envelope) {
        final var cartId = envelope.getStreamId();
        //TODO: decrease cartPrice, totalPrice and popularProducts
        bookmark.put(envelope.getPosition());
    }
}
