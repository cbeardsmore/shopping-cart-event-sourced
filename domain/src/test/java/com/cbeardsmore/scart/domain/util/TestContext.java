package com.cbeardsmore.scart.domain.util;

import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.event.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestContext {

    private final InMemoryRepository repository;
    private final CommandHandler handler;
    private Exception lastException;
    private int eventCursor;

    private TestContext() {
        this.repository = new InMemoryRepository();
        this.handler = new CommandHandler(repository);
        this.eventCursor = 0;
    }

    public static TestContext init() {
        return new TestContext();
    }

    public void givenNoEvents() {
    }

    public void givenEvent(Event event) {
        repository.append(event);
        eventCursor++;
    }

    public void whenCommand(CreateCartCommand command) {
        try {
            handler.handle(command);
        } catch (Exception ex) {
            lastException = ex;
        }
    }

    public void whenCommand(AddProductCommand command) {
        try {
            handler.handle(command);
        } catch (Exception ex) {
            lastException = ex;
        }
    }

    public void whenCommand(RemoveProductCommand command) {
        try {
            handler.handle(command);
        } catch (Exception ex) {
            lastException = ex;
        }
    }

    public void whenCommand(CheckoutCommand command) {
        try {
            handler.handle(command);
        } catch (Exception ex) {
            lastException = ex;
        }
    }

    public void thenAssertEventAndPayload(Object expectedPayload) {
        final var event = repository.read(eventCursor);
        assertEquals(expectedPayload, event);
        eventCursor++;
    }

    public void thenAssertNoOtherEventsAreRaised() {
        assertEquals(0, repository.readAll(eventCursor).size());
    }

    public void thenAssertException(Class exceptionClass) {
        assertEquals(exceptionClass, lastException.getClass());
    }

}