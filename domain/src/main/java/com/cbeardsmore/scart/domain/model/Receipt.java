package com.cbeardsmore.scart.domain.model;

import java.util.UUID;

public class Receipt {
    private final UUID aggregateId;

    public Receipt(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }
}