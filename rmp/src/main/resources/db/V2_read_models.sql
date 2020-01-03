CREATE TABLE shopping_cart.total_price (
    id BIGSERIAL PRIMARY KEY,
    totalPrice BIGINT NOT NULL,
);

CREATE TABLE shopping_cart.cart_price (
    id BIGSERIAL PRIMARY KEY,
    cartId UUID NOT NULL,
    price BIGINT NOT NULL,
    UNIQUE(cartId)
);

CREATE TABLE shopping_cart.popular_products (
    id BIGSERIAL PRIMARY KEY,
    productId UUID NOT NULL,
    count BIGINT NOT NULL,
    UNIQUE(productId)
);