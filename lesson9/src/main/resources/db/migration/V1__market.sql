-- Категории товаров
-- parent_id - ссылка на категорию-родителя
CREATE TABLE "product_categories" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar UNIQUE NOT NULL,
  "parent_id" bigint
);

-- Перечень производителей товаров
CREATE TABLE "product_brands" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar UNIQUE NOT NULL
);

-- Перечень товаров магазина
-- is_available - доступность товара для отображения
CREATE TABLE "products" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar UNIQUE NOT NULL,
  "category_id" bigint,
  "brand_id" bigint,
  "created_at" timestamp(3) NOT NULL DEFAULT 'now()',
  "is_available" boolean NOT NULL DEFAULT true
);

-- Перечень характеристик товара
CREATE TABLE "product_attributes" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar UNIQUE NOT NULL
);

-- Перечень значений характеристик для конкретного товара
CREATE TABLE "product_attribute_values" (
  "product_id" bigint,
  "attribute_id" bigint,
  "value" varchar NOT NULL
);

-- Кол-во доступных ед. товара
CREATE TABLE "product_availabilities" (
  "product_id" bigint UNIQUE,
  "quantity" integer NOT NULL DEFAULT 0
);

-- История изменений цен на товары
CREATE TABLE "product_price_histories" (
  "product_id" bigint,
  "price" decimal(9,2) NOT NULL,
  "start_date" timestamp(3) DEFAULT 'now()',
  "end_date" timestamp(3) DEFAULT '2999-12-31'
);

CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "username" varchar UNIQUE NOT NULL,
  "password" varchar NOT NULL,
  "created_at" timestamp(3) DEFAULT 'now()',
  "is_enabled" boolean DEFAULT false
);

CREATE TABLE "roles" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar UNIQUE NOT NULL
);

CREATE TABLE "users_roles" (
  "user_id" bigint,
  "role_id" bigint
);

-- Личная информация пользователя
CREATE TABLE "user_personal_data" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "user_id" bigint,
  "surname" varchar NOT NULL,
  "name" varchar NOT NULL,
  "middle_name" varchar,
  "email" varchar NOT NULL,
  "phone_number" varchar NOT NULL,
  "birth_date" date
);

-- Методы доставки (до магазина, до пункта выдачи, до двери)
CREATE TABLE "delivery_methods" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "name" varchar
);

-- Методы/адреса доставки пользователя
CREATE TABLE "user_delivery_profiles" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "user_id" bigint,
  "method_id" bigint,
  "postcode" varchar,
  "city" varchar,
  "street" varchar,
  "building" varchar,
  "entrance" integer,
  "floor" integer,
  "apartment" integer
);

-- Перечень заказов
CREATE TABLE "orders" (
  "id" BIGSERIAL PRIMARY KEY NOT NULL,
  "user_id" bigint,
  "user_pers_data_id" bigint,
  "user_deli_prof_id" bigint,
  "created_at" timestamp(3) DEFAULT 'now()'
);

-- Перечень позиций заказа и их стоимоть
CREATE TABLE "order_details" (
  "order_id" bigint,
  "product_id" bigint,
  "quantity" integer NOT NULL,
  "price" decimal(9,2) NOT NULL
);

-- Платежная информация по заказу
-- Method: PayPal, карта и т.д.
CREATE TABLE "order_payments" (
  "order_id" bigint,
  "tx_id" varchar NOT NULL,
  "method" varchar NOT NULL,
  "amount" decimal(9,2) NOT NULL,
  "paid_for_at" timestamp(3)
);

-- Статусы заказа
CREATE TYPE "order_statuses" AS ENUM ('Created', 
									  'Awaiting Payment', 
									  'Awaiting Fulfillmen', 
								      'Awaiting Shipment',
									  'Shipped ',
									  'Completed',
									  'Cancelled',
									  'Refunded');

-- История изменений статуса заказа
CREATE TABLE "order_status_histories" (
  "order_id" bigint,
  "status" order_statuses DEFAULT 'Created',
  "start_date" timestamp(3) DEFAULT 'now()',
  "end_date" timestamp(3) DEFAULT '2999-12-31'
);

ALTER TABLE "products" ADD FOREIGN KEY ("category_id") REFERENCES "product_categories" ("id");

ALTER TABLE "products" ADD FOREIGN KEY ("brand_id") REFERENCES "product_brands" ("id");

ALTER TABLE "product_categories" ADD FOREIGN KEY ("parent_id") REFERENCES "product_categories" ("id");

ALTER TABLE "product_attribute_values" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "product_attribute_values" ADD FOREIGN KEY ("attribute_id") REFERENCES "product_attributes" ("id");

ALTER TABLE "product_availabilities" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "product_price_histories" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "users_roles" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "users_roles" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "user_personal_data" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_delivery_profiles" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_delivery_profiles" ADD FOREIGN KEY ("method_id") REFERENCES "delivery_methods" ("id");

ALTER TABLE "orders" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "orders" ADD FOREIGN KEY ("user_pers_data_id") REFERENCES "user_personal_data" ("id");

ALTER TABLE "orders" ADD FOREIGN KEY ("user_deli_prof_id") REFERENCES "user_delivery_profiles" ("id");

ALTER TABLE "order_details" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id");

ALTER TABLE "order_details" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "order_payments" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id");

ALTER TABLE "order_status_histories" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id");

CREATE UNIQUE INDEX "unique_prod_attr_idx" ON "product_attribute_values" ("product_id", "attribute_id");

ALTER TABLE "product_attribute_values" ADD CONSTRAINT "unique_prod_attr" UNIQUE USING INDEX "unique_prod_attr_idx";

CREATE INDEX "prod_attr_values_idx" ON "product_attribute_values" USING BTREE ("value", "attribute_id", "product_id");
