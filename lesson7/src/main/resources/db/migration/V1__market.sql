-- Категории товаров
-- parent_id - ссылка на категорию-родителя
CREATE TABLE product_categories (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name varchar UNIQUE NOT NULL,
  parent_id bigint
);

-- Перечень товаров магазина и кол-во доступных товаров
-- is_available - доступность товара для отображения
CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name varchar UNIQUE NOT NULL,
  description varchar,
  image varchar,
  category_id bigint not null,
  created_at timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  quantity integer NOT NULL default 0,
  is_available boolean NOT NULL default true
);

-- Перечень характеристик товара
CREATE TABLE product_attributes (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name varchar UNIQUE NOT NULL,
  description varchar
);

-- Перечень значений характеристик для конкретного товара
CREATE TABLE product_attribute_values (
  product_id bigint,
  attribute_id bigint,
  value varchar NOT NULL
);

-- История изменений цен на товары
CREATE TABLE product_price_histories (
  product_id bigint,
  price decimal(9,2) NOT NULL,
  start_date timestamp(3) not null DEFAULT CURRENT_TIMESTAMP(3),
  end_date timestamp(3)
);

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  username varchar UNIQUE NOT NULL,
  password varchar NOT NULL,
  created_at timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
  is_enable boolean DEFAULT false
);

CREATE TABLE roles (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name varchar UNIQUE NOT NULL
);

CREATE TABLE users_roles (
  user_id bigint,
  role_id bigint
);

-- Личная информация пользователя
CREATE TABLE user_personal_data (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id bigint UNIQUE,
  surname varchar NOT NULL,
  name varchar NOT NULL,
  middle_name varchar,
  email varchar NOT NULL,
  phone_number varchar NOT NULL,
  birth_date date
);

-- Методы доставки (до магазина, до пункта выдачи, до двери)
CREATE TABLE delivery_methods (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  code varchar not null unique,
  description varchar
);

-- Методы/адреса доставки пользователя
CREATE TABLE user_delivery_profiles (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id bigint,
  method_id bigint,
  postcode varchar,
  city varchar,
  street varchar,
  building varchar,
  entrance integer,
  floor integer,
  apartment integer
);

-- Перечень заказов
CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id bigint,
  user_pers_data_id bigint,
  user_deli_prof_id bigint,
  created_at timestamp(3) not null DEFAULT CURRENT_TIMESTAMP(3)
);

-- Перечень позиций заказа и их стоимоть
CREATE TABLE order_items (
  order_id bigint not null,
  product_id bigint not null,
  quantity integer NOT NULL,
  price decimal(9,2) NOT NULL
);

-- Платежная информация по заказу
-- Method: PayPal, карта и т.д.
CREATE TABLE order_payments (
  order_id bigint not null,
  tx_id varchar,
  method varchar,
  price decimal(9,2) NOT NULL,
  paid_for_at timestamp(3)
);

CREATE TABLE order_statuses (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name varchar not null unique
);

-- История изменений статуса заказа
CREATE TABLE order_status_histories (
  order_id bigint not null,
  status_id bigint default 1,
  start_date timestamp(3) DEFAULT 'now()',
  end_date timestamp(3)
);

ALTER TABLE products ADD FOREIGN KEY (category_id) REFERENCES product_categories (id);
ALTER TABLE product_categories ADD FOREIGN KEY (parent_id) REFERENCES product_categories (id);
ALTER TABLE product_attribute_values ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE product_attribute_values ADD FOREIGN KEY (attribute_id) REFERENCES product_attributes (id);
ALTER TABLE product_price_histories ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE users_roles ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE users_roles ADD FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE user_personal_data ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_delivery_profiles ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_delivery_profiles ADD FOREIGN KEY (method_id) REFERENCES delivery_methods (id);
ALTER TABLE orders ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE orders ADD FOREIGN KEY (user_pers_data_id) REFERENCES user_personal_data (id);
ALTER TABLE orders ADD FOREIGN KEY (user_deli_prof_id) REFERENCES user_delivery_profiles (id);
ALTER TABLE order_items ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_items ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE order_payments ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_status_histories ADD FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_status_histories ADD FOREIGN KEY (status_id) REFERENCES order_statuses (id);

--CREATE UNIQUE INDEX "unique_prod_attr_idx" ON "product_attribute_values" ("product_id", "attribute_id");

--ALTER TABLE "product_attribute_values" ADD CONSTRAINT "unique_prod_attr" UNIQUE USING INDEX "unique_prod_attr_idx";

--CREATE INDEX "prod_attr_values_idx" ON "product_attribute_values" USING BTREE ("value", "attribute_id", "product_id");

insert into product_categories (name)
values ('PC Components'),
       ('Peripherals');

insert into products (name, description, category_id)
values ('PSU', 'PSU Description', 1),
       ('Motherboard', 'MB Description', 1),
       ('Memory RAM', 'RAM Description', 1),
       ('CPU', 'CPU Description', 1),
       ('Graphic card', 'VGA Description', 1),
       ('HDD', 'HDD Description', 1),
       ('SSD', 'SSD Description', 1),
       ('Sound card', 'Sound Card Description', 1),
       ('Case', 'Case Description', 1),
       ('CPU Cooler', 'CPU Cooler Description', 1),
       ('Fan', 'Fan Description', 1),
       ('Network Adapter', 'Network Adapter Description', 1),
       ('Wi-Fi Adapter', 'Wi-Fi Adapter Description', 1),
       ('Monitor', 'Monitor Description', 2),
       ('Keyboard', 'Keyboard Description', 2),
       ('Mouse Adapter', 'Mouse Adapter Description', 2),
       ('Headphones', 'Headphones Description', 2),
       ('Wi-Fi Router', 'Wi-Fi Router Description', 2),
       ('Web Camera', 'Web Camera Description', 2),
       ('USB Hub', 'USB Hub Description', 2);

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
       (20, 25);

insert into order_statuses (id, name)
values (1, 'Created'),
       (2, 'Awaiting Payment'),
       (3, 'Awaiting Fulfillment'),
       (4, 'Awaiting Shipment'),
       (5, 'Shipped '),
       (6, 'Completed'),
       (7, 'Cancelled'),
       (8, 'Refunded');

insert into users (username, password, is_enable)
VALUES
    ('admin', '$2a$12$ceaV6lsFESjPYbj0sxvMl.1vZ/BKOrzRkY0Iue2Q9B325OWUhWJ2u', true),
    ('manager', '$2a$12$qGqr6T2/cXdD/HUT9w2KpOpnkhahpNbY/TXLx2YaHa2XgI0cp6zaW', true),
    ('user', '$2a$12$C4CmyvinB6utYfp7WAZCqe3qrdX5KDyvldbesg8mxH.hqGBk9eCVG', true);

insert into roles (name)
values ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_USER');

insert into users_roles
values (1,1), (2,2), (3,3);

insert into delivery_methods (code, description)
values ('toStore', 'Доставка до выбранного магазина'),
       ('toPost', 'Доставка до пункта выдачи'),
       ('toDoor', 'Доставка курьером по адресу');