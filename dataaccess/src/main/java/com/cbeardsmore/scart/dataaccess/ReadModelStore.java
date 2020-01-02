package com.cbeardsmore.scart.dataaccess;

import java.math.BigDecimal;
import java.util.UUID;

public class ReadModelStore {

    private final String connectionUrl;

    public ReadModelStore(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public BigDecimal getCartPrice(UUID cartId) {
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalCartsPrice() {
        return BigDecimal.ZERO;
    }

    public String getPopularProducts() {
        return null;
    }
}