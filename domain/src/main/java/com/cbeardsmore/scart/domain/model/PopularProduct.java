package com.cbeardsmore.scart.domain.model;

import java.util.Objects;
import java.util.UUID;

public class PopularProduct {
    private final UUID productId;
    private final long count;

    public PopularProduct(UUID productId, long count) {
        this.productId = productId;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopularProduct that = (PopularProduct) o;
        return count == that.count &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, count);
    }
}
