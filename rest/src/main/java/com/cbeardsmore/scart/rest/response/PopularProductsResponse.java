package com.cbeardsmore.scart.rest.response;

import com.cbeardsmore.scart.domain.model.PopularProduct;

import java.util.List;
import java.util.Objects;

public class PopularProductsResponse {
    private final List<PopularProduct> popularProducts;

    public PopularProductsResponse(List<PopularProduct> popularProducts) {
        this.popularProducts = popularProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopularProductsResponse that = (PopularProductsResponse) o;
        return Objects.equals(popularProducts, that.popularProducts);
    }
}