package com.cbeardsmore.scart.rest.utils;

import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.Command;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.model.Receipt;

public class CommandHandlerStub extends CommandHandler {

    private Command lastCommand;
    private boolean throwNext;
    private RuntimeException nextException;

    CommandHandlerStub() {
        super(null);
    }

    @Override
    public Receipt handle(CreateCartCommand command) {
        throwNextIfRequired();
        lastCommand = command;
        return null;
    }

    @Override
    public Receipt handle(AddProductCommand command) {
        throwNextIfRequired();
        lastCommand = command;
        return null;
    }

    @Override
    public Receipt handle(RemoveProductCommand command) {
        throwNextIfRequired();
        lastCommand = command;
        return null;
    }

    @Override
    public Receipt handle(CheckoutCommand command) {
        throwNextIfRequired();
        lastCommand = command;
        return null;
    }

    Command getLastCommand() {
        return lastCommand;
    }

    void whenNextCommandThrow(RuntimeException e) {
        this.throwNext = true;
        this.nextException = e;
    }

    private void throwNextIfRequired() {
        if (throwNext) {
            throwNext = false;
            throw nextException;
        }
    }
}