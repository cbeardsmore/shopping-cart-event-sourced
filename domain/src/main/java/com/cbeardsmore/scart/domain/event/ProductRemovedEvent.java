package com.cbeardsmore.scart.domain.event;

import java.util.UUID;

public final class ProductRemovedEvent extends Event {

    private final UUID productId;

    public ProductRemovedEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
