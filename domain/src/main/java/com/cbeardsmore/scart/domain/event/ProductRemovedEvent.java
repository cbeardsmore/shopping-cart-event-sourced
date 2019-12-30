package com.cbeardsmore.scart.domain.event;

import java.util.Objects;
import java.util.UUID;

public final class ProductRemovedEvent extends Event {

    private final UUID productId;

    public ProductRemovedEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRemovedEvent that = (ProductRemovedEvent) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
