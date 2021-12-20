create table if not exists products (
    id bigserial primary key,
    title varchar(255),
    price float
);

insert into products (title, price)
values ('PSU', 100.0),
       ('Motherboard', 200.0),
       ('Memory RAM', 300.0),
       ('CPU', 400.0),
       ('Graphic card', 500.0),
       ('HDD', 80.0),
       ('SSD', 100.0),
       ('Sound card', 150.0),
       ('Case', 130.0),
       ('CPU Cooler', 50.0),
       ('Fan', 20.0),
       ('Network Adapter', 35.0),
       ('Wi-Fi Adapter', 55.0),
       ('Monitor', 380.0),
       ('Keyboard', 170.0),
       ('Mouse Adapter', 140.0),
       ('Headphones', 230.0),
       ('Wi-Fi Router', 90.0),
       ('Web Camera', 60.0),
       ('USB Hub', 25.0);