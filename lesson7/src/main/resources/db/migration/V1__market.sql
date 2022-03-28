-- Категории товаров
-- parent_id - ссылка на категорию-родителя
CREATE TABLE product_categories
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    name      varchar UNIQUE        NOT NULL,
    image     varchar,
    parent_id bigint
);

-- Перечень товаров магазина и кол-во доступных товаров
-- is_available - доступность товара для отображения
CREATE TABLE products
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    name         varchar UNIQUE        NOT NULL,
    description  varchar,
    image        varchar,
    category_id  bigint                not null,
    created_at   timestamp(3)          NOT NULL DEFAULT now(),
    quantity     integer               NOT NULL default 0,
    is_available boolean               NOT NULL default true
);

-- Перечень характеристик товара
CREATE TABLE attributes
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    name        varchar UNIQUE        NOT NULL,
    description varchar
);

-- Доступные хар-ки для категории
CREATE TABLE attributes_categories
(
    attribute_id bigint NOT NULL,
    category_id  bigint NOT NULL
);

-- Перечень значений характеристик для конкретного товара
CREATE TABLE attribute_values
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    attribute_id bigint                NOT NULL,
    value        varchar               NOT NULL
);

CREATE TABLE products_attribute_values
(
    product_id    bigint NOT NULL,
    attr_value_id bigint NOT NULL
);

-- История изменений цен на товары
CREATE TABLE product_price_histories
(
    product_id bigint,
    price      decimal(9, 2) NOT NULL,
    start_date timestamp(3)  not null DEFAULT now(),
    end_date   timestamp(3)
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    email      varchar UNIQUE        NOT NULL,
    password   varchar               NOT NULL,
    created_at timestamp(3) DEFAULT now(),
    is_enable  boolean      DEFAULT false
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name varchar UNIQUE        NOT NULL
);

CREATE TABLE users_roles
(
    user_id bigint,
    role_id bigint
);

-- Личная информация пользователя
CREATE TABLE user_personal_data
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    user_id      bigint UNIQUE,
    surname      varchar,
    name         varchar,
    middle_name  varchar,
    phone_number varchar               NOT NULL,
    birth_date   date
);

-- Методы доставки (до магазина, до пункта выдачи, до двери)
CREATE TABLE delivery_methods
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    code        varchar               not null unique,
    description varchar
);


-- Методы/адреса доставки пользователя
CREATE TABLE delivery_addresses
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    user_id   bigint unique,
    method_id bigint,
    postcode  varchar,
    city      varchar,
    street    varchar,
    building  varchar,
    entrance  varchar,
    floor     varchar,
    apartment varchar
);

-- Перечень заказов
CREATE TABLE orders
(
    id                BIGSERIAL PRIMARY KEY NOT NULL,
    user_id           bigint,
    contact_email     varchar,
    user_pers_data_id bigint,
    deli_addr_id      bigint,
    manager_id        bigint,
    created_at        timestamp(3)          not null DEFAULT now()
);

-- Перечень позиций заказа и их стоимоть
CREATE TABLE order_items
(
    order_id   bigint         not null,
    product_id bigint         not null,
    quantity   integer        NOT NULL,
    price      decimal(12, 2) NOT NULL
);

-- Платежная информация по заказу
-- Method: PayPal, карта и т.д.
CREATE TABLE order_payments
(
    order_id    bigint         not null,
    tx_id       varchar,
    method      varchar,
    price       decimal(12, 2) NOT NULL,
    paid_for_at timestamp(3)
);

CREATE TABLE order_statuses
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    code        varchar               not null unique,
    description varchar               not null
);

-- История изменений статуса заказа
CREATE TABLE order_status_histories
(
    order_id   bigint not null,
    status_id  bigint       default 1,
    start_date timestamp(3) DEFAULT now(),
    end_date   timestamp(3)
);

CREATE TABLE registration_tokens
(
    id         bigserial primary key not null,
    user_id    bigint                not null,
    token      varchar(128)          not null,
    expired_at timestamp             not null
);

ALTER TABLE products
    ADD FOREIGN KEY (category_id) REFERENCES product_categories (id);
ALTER TABLE product_categories
    ADD FOREIGN KEY (parent_id) REFERENCES product_categories (id);
ALTER TABLE attribute_values
    ADD FOREIGN KEY (attribute_id) REFERENCES attributes (id);
ALTER TABLE attributes_categories
    ADD FOREIGN KEY (category_id) REFERENCES product_categories (id);
ALTER TABLE attributes_categories
    ADD FOREIGN KEY (attribute_id) REFERENCES attributes (id);
ALTER TABLE products_attribute_values
    ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE products_attribute_values
    ADD FOREIGN KEY (attr_value_id) REFERENCES attribute_values (id);
ALTER TABLE product_price_histories
    ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE users_roles
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE users_roles
    ADD FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE user_personal_data
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE delivery_addresses
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE delivery_addresses
    ADD FOREIGN KEY (method_id) REFERENCES delivery_methods (id);
ALTER TABLE orders
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE orders
    ADD FOREIGN KEY (manager_id) REFERENCES users (id);
ALTER TABLE orders
    ADD FOREIGN KEY (user_pers_data_id) REFERENCES user_personal_data (id);
ALTER TABLE orders
    ADD FOREIGN KEY (deli_addr_id) REFERENCES delivery_addresses (id);
ALTER TABLE order_items
    ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_items
    ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE order_payments
    ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_status_histories
    ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_status_histories
    ADD FOREIGN KEY (status_id) REFERENCES order_statuses (id);
ALTER TABLE registration_tokens
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

