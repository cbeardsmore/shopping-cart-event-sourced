package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.rmp.projection.CartPriceProjection;
import com.cbeardsmore.scart.rmp.projection.PopularProductsProjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class PostgresRepository {

    private static final String INSERT_CART_PRICE_PROJECTION =
            "INSERT INTO shopping_cart.cart_price (cartId, price) VALUES (?, ?) "
                    + "ON CONFLICT (cartId) DO UPDATE SET price = cart_price.price + ?";

    private static final String INSERT_POPULAR_PRODUCTS_PROJECTION =
            "INSERT INTO shopping_cart.popular_products (productId, count) VALUES (?, ?) "
                    + "ON CONFLICT (productId) DO UPDATE SET count = popular_products.count + ?";

    private final String connectionUrl;

    public PostgresRepository(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void put(CartPriceProjection projection) {
        try (final var connection = DriverManager.getConnection(connectionUrl)) {
            putCartPriceProjection(connection, projection);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void putCartPriceProjection(Connection connection, CartPriceProjection projection) throws SQLException {
        try (final var statement = connection.prepareStatement(INSERT_CART_PRICE_PROJECTION)) {
            statement.setObject(1, projection.getCartId());
            statement.setLong(2, projection.getPrice());
            statement.setLong(3, projection.getPrice());
            statement.executeUpdate();
        }
    }

    private void putPopularProductsProjection(Connection connection, PopularProductsProjection projection) throws SQLException {
        try (final var statement = connection.prepareStatement(INSERT_POPULAR_PRODUCTS_PROJECTION)) {
            statement.setObject(1, projection.getProductId());
            statement.setLong(2, projection.getCount());
            statement.setLong(3, projection.getCount());
            statement.executeUpdate();
        }
    }
}
