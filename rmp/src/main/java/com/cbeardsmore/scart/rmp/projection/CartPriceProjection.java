package com.cbeardsmore.scart.rmp.projection;

import java.math.BigDecimal;
import java.util.UUID;

public class CartPriceProjection {
    private final UUID cartId;
    private final BigDecimal price;

    public CartPriceProjection(UUID cartId, BigDecimal price) {
        this.cartId = cartId;
        this.price = price;
    }

    public UUID getCartId() {
        return cartId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
