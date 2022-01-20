CREATE TABLE IF NOT EXISTS categories
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

create table if not exists products (
    id bigserial primary key,
    title varchar(255) not null,
    price numeric(9,2) not null,
    category_id BIGINT REFERENCES categories (id)
);

insert into categories
values ('PC Components'), ('Peripherals');

insert into products (title, price)
values ('PSU', 100.0, 1),
       ('Motherboard', 200.0, 1),
       ('Memory RAM', 300.0, 1),
       ('CPU', 400.0, 1),
       ('Graphic card', 500.0, 1),
       ('HDD', 80.0, 1),
       ('SSD', 100.0, 1),
       ('Sound card', 150.0, 1),
       ('Case', 130.0, 1),
       ('CPU Cooler', 50.0, 1),
       ('Fan', 20.0, 1),
       ('Network Adapter', 35.0, 1),
       ('Wi-Fi Adapter', 55.0, 1),
       ('Monitor', 380.0, 2),
       ('Keyboard', 170.0, 2),
       ('Mouse Adapter', 140.0, 2),
       ('Headphones', 230.0, 2),
       ('Wi-Fi Router', 90.0, 2),
       ('Web Camera', 60.0, 2),
       ('USB Hub', 25.0, 2);