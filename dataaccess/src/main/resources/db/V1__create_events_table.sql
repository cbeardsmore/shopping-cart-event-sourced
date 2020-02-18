CREATE SCHEMA IF NOT EXISTS event_store;

CREATE TABLE event_store.events
(
    position BIGSERIAL UNIQUE NOT NULL,
    stream_id UUID NOT NULL,
    version INT NOT NULL,
    event_type TEXT NOT NULL,
    payload JSON NOT NULL,
    PRIMARY KEY (stream_id, version)
);