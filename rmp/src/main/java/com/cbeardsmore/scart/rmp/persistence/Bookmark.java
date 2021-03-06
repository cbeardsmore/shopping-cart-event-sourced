package com.cbeardsmore.scart.rmp.persistence;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Bookmark {

    private static final String SELECT_BOOKMARK =
            "SELECT COALESCE(MAX(position), 0) FROM shopping_cart.bookmark WHERE rmp = 'shopping-cart-rmp'";

    private static final String UPSERT_BOOKMARK =
            "INSERT INTO shopping_cart.bookmark (rmp, position) VALUES ('shopping-cart-rmp', ?) "
                    + "ON CONFLICT (rmp) DO UPDATE SET position = ?";

    private final DataSource dataSource;

    public Bookmark(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long get() {
        try (final var connection = dataSource.getConnection()) {
            try (final var statement = connection.prepareStatement(SELECT_BOOKMARK)) {
                try (final var resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void put(long position) {
        try (final var connection = dataSource.getConnection()) {
            put(connection, position);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void put(Connection connection, long position) throws SQLException {
        try (final var statement = connection.prepareStatement(UPSERT_BOOKMARK)) {
            statement.setLong(1, position);
            statement.setLong(2, position);
            statement.executeUpdate();
        }
    }
}
