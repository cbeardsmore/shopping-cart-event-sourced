package com.cbeardsmore.scart.rest.request;

import com.cbeardsmore.scart.domain.command.AddProductCommand;

import java.math.BigDecimal;
import java.util.UUID;

public class AddProductRequest {

    private final UUID productId;
    private final String name;
    private final BigDecimal price;

    public AddProductRequest(UUID productId, String name, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public AddProductCommand toCommand(UUID cartId) {
        return new AddProductCommand(cartId, productId, name, price);
    }
}