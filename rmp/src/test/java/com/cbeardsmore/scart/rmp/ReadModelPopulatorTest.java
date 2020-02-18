package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.dataaccess.ReadModelStore;
import com.cbeardsmore.scart.domain.event.CartCreatedEvent;
import com.cbeardsmore.scart.domain.event.ProductAddedEvent;
import com.cbeardsmore.scart.domain.event.ProductRemovedEvent;
import com.cbeardsmore.scart.domain.model.PopularProduct;
import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.persistence.PostgresRepository;
import com.cbeardsmore.scart.rmp.utils.EventWrapper;
import com.cbeardsmore.scart.rmp.utils.PostgresDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadModelPopulatorTest {

    private final UUID CART_ID = UUID.randomUUID();
    private final UUID PRODUCT_ID = UUID.randomUUID();
    private final String NAME = "42 TV";
    private final BigDecimal PRICE_ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.CEILING);
    private final BigDecimal PRICE_TEN = BigDecimal.TEN.setScale(2, RoundingMode.CEILING);


    private Bookmark bookmark;
    private ReadModelPopulator populator;
    private ReadModelStore store;
    private EventWrapper eventWrapper;

    @BeforeEach
    void beforeEach() throws IOException  {
        final var dataSource = PostgresDatabase.provide();
        bookmark = new Bookmark(dataSource);
        final var repository = new PostgresRepository(bookmark, dataSource);
        populator = new ReadModelPopulator(repository);
        store = new ReadModelStore(dataSource);
        eventWrapper = new EventWrapper(CART_ID);
    }

    @Test
    void givenAnyEventThenBookmarkIsIncremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        populator.dispatch(cartCreatedEnvelope);
        assertEquals(1, bookmark.get());

        final var productAddedEnvelope = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        populator.dispatch(productAddedEnvelope);
        assertEquals(2, bookmark.get());

        final var productRemovedEnvelope = eventWrapper.wrap(new ProductRemovedEvent(PRODUCT_ID));
        populator.dispatch(productRemovedEnvelope);
        assertEquals(3, bookmark.get());
    }

    @Test
    void givenCartCreatedEventThenUpdateCartPriceAsZero() {
        final var envelope = eventWrapper.wrap(new CartCreatedEvent());
        populator.dispatch(envelope);
        final var cartPrice = store.getCartPrice(CART_ID);
        assertEquals(PRICE_ZERO, cartPrice);
    }

    @Test
    void givenProductAddedEventThenCartPriceIncremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        final var productAddedEvent = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        populator.dispatch(cartCreatedEnvelope);
        populator.dispatch(productAddedEvent);
        assertEquals(PRICE_TEN, store.getCartPrice(CART_ID));
    }

    @Test
    void givenProductAddedEventThenTotalPriceIncremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        final var productAddedEvent = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        populator.dispatch(cartCreatedEnvelope);
        populator.dispatch(productAddedEvent);
        assertEquals(PRICE_TEN, store.getTotalCartsPrice());
    }

    @Test
    void givenProductAddedEventThenPopularProductCountIncremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        final var productAddedEvent = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        populator.dispatch(cartCreatedEnvelope);
        populator.dispatch(productAddedEvent);
        final var expected = Collections.singletonList(new PopularProduct(PRODUCT_ID, 1));
        assertEquals(expected, store.getPopularProducts());
    }

    @Test
    void givenProductRemovedEventThenCartPriceDecremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        final var productAddedEnvelope = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        final var productRemovedEnvelope = eventWrapper.wrap(new ProductRemovedEvent(PRODUCT_ID));
        populator.dispatch(cartCreatedEnvelope);
        populator.dispatch(productAddedEnvelope);
        populator.dispatch(productRemovedEnvelope);
        assertEquals(PRICE_ZERO, store.getCartPrice(CART_ID));
    }

    @Test
    void givenProductRemovedEventThenTotalPriceDecremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        final var productAddedEnvelope = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        final var productRemovedEnvelope = eventWrapper.wrap(new ProductRemovedEvent(PRODUCT_ID));
        populator.dispatch(cartCreatedEnvelope);
        populator.dispatch(productAddedEnvelope);
        populator.dispatch(productRemovedEnvelope);
        assertEquals(PRICE_ZERO, store.getTotalCartsPrice());
    }

    @Test
    void givenProductRemovedEventThenPopularProductsCountDecremented() {
        final var cartCreatedEnvelope = eventWrapper.wrap(new CartCreatedEvent());
        final var productAddedEnvelope = eventWrapper.wrap(new ProductAddedEvent(PRODUCT_ID, NAME, PRICE_TEN));
        final var productRemovedEnvelope = eventWrapper.wrap(new ProductRemovedEvent(PRODUCT_ID));
        populator.dispatch(cartCreatedEnvelope);
        populator.dispatch(productAddedEnvelope);
        populator.dispatch(productRemovedEnvelope);
        final var expected = Collections.singletonList(new PopularProduct(PRODUCT_ID, 0));
        assertEquals(expected, store.getPopularProducts());
    }
}
