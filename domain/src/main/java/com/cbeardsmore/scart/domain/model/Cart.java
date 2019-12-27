package com.cbeardsmore.scart.domain.model;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.CheckoutCompletedEvent;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;

import java.util.UUID;

public class Cart extends AggregateRoot {

    public Cart(UUID aggregateId) {
        super(aggregateId);
        registerHandler(CartCreatedEvent.class, this::handle);
        registerHandler(ProductAddedEvent.class, this::handle);
        registerHandler(ProductRemovedEvent.class, this::handle);
        registerHandler(CheckoutCompletedEvent.class, this::handle);
    }

    private void createCart(CreateCartCommand command) {
        addEvent(new CartCreatedEvent());
    }

    private void addProduct(AddProductCommand command) {
        addEvent(new ProductAddedEvent());
    }

    private void removeProduct(RemoveProductCommand command) {
        addEvent(new ProductRemovedEvent());
    }

    private void checkout(CheckoutCommand command) {
        addEvent(new CheckoutCompletedEvent());
    }

    private void handle(CartCreatedEvent event) {}
    private void handle(ProductAddedEvent event) {}
    private void handle(ProductRemovedEvent event) {}
    private void handle(CheckoutCompletedEvent event) {}
}
