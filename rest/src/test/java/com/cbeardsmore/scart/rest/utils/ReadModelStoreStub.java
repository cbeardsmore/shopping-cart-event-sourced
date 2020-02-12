package com.cbeardsmore.scart.rest.utils;

import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.domain.model.PopularProduct;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ReadModelStoreStub extends ReadModelStore {

    public static final UUID PRODUCT_ID = UUID.randomUUID();

    ReadModelStoreStub() {
        super(null);
    }

    @Override
    public long getCartPrice(UUID cartId) {
        return 0L;
    }

    @Override
    public long getTotalCartsPrice() {
        return 0L;
    }

    @Override
    public List<PopularProduct> getPopularProducts() {
        return Collections.singletonList(new PopularProduct(PRODUCT_ID, 1));
    }
}