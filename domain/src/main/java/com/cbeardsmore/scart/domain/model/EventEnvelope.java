package com.cbeardsmore.scart.domain.model;

import com.cbeardsmore.scart.domain.event.Event;

import java.util.UUID;

public interface EventEnvelope {
    long getPosition();
    String getStreamName();
    UUID getStreamId();
    int getVersion();
    <T extends Event> T getPayload(Class<T> outputType);
}
