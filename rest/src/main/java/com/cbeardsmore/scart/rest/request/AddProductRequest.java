package com.cbeardsmore.scart.rest.request;

import com.cbeardsmore.scart.domain.command.AddProductCommand;

import java.math.BigDecimal;
import java.util.UUID;

public class AddProductRequest {

    private final UUID productId;
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public AddProductRequest(UUID productId, String name, BigDecimal price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public AddProductCommand toCommand(UUID cartId) {
        return new AddProductCommand(cartId, productId, name, price, quantity);
    }
}