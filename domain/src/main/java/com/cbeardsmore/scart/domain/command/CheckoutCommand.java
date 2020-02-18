package com.cbeardsmore.scart.domain.command;

import com.cbeardsmore.scart.domain.exception.CommandValidationException;

import java.util.Objects;
import java.util.UUID;

public class CheckoutCommand implements Command {

    private final UUID cartId;

    public CheckoutCommand(UUID cartId) {
        if (cartId == null)
            throw new CommandValidationException("cartId");
        this.cartId = cartId;
    }

    public UUID getCartId() {
        return cartId;
    }

    @Override
    public String toString() {
        return String.format("CheckoutCommand[id=%s]", cartId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckoutCommand that = (CheckoutCommand) o;
        return Objects.equals(cartId, that.cartId);
    }
}
