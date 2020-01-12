package com.cbeardsmore.scart.rmp.persistence;

import org.sql2o.Sql2o;

import javax.sql.DataSource;

public class Bookmark {

    private static final String SELECT_BOOKMARK =
            "SELECT COALESCE(MAX(position), 0) FROM shopping_cart.bookmark "
                    + "WHERE rmp = 'shopping-cart-rmp'";

    private static final String UPSERT_BOOKMARK =
            "INSERT INTO shopping_cart.bookmark (rmp, position) "
                    + "VALUES ('shopping-cart-rmp', :position) "
                    + "ON CONFLICT (rmp) DO UPDATE SET position = :position";

    private final Sql2o sql2o;

    public Bookmark(DataSource dataSource) {
        this.sql2o = new Sql2o(dataSource);
    }

    public long get() {
        try (final var connection = sql2o.open()) {
            try (var query = connection.createQuery(SELECT_BOOKMARK)) {
                return query.executeScalar(Long.class);
            }
        }
    }

    public void put(long position) {
        try (final var connection = sql2o.open()) {
            try (var query = connection.createQuery(UPSERT_BOOKMARK)) {
                query.addParameter("position", position).executeUpdate();
            }
        }
    }
}
