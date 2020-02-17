package com.cbeardsmore.scart.domain.model;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.CheckoutCompletedEvent;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;
import com.cbeardsmore.scart.domain.exception.DuplicateTransactionException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart extends AggregateRoot {

    private Map<UUID, BigDecimal> productPrices;
    private Map<UUID, Integer> productQuantities;
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
        if (this.getVersion() == 0)
            throw new IllegalStateException(String.format("Cart does not exist with id[%s]", command.getCartId()));
        if (orderCompleted)
            throw new IllegalStateException(String.format("Cart %s is already checked out", this.getId()));

        final var productAddedEvent = new ProductAddedEvent(
                command.getProductId(),
                command.getName(),
                command.getPrice()
        );
        addEvent(productAddedEvent);
    }

    public void removeProduct(RemoveProductCommand command) {
        if (this.getVersion() == 0)
            throw new IllegalStateException(String.format("Cart does not exist with id[%s]", command.getCartId()));
        if (orderCompleted)
            throw new IllegalStateException(String.format("Cart %s is already checked out", this.getId()));
        if (!productQuantities.containsKey(command.getProductId()))
            throw new IllegalArgumentException(String.format("Cart %s does not contain product %s", this.getId(), command.getProductId()));

        addEvent(new ProductRemovedEvent(command.getProductId()));
    }

    public void checkout(CheckoutCommand command) {
        if (this.getVersion() == 0)
            throw new IllegalStateException(String.format("Cart does not exist with id[%s]", command.getCartId()));
        if (orderCompleted)
            throw new DuplicateTransactionException(String.format("Cart %s is already checked out", this.getId()));

        addEvent(new CheckoutCompletedEvent(price));
    }

    private void handle(CartCreatedEvent event) {
        productPrices = new HashMap<>();
        productQuantities = new HashMap<>();
        price = BigDecimal.ZERO;
        orderCompleted = false;
    }

    private void handle(ProductAddedEvent event) {
        final var productId = event.getProductId();
        final var existingQuantity = productQuantities.getOrDefault(productId, 0);
        productPrices.put(productId, event.getPrice());
        productQuantities.put(productId, existingQuantity + 1);
        price = price.add(event.getPrice());
    }

    private void handle(ProductRemovedEvent event) {
        final var productId = event.getProductId();
        final var productPrice = productPrices.get(productId);
        final var newQuantity = productQuantities.get(productId) - 1;
        if (newQuantity == 0)
            productQuantities.remove(productId);
        else
            productQuantities.put(productId, newQuantity);
        price = price.subtract(productPrice);
    }

    private void handle(CheckoutCompletedEvent event) {
        orderCompleted = true;
    }
}
