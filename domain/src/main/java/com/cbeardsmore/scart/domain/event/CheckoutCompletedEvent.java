package com.cbeardsmore.scart.domain.event;

import java.math.BigDecimal;
import java.util.Objects;

public final class CheckoutCompletedEvent extends Event {

    private final BigDecimal totalPrice;

    public CheckoutCompletedEvent(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckoutCompletedEvent that = (CheckoutCompletedEvent) o;
        return Objects.equals(totalPrice, that.totalPrice);
    }
}
