package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.model.Cart;
import com.cbeardsmore.scart.domain.model.Receipt;

public class CommandHandler {

    private final Repository<Cart> repository;

    public CommandHandler(Repository<Cart> repository) {
        this.repository = repository;
    }

    public Receipt handle(CreateCartCommand command) {
        final Cart cart = repository.load(command.getId());
        cart.createCart(command);
        repository.save(cart);
        return new Receipt(cart.getId());
    }

    public Receipt handle(AddProductCommand command) {
        final Cart cart = repository.load(command.getCartId());
        cart.addProduct(command);
        repository.save(cart);
        return new Receipt(cart.getId());
    }

    public Receipt handle(RemoveProductCommand command) {
        final Cart cart = repository.load(command.getCartId());
        cart.removeProduct(command);
        repository.save(cart);
        return new Receipt(cart.getId());
    }

    public Receipt handle(CheckoutCommand command) {
        final Cart cart = repository.load(command.getCartId());
        cart.checkout(command);
        repository.save(cart);
        return new Receipt(cart.getId());
    }
}