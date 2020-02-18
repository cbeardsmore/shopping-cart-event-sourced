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

    public AddProductCommand(UUID cartId, UUID productId, String name, BigDecimal price) {
        if (cartId == null)
            throw new CommandValidationException("cartId");
        if (productId == null)
            throw new CommandValidationException("productId");
        if (StringUtils.isBlank(name))
            throw new CommandValidationException("name");
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new CommandValidationException("price");

        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return String.format("AddProductCommand[cartId=%s,productId=%s,name=%s,price=%s]",
                cartId, productId, name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddProductCommand command = (AddProductCommand) o;
        return Objects.equals(cartId, command.cartId) &&
                Objects.equals(productId, command.productId) &&
                Objects.equals(name, command.name) &&
                Objects.equals(price, command.price);
    }
}