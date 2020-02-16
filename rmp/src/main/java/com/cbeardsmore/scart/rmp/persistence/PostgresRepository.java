package com.cbeardsmore.scart.rmp.persistence;

import com.cbeardsmore.scart.rmp.projection.CartPriceProjection;
import com.cbeardsmore.scart.rmp.projection.PopularProductsProjection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class PostgresRepository {

    private static final String INSERT_CART_PRICE_PROJECTION =
            "INSERT INTO shopping_cart.cart_price (cartId, price) VALUES (?, ?) "
                    + "ON CONFLICT (cartId) DO UPDATE SET price = cart_price.price + ?";

    private static final String INSERT_POPULAR_PRODUCTS_PROJECTION =
            "INSERT INTO shopping_cart.popular_products (productId, count) VALUES (?, ?) "
                    + "ON CONFLICT (productId) DO UPDATE SET count = popular_products.count + ?";

    private static final String UPDATE_TOTAL_PRICE =
            "INSERT INTO shopping_cart.total_price (store, totalPrice) VALUES ('Store', ?)"
                    + "ON CONFLICT (store) DO UPDATE SET totalPrice = total_price.totalPrice + ?";

    private final Bookmark bookmark;
    private final DataSource dataSource;

    public PostgresRepository(Bookmark bookmark, DataSource dataSource) {
        this.bookmark = bookmark;
        this.dataSource = dataSource;
    }

    public void put(CartPriceProjection projection, long position) {
        try (final var connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            putCartPriceProjection(connection, projection);
            bookmark.put(connection, position);
            connection.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void put(CartPriceProjection cartPriceProjection, PopularProductsProjection productsProjection, long position) {
        try (final var connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            putCartPriceProjection(connection, cartPriceProjection);
            putPopularProductsProjection(connection, productsProjection);
            updateTotalPrice(connection, cartPriceProjection.getPrice());
            bookmark.put(connection, position);
            connection.commit();
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

    private void updateTotalPrice(Connection connection, long price) throws SQLException {
        try (final var statement = connection.prepareStatement(UPDATE_TOTAL_PRICE)) {
            statement.setLong(1, price);
            statement.setLong(2, price);
            statement.executeUpdate();
        }
    }
}
