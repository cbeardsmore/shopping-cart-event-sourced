package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.domain.event.EventType;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;
import com.cbeardsmore.scart.rmp.persistence.EventEnvelope;
import com.cbeardsmore.scart.rmp.persistence.PostgresRepository;
import com.cbeardsmore.scart.rmp.projection.CartPriceProjection;
import com.cbeardsmore.scart.rmp.projection.PopularProductsProjection;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ReadModelPopulator {

    private final PostgresRepository repository;
    private final Map<String, Consumer<EventEnvelope>> handlers;
    private final Map<UUID, BigDecimal> productPrices;

    public ReadModelPopulator(PostgresRepository repository) {
        this.repository = repository;
        handlers = new HashMap<>();
        productPrices = new HashMap<>();

        handlers.put(EventType.CART_CREATED.name(), this::cartCreated);
        handlers.put(EventType.PRODUCT_ADDED.name(), this::productAdded);
        handlers.put(EventType.PRODUCT_REMOVED.name(), this::productRemoved);
    }

    public void dispatch(EventEnvelope envelope) {
        handlers.getOrDefault(envelope.getType(), t -> {}).accept(envelope);
    }

    private void cartCreated(EventEnvelope envelope) {
        final var cartId = UUID.fromString(envelope.getStreamId());
        final var projection = new CartPriceProjection(cartId, BigDecimal.ZERO);
        repository.put(projection, envelope.getPosition());
    }

    private void productAdded(EventEnvelope envelope) {
        final var event = (ProductAddedEvent)envelope.getEvent();
        final var cartId = UUID.fromString(envelope.getStreamId());
        final var quantity = event.getQuantity();
        final var totalPrice = event.getPrice().multiply(BigDecimal.valueOf(quantity));
        final var cartProjection = new CartPriceProjection(cartId, totalPrice);
        final var productProjection = new PopularProductsProjection(event.getProductId(), quantity);
        productPrices.put(event.getProductId(), event.getPrice());
        repository.put(cartProjection, productProjection, envelope.getPosition());
    }

    private void productRemoved(EventEnvelope envelope) {
        final var event = (ProductRemovedEvent)envelope.getEvent();
        final var cartId = UUID.fromString(envelope.getStreamId());
        final var price = productPrices.get(event.getProductId()).negate();
        final var cartProjection = new CartPriceProjection(cartId, price);
        final var productProjection = new PopularProductsProjection(event.getProductId(), -1);
        repository.put(cartProjection, productProjection, envelope.getPosition());
    }
}
