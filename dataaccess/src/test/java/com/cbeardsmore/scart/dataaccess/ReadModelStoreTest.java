package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.dataaccess.utils.PostgresDatabase;
import com.cbeardsmore.scart.domain.model.PopularProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadModelStoreTest {

    private static final UUID CART_ID = UUID.fromString("3d88e4ef-2b67-4e6f-8108-06302823c16d");
    private static final UUID PRODUCT_ID = UUID.fromString("b2edee96-aa12-4061-a2da-930d10b42bd3");
    private static final BigDecimal PRICE = BigDecimal.TEN.setScale(2, RoundingMode.CEILING);
    private ReadModelStore store;

    @BeforeEach
    void beforeEach() throws IOException {
        final var dataSource = PostgresDatabase.provide();
        store = new ReadModelStore(dataSource);
    }

    @Test
    void givenCartPopulatedThenReturnValidTotalCartPrice() {
        final var cartPrice = store.getCartPrice(CART_ID);
        assertEquals(PRICE, cartPrice);
    }

    @Test
    void givenInvalidCartIdThenThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> store.getCartPrice(UUID.randomUUID()));
    }

    @Test
    void givenCartPopulatedThenReturnValidTotalPrice() {
        final var totalPrice = store.getTotalCartsPrice();
        assertEquals(PRICE, totalPrice);
    }

    @Test
    void givenCartPopulatedThenReturnValidPopularProducts() {
        final var popularProducts = store.getPopularProducts();
        final var expected = Collections.singletonList(new PopularProduct(PRODUCT_ID, 1));
        assertEquals(expected, popularProducts);
    }
}
