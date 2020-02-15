package com.cbeardsmore.scart.rest.utils;

import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.domain.model.PopularProduct;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ReadModelStoreStub extends ReadModelStore {

    public static final UUID PRODUCT_ID = UUID.randomUUID();

    ReadModelStoreStub() {
        super(null);
    }

    @Override
    public BigDecimal getCartPrice(UUID cartId) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalCartsPrice() {
        return BigDecimal.ZERO;
    }

    @Override
    public List<PopularProduct> getPopularProducts() {
        return Collections.singletonList(new PopularProduct(PRODUCT_ID, 1));
    }
}