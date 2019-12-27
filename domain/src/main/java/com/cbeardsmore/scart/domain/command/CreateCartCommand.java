package com.cbeardsmore.scart.domain.command;

import java.util.Objects;
import java.util.UUID;

public class CreateCartCommand implements Command {

    private final UUID id;

    public CreateCartCommand() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return String.format("CreateCartCommand[id=%s]", id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCartCommand that = (CreateCartCommand) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
