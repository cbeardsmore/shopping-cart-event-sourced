package com.cbeardsmore.scart.domain.event;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class ProductAddedEvent extends Event {

    private final UUID productId;
    private final String name;
    private final BigDecimal price;

    public ProductAddedEvent(UUID productId, String name, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAddedEvent that = (ProductAddedEvent) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price);
    }
}
