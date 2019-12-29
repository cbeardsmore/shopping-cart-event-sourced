package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.util.TestContext;
import org.junit.jupiter.api.Test;

class CartCreatedTest {

    private final TestContext context = TestContext.init();

    @Test
    void givenNoEventsWhenCreateCartCommandShouldRaiseCartCreatedEvent() {
        final var command = new CreateCartCommand();
        context.givenNoEvents();
        context.whenCommand(command);

        final var event = new CartCreatedEvent();
        context.thenAssertEventAndPayload(event);
        context.thenAssertNoOtherEventsAreRaised();
    }
}
