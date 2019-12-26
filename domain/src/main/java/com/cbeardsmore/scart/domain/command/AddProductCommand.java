package com.cbeardsmore.scart.domain.command;

import java.math.BigDecimal;
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
}