package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.Repository;
import com.cbeardsmore.scart.domain.model.Cart;

import java.sql.SQLException;
import java.util.UUID;

public class PostgresRepository implements Repository<Cart> {
    private final EventAppender writer;
    private final EventReader reader;

    public PostgresRepository(String connectionUrl){
        this.reader = new EventReader(connectionUrl);
        this.writer = new EventAppender(connectionUrl);
    }

    @Override
    public Cart load(UUID id) {
        try {
            final var currentState = reader.readStreamInstance("Cart", id.toString());
            final var cart = new Cart(id);
            for (var event : currentState) {
                cart.apply(event);
            }
            return cart;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Cart cart) {
        try {
            final var events = cart.getEvents();
            int expectedVersion = cart.getVersion() - events.size();
            writer.append("Cart", cart.getId().toString(), expectedVersion, events);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
