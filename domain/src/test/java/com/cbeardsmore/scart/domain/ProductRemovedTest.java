package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.CheckoutCompletedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;
import com.cbeardsmore.scart.domain.util.TestContext;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class ProductRemovedTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();

    private final TestContext context = TestContext.init();

    @Test
    void givenCartCreatedEventWhenRemoveProductCommandShouldRaiseProductRemovedEvent() {
        final var command = new RemoveProductCommand(CART_ID, PRODUCT_ID);
        context.givenEvent(new CartCreatedEvent());
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
}
