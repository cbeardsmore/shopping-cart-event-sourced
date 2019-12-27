package com.cbeardsmore.scart.domain.model;

import com.cbeardsmore.scart.domain.event.Event;
import com.cbeardsmore.scart.domain.event.EventEnvelope;

public class BasicEventEnvelope implements EventEnvelope {
    private final long sequenceId;
    private final String streamName;
    private final String streamId;
    private final int version;
    private final Event payload;

    public BasicEventEnvelope(long sequenceId, String streamName, String streamId, int version, Event payload) {
        this.sequenceId = sequenceId;
        this.streamName = streamName;
        this.streamId = streamId;
        this.version = version;
        this.payload = payload;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public String getStreamName() {
        return streamName;
    }

    public String getStreamId() {
        return streamId;
    }

    public int getVersion() {
        return version;
    }

    public Event getPayload() {
        return payload;
    }
}
