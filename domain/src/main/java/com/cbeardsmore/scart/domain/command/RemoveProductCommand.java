package com.cbeardsmore.scart.domain.command;

import com.cbeardsmore.scart.domain.exception.CommandValidationException;

import java.util.Objects;
import java.util.UUID;

public class RemoveProductCommand implements Command {

    private final UUID cartId;
    private final UUID productId;

    public RemoveProductCommand(UUID cartId, UUID productId) {
        if (cartId == null)
            throw new CommandValidationException("cartId");
        if (productId == null)
            throw new CommandValidationException("productId");

        this.cartId = cartId;
        this.productId = productId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public UUID getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return String.format("RemoveProductCommand{cartId=%s,productId=%s}", cartId, productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoveProductCommand that = (RemoveProductCommand) o;
        return Objects.equals(cartId, that.cartId) &&
                Objects.equals(productId, that.productId);
    }
}
