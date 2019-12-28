package com.cbeardsmore.scart.domain.commands;

import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.exception.CommandValidationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RemoveProductCommandTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();

    @Test
    void givenNullCartIdShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new RemoveProductCommand(null, PRODUCT_ID));
    }

    @Test
    void givenNullProductIdShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new RemoveProductCommand(CART_ID, null));
    }
}
