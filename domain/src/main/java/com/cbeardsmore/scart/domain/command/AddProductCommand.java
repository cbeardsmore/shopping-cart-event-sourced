package com.cbeardsmore.scart.domain.command;

import com.cbeardsmore.scart.domain.exception.CommandValidationException;
import org.apache.commons.lang3.StringUtils;

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
        if (cartId == null)
            throw new CommandValidationException("cartId cannot be null for AddProductCommand.");
        if (productId == null)
            throw new CommandValidationException("productId cannot be null for AddProductCommand.");
        if (StringUtils.isBlank(name))
            throw new CommandValidationException("name cannot be null for AddProductCommand.");
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new CommandValidationException("price must be positive for AddProductCommand.");
        if (quantity <= 0)
            throw new CommandValidationException("quantity must be positive for AddProductCommand.");

        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getCartId() {
        return cartId;
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