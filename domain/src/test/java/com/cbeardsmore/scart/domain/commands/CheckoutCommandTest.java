package com.cbeardsmore.scart.domain.commands;

import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.exception.CommandValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckoutCommandTest {
    @Test
    void givenNullCartIdShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new CheckoutCommand(null));
    }
}
