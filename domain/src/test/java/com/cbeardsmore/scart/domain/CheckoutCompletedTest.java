package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.CheckoutCompletedEvent;
import com.cbeardsmore.scart.domain.exception.DuplicateTransactionException;
import com.cbeardsmore.scart.domain.util.TestContext;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class CheckoutCompletedTest {

    private static final UUID CART_ID = UUID.randomUUID();

    private final TestContext context = TestContext.init();

    @Test
    void givenCartCreatedEventWhenCheckoutCommandShouldRaiseCheckoutCompletedEvent() {
        final var command = new CheckoutCommand(CART_ID);
        context.givenEvent(new CartCreatedEvent());
        context.whenCommand(command);

        final var event = new CheckoutCompletedEvent(BigDecimal.ZERO);
        context.thenAssertEventAndPayload(event);
        context.thenAssertNoOtherEventsAreRaised();
    }

    @Test
    void givenNoEventsWhenCheckoutCommandShouldThrowIllegalStateException() {
        final var command = new CheckoutCommand(CART_ID);
        context.givenNoEvents();
        context.whenCommand(command);

        context.thenAssertException(IllegalStateException.class);
        context.thenAssertNoOtherEventsAreRaised();
    }

    @Test
    void givenCheckoutCompletedEventWhenCheckoutCommandShouldThrowDuplicateTransactionException() {
        final var command = new CheckoutCommand(CART_ID);
        context.givenEvent(new CheckoutCompletedEvent(BigDecimal.TEN));
        context.whenCommand(command);

        context.thenAssertException(DuplicateTransactionException.class);
        context.thenAssertNoOtherEventsAreRaised();
    }
}
