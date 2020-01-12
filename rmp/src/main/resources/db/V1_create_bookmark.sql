CREATE SCHEMA IF NOT EXISTS shopping_cart;

CREATE TABLE shopping_cart.bookmark (
    rmp TEXT PRIMARY KEY,
    position BIGINT NOT NULL
);