package com.cbeardsmore.scart.rest.response;

import java.util.Objects;

public class PopularProductsResponse {

    private final String popular;

    public PopularProductsResponse(String popular) {
        this.popular = popular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopularProductsResponse that = (PopularProductsResponse) o;
        return Objects.equals(popular, that.popular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(popular);
    }
}
