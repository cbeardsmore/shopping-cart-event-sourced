package com.cbeardsmore.scart.domain.commands;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.exception.CommandValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AddProductCommandTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final String NAME = "Samsung TV";
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final int QUANTITY = 1;

    @Test
    void givenNullCartIdShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new AddProductCommand(null, PRODUCT_ID, NAME, PRICE, QUANTITY));
    }

    @Test
    void givenNullProductIdShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new AddProductCommand(CART_ID, null, NAME, PRICE, QUANTITY));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void givenInvalidNameShouldThrowCommandValidationException(String name) {
        assertThrows(CommandValidationException.class,
                () -> new AddProductCommand(CART_ID, PRODUCT_ID, name, PRICE, QUANTITY));
    }

    @Test
    void givenNullPriceShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new AddProductCommand(CART_ID, PRODUCT_ID, NAME, null, QUANTITY));
    }

    @Test
    void givenNegativePriceShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new AddProductCommand(CART_ID, PRODUCT_ID, NAME, BigDecimal.valueOf(-10L), QUANTITY));
    }

    @Test
    void givenNegativeQuantityShouldThrowCommandValidationException() {
        assertThrows(CommandValidationException.class,
                () -> new AddProductCommand(CART_ID, PRODUCT_ID, NAME, PRICE, -1));
    }
}
