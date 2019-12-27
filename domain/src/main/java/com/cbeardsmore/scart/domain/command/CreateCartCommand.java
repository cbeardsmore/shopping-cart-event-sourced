package com.cbeardsmore.scart.domain.command;

import java.util.UUID;

public class CreateCartCommand implements Command {

    private final UUID id;

    public CreateCartCommand() {
        this.id = UUID.randomUUID();
    }
}
