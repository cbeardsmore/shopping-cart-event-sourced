package com.cbeardsmore.scart.rest.response;

import java.util.Objects;

public class TotalPriceResponse {

    private final long totalPrice;

    public TotalPriceResponse(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalPriceResponse that = (TotalPriceResponse) o;
        return totalPrice == that.totalPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPrice);
    }
}
