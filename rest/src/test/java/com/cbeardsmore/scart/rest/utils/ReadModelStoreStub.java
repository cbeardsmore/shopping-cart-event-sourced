package com.cbeardsmore.scart.rest.utils;

import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.domain.CommandHandler;
import com.cbeardsmore.scart.domain.command.AddProductCommand;
import com.cbeardsmore.scart.domain.command.CheckoutCommand;
import com.cbeardsmore.scart.domain.command.Command;
import com.cbeardsmore.scart.domain.command.CreateCartCommand;
import com.cbeardsmore.scart.domain.command.RemoveProductCommand;
import com.cbeardsmore.scart.domain.model.Receipt;

import java.math.BigDecimal;
import java.util.UUID;

public class ReadModelStoreStub extends ReadModelStore {

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
    public String getPopularProducts() {
        return null;
    }
}