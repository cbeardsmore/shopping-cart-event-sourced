package com.cbeardsmore.scart.rest.response;

import java.math.BigDecimal;
import java.util.Objects;

public class TotalPriceResponse {

    private final BigDecimal totalPrice;

    public TotalPriceResponse(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalPriceResponse that = (TotalPriceResponse) o;
        return Objects.equals(totalPrice, that.totalPrice);
    }
}
