package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.Repository;
import com.cbeardsmore.scart.domain.model.Cart;

import javax.sql.DataSource;
import java.util.UUID;

public class PostgresRepository implements Repository<Cart> {
    private final EventAppender writer;
    private final EventReader reader;

    public PostgresRepository(DataSource dataSource) {
        this.reader = new EventReader(dataSource);
        this.writer = new EventAppender(dataSource);
    }

    @Override
    public Cart load(UUID id) {
        final var currentState = reader.readStreamInstance(id);
        final var cart = new Cart(id);
        for (var event : currentState)
            cart.apply(event);
        return cart;
    }

    @Override
    public void save(Cart cart) {
        final var events = cart.getEvents();
        final var expectedVersion = cart.getVersion() - events.size();
        writer.append(cart.getId(), expectedVersion, events);
    }
}
