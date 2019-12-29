package com.cbeardsmore.scart.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public final class ProductAddedEvent extends Event {

    private final UUID productId;
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public ProductAddedEvent(UUID productId, String name, BigDecimal price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}
