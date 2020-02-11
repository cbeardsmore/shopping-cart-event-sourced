package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.rmp.projection.CartPriceProjection;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class PostgresRepository {

    private static final String INSERT_CART_PRICE_PROJECTION =
            "INSERT INTO shopping_cart.cart_price (cartId, price) VALUES (?, ?) "
                    + "ON CONFLICT (cartId) DO UPDATE SET price = cart_price.price + ?";

    private final String connectionUrl;

    public PostgresRepository(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void put(CartPriceProjection projection, long position) {
        try (final var connection = DriverManager.getConnection(connectionUrl)) {
            try (final var statement = connection.prepareStatement(INSERT_CART_PRICE_PROJECTION)) {
                statement.setObject(1, projection.getCartId());
                statement.setLong(2, projection.getPrice());
                statement.setLong(3, projection.getPrice());
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
