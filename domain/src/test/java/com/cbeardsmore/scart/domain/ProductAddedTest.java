package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.util.TestContext;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class ProductAddedTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final String NAME = "Samsung TV";
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final int QUANTITY = 1;

    private final TestContext context = TestContext.init();

    @Test
    void givenCartCreatedEventWhenAddProductCommandShouldRaiseProductAddedEvent() {
        final var addProductCommand = new AddProductCommand(CART_ID, PRODUCT_ID, NAME, PRICE, QUANTITY);
        context.givenEvent(new CartCreatedEvent());
        context.whenCommand(addProductCommand);

        final var event = new ProductAddedEvent(PRODUCT_ID, NAME, PRICE, QUANTITY);
        context.thenAssertEventAndPayload(event);
        context.thenAssertNoOtherEventsAreRaised();
    }
}
