package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.dataaccess.utils.PostgresDatabase;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostgresRepositoryTest {

    private static final UUID CART_ID = UUID.randomUUID();
    private PostgresRepository repository;

    @BeforeEach
    void beforeEach() throws IOException {
        final var dataSource = PostgresDatabase.provide();
        repository = new PostgresRepository(dataSource);
    }

    @Test
    void givenCartWithEventsLoadAfterSaveIsConsistent() {
        final var savedCart = new Cart(CART_ID);
        savedCart.createCart(new CreateCartCommand());
        repository.save(savedCart);
        final var loadedCart = repository.load(CART_ID);
        assertEquals(savedCart, loadedCart);
    }
}
