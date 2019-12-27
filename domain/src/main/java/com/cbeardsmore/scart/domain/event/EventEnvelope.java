package com.cbeardsmore.scart.domain.event;

public interface EventEnvelope {
    long getSequenceId();
    String getStreamName();
    String getStreamId();
    int getVersion();
    Event getPayload();
}