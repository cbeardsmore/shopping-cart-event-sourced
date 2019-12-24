package com.cbeardsmore.scart.domain.command;

import java.util.UUID;

public class CreateCartCommand implements Command {

    private final UUID uuid;

    public CreateCartCommand() {
        this.uuid = UUID.randomUUID();
    }
}
