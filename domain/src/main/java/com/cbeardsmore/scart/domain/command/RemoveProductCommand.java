package com.cbeardsmore.scart.domain.command;

import java.util.UUID;

public class RemoveProductCommand implements Command {

    private final UUID cartId;

    public RemoveProductCommand(UUID cartId) {
        this.cartId = cartId;
    }
}
