CREATE TABLE categories
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    name      varchar UNIQUE        NOT NULL
);

CREATE TABLE products
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    name         varchar UNIQUE        NOT NULL,
    category_id  bigint                not null references categories (id),
    price        decimal(9,2)          not null,
    quantity     integer               NOT NULL default 0
);

insert into categories (name)
values ('PC Components'),
       ('Peripherals');

insert into products (name, category_id, price, quantity)
values ('CPU_!', 1, 400.00, 2),
       ('CPU_2', 1, 599.99, 1),
       ('Keyboard_1', 2, 120.00, 5),
       ('Keyboard_2', 2, 299.99, 2),
       ('Monitor', 2, 899.00, 3);
