package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.CheckoutCompletedEvent;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;
import com.cbeardsmore.scart.domain.util.TestContext;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class ProductRemovedTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final String NAME = "Samsung TV";
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final int QUANTITY = 1;

    private final TestContext context = TestContext.init();

    @Test
    void givenCartCreatedandProductAddedEventWhenRemoveProductCommandShouldRaiseProductRemovedEvent() {
        final var command = new RemoveProductCommand(CART_ID, PRODUCT_ID);
        context.givenEvent(new CartCreatedEvent());
        context.givenEvent(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE, QUANTITY));
        context.whenCommand(command);

        final var event = new ProductRemovedEvent(PRODUCT_ID);
        context.thenAssertEventAndPayload(event);
        context.thenAssertNoOtherEventsAreRaised();
    }

    @Test
    void givenNoEventsWhenRemoveProductCommandShouldThrowIllegalStateException() {
        final var command = new RemoveProductCommand(CART_ID, PRODUCT_ID);
        context.givenNoEvents();
        context.whenCommand(command);

        context.thenAssertException(IllegalStateException.class);
        context.thenAssertNoOtherEventsAreRaised();
    }

    @Test
    void givenCheckoutCompletedEventWhenRemoveProductCommandShouldThrowIllegalStateException() {
        final var command = new RemoveProductCommand(CART_ID, PRODUCT_ID);
        context.givenEvent(new CheckoutCompletedEvent(BigDecimal.TEN));
        context.whenCommand(command);

        context.thenAssertException(IllegalStateException.class);
        context.thenAssertNoOtherEventsAreRaised();
    }

    @Test
    void givenCartCreatedEventWhenRemoveProductCommandShouldThrowIllegalArgumentException() {
        final var command = new RemoveProductCommand(CART_ID, PRODUCT_ID);
        context.givenEvent(new CartCreatedEvent());
        context.whenCommand(command);

        context.thenAssertException(IllegalArgumentException.class);
        context.thenAssertNoOtherEventsAreRaised();
    }
}
