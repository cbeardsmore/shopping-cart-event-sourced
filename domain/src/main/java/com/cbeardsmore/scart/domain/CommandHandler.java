package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.model.Cart;

public class CommandHandler {

    private final Repository<Cart> repository;

    public CommandHandler(Repository<Cart> repository) {
        this.repository = repository;
    }

    public Object handle(CreateCartCommand command) {
        final Cart cart = repository.load(command.getId());
        cart.createCart(command);
        repository.save(cart);
        return null;
    }

    public Object handle(AddProductCommand command) {
        return null;
    }

    public Object handle(CheckoutCommand command) {
        return null;
    }

    public Object handle(RemoveProductCommand command) {
        return null;
    }
}