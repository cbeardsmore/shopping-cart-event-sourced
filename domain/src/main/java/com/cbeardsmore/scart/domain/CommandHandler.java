package com.cbeardsmore.scart.domain;

import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;

public class CommandHandler {

    public CommandHandler() {}

    public Object handle(CreateCartCommand command) {
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
