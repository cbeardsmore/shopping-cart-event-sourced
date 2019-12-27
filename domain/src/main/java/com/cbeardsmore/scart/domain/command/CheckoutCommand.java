package com.cbeardsmore.scart.domain.command;

import java.util.UUID;

public class CheckoutCommand implements Command {

    private final UUID cartId;

    public CheckoutCommand(UUID cartId) {
        this.cartId = cartId;
    }
}
