package com.cbeardsmore.scart.rmp.projection;

import java.util.UUID;

public class CartPriceProjection {
    private final UUID cartId;
    private final long price;

    public CartPriceProjection(UUID cartId, long price) {
        this.cartId = cartId;
        this.price = price;
    }

    public UUID getCartId() {
        return cartId;
    }

    public long getPrice() {
        return price;
    }
}