CREATE UNIQUE INDEX unique_prod_attr_idx ON products_attribute_values (product_id, attr_value_id);

ALTER TABLE products_attribute_values
    ADD constraint unique_prod_attr UNIQUE (product_id, attr_value_id);

CREATE INDEX prod_attr_values_idx ON products_attribute_values (product_id, attr_value_id);

insert into product_categories (name, image)
values ('PC Components', '/media/cat_pc_parts.jpg'),
       ('Peripherals', '/media/cat_periph.jpg'),
       ('PC, Notebooks', '/media/cat_pc.jpg');

insert into products (name, description, category_id, image)
values ('PSU', 'PSU Description', 1, '/media/psu.jpg'),
       ('Motherboard', 'MB Description', 1, '/media/mb.png'),
       ('Memory RAM', 'RAM Description', 1, '/media/ram.jpg'),
       ('CPU', 'CPU Description', 1, '/media/cpu.jpg'),
       ('Graphic card', 'VGA Description', 1, '/media/vga.jpg'),
       ('HDD', 'HDD Description', 1, '/media/hdd.jpg'),
       ('SSD', 'SSD Description', 1, '/media/ssd.jpg'),
       ('Sound card', 'Sound Card Description', 1, '/media/sound_card.jpg'),
       ('Case', 'Case Description', 1, '/media/case.jpg'),
       ('CPU Cooler', 'CPU Cooler Description', 1, '/media/cpu_cooler.jpg'),
       ('Fan', 'Fan Description', 1, '/media/fan.jpg'),
       ('Network Adapter', 'Network Adapter Description', 1, '/media/network_adapter.jpg'),
       ('Wi-Fi Adapter', 'Wi-Fi Adapter Description', 1, '/media/wifi_adapter.jpg'),
       ('Monitor', 'Monitor Description', 2, '/media/monitor.jpg'),
       ('Keyboard', 'Keyboard Description', 2, '/media/keyboard.jpg'),
       ('Mouse Adapter', 'Mouse Adapter Description', 2, '/media/mouse.jpg'),
       ('Headphones', 'Headphones Description', 2, '/media/headphones.jpg'),
       ('Wi-Fi Router', 'Wi-Fi Router Description', 2, '/media/router.jpg'),
       ('Web Camera', 'Web Camera Description', 2, '/media/web_camera.jpg'),
       ('USB Hub', 'USB Hub Description', 2, '/media/usb_hub.jpg'),
       ('PC', 'PC Description', 3, '/media/pc.jpg'),
       ('Notebook', 'Notebook Description', 3, '/media/notebook.jpg'),
       ('Monoblock PC', 'Monoblock PC Description', 3, '/media/mono.jpg');

insert into product_price_histories (product_id, price)
values (1, 100),
       (2, 200),
       (3, 300),
       (4, 400),
       (5, 500),
       (6, 80),
       (7, 100),
       (8, 150),
       (9, 130),
       (10, 50),
       (11, 20),
       (12, 55),
       (13, 35),
       (14, 380),
       (15, 170),
       (16, 140),
       (17, 230),
       (18, 90),
       (19, 60),
       (20, 25),
       (21, 1500),
       (22, 2000),
       (23, 2500);

insert into order_statuses (code, description)
values ('notPaid', 'Awaiting Payment'),
       ('created', 'Created'),
       ('fulfilled', 'Fulfilled'),
       ('shipped', 'Shipped'),
       ('completed', 'Completed'),
       ('cancelled', 'Cancelled'),
       ('refunded', 'Refunded');

insert into users (email, password, is_enable)
VALUES ('admin@shop.ru', '$2a$12$ceaV6lsFESjPYbj0sxvMl.1vZ/BKOrzRkY0Iue2Q9B325OWUhWJ2u', true),
       ('manager1@shop.ru', '$2a$12$qGqr6T2/cXdD/HUT9w2KpOpnkhahpNbY/TXLx2YaHa2XgI0cp6zaW', true),
       ('manager2@shop.ru', '$2a$12$qGqr6T2/cXdD/HUT9w2KpOpnkhahpNbY/TXLx2YaHa2XgI0cp6zaW', true),
       ('user@shop.ru', '$2a$12$C4CmyvinB6utYfp7WAZCqe3qrdX5KDyvldbesg8mxH.hqGBk9eCVG', true);

insert into roles (name)
values ('ROLE_ADMIN'),
       ('ROLE_MANAGER'),
       ('ROLE_USER');

insert into users_roles
values (1, 1),
       (2, 2),
       (3, 2),
       (4, 3);

insert into delivery_methods (code, description)
values ('toStore', 'Доставка до выбранного магазина'),
       ('toPost', 'Доставка до пункта выдачи'),
       ('toDoor', 'Доставка курьером по адресу');

insert into user_personal_data (user_id, surname, name, phone_number)
values (1, 'admin_surname', 'admin_name', 'admin_phone');

insert into delivery_addresses (user_id, postcode, city, street, building, entrance, floor, apartment)
values (1, 'admin_post', 'admin_city', 'admin_street', 'admin_build', 'admin_entr', 'admin_floor', 'admin_apt');

insert into attributes (name)
VALUES ('Product Dimensions'),
       ('Item Weight'),
       ('Colour'),
       ('Manufacturer'),
       ('Date First Available');

insert into attribute_values (attribute_id, value) VALUES
(1, '140 x 140 x 75'),
(2, '3'),
(4, 'Super Flower'),
(5, '27.10.2020');

insert into products_attribute_values (product_id, attr_value_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);