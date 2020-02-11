package com.cbeardsmore.scart.rmp.projection;

import java.util.UUID;

public class PopularProductsProjection {
    private final UUID productId;
    private final long count;

    public PopularProductsProjection(UUID productId, long count) {
        this.productId = productId;
        this.count = count;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getCount() {
        return count;
    }
}
