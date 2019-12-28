package com.cbeardsmore.scart.domain.command;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AddProductCommand implements Command {

    private final UUID cartId;
    private final UUID productId;
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public AddProductCommand(UUID cartId, UUID productId, String name, BigDecimal price, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public AddProductCommand(UUID cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return String.format("AddProductCommand[cartId=%s,productId=%s,name=%s,price=%s,quantity=%d]",
                cartId, productId, name, price, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddProductCommand that = (AddProductCommand) o;
        return quantity == that.quantity &&
                Objects.equals(cartId, that.cartId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId, name, price, quantity);
    }
}