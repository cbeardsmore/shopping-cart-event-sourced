package com.cbeardsmore.scart.dataaccess;

import com.cbeardsmore.scart.domain.model.PopularProduct;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadModelStore {

    private static final String SELECT_TOTAL_PRICE =
            "SELECT totalPrice FROM shopping_cart.total_price WHERE store = 'Store'";

    private static final String SELECT_CART_PRICE =
            "SELECT price FROM shopping_cart.cart_price WHERE cartId = ?";

    private static final String SELECT_POPULAR_PRODUCTS =
            "SELECT productId, count FROM shopping_cart.popular_products LIMIT 5";

    private final DataSource dataSource;

    public ReadModelStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public BigDecimal getCartPrice(UUID cartId) {
        try (final var connection = dataSource.getConnection()) {
            try (final var statement = connection.prepareStatement(SELECT_CART_PRICE)) {
                statement.setObject(1, cartId);
                try (final var resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getBigDecimal(1);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public BigDecimal getTotalCartsPrice() {
        try (final var connection = dataSource.getConnection()) {
            try (final var statement = connection.prepareStatement(SELECT_TOTAL_PRICE)) {
                try (final var resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getBigDecimal(1);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PopularProduct> getPopularProducts() {
        try (final var connection = dataSource.getConnection()) {
            try (final var statement = connection.prepareStatement(SELECT_POPULAR_PRODUCTS)) {
                try (final var resultSet = statement.executeQuery()) {
                    final List<PopularProduct> products = new ArrayList<>();
                    while (resultSet.next()) {
                        final var productId = resultSet.getObject(1, UUID.class);
                        final var count = resultSet.getLong(2);
                        products.add(new PopularProduct(productId, count));
                    }
                    return products;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}