package com.cbeardsmore.scart.domain.model;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.CheckoutCompletedEvent;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart extends AggregateRoot {

    private Map<UUID, BigDecimal> productPrices;
    private BigDecimal price;
    private boolean orderCompleted;

    public Cart(UUID aggregateId) {
        super(aggregateId);
        registerHandler(CartCreatedEvent.class, this::handle);
        registerHandler(ProductAddedEvent.class, this::handle);
        registerHandler(ProductRemovedEvent.class, this::handle);
        registerHandler(CheckoutCompletedEvent.class, this::handle);
    }

    public void createCart(CreateCartCommand command) {
        if (this.getVersion() > 0)
            throw new IllegalStateException(String.format("Cart already created with id[%s]", command.getId()));
        addEvent(new CartCreatedEvent());
    }

    public void addProduct(AddProductCommand command) {
        final var productAddedEvent = new ProductAddedEvent(
                command.getProductId(),
                command.getName(),
                command.getPrice(),
                command.getQuantity()
        );
        addEvent(productAddedEvent);
    }

    public void removeProduct(RemoveProductCommand command) {
        final var productRemovedEvent = new ProductRemovedEvent(command.getProductId());
        addEvent(productRemovedEvent);
    }

    public void checkout(CheckoutCommand command) {
        addEvent(new CheckoutCompletedEvent(price));
    }

    private void handle(CartCreatedEvent event) {
        productPrices = new HashMap<>();
        price = BigDecimal.ZERO;
        orderCompleted = false;
    }

    private void handle(ProductAddedEvent event) {
        productPrices.put(event.getProductId(), event.getPrice());
        final var totalPrice = event.getPrice().multiply(BigDecimal.valueOf(event.getQuantity()));
        price = price.add(totalPrice);
    }

    private void handle(ProductRemovedEvent event) {
        final var productPrice = productPrices.getOrDefault(event.getProductId(), BigDecimal.ZERO);
        price = price.subtract(productPrice);
    }

    private void handle(CheckoutCompletedEvent event) {
        orderCompleted = true;
    }
}
