package com.cbeardsmore.scart.domain.event;

import java.math.BigDecimal;

public final class CheckoutCompletedEvent extends Event {

    private final BigDecimal totalPrice;

    public CheckoutCompletedEvent(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
