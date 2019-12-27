package com.cbeardsmore.scart.domain.command;

import java.util.UUID;

public class RemoveProductCommand implements Command {

    private final UUID cartId;
    private final UUID productId;

    public RemoveProductCommand(UUID cartId, UUID productId) {
        this.cartId = cartId;
        this.productId = productId;
    }
}
