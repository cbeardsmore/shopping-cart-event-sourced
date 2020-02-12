package com.cbeardsmore.scart.rest.response;

import java.util.Objects;

public class PriceResponse {

    private final long price;

    public PriceResponse(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceResponse that = (PriceResponse) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
