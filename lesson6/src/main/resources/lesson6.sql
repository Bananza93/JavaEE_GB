DROP TABLE IF EXISTS client_product;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS clients;

CREATE TABLE IF NOT EXISTS products (
	id bigserial PRIMARY KEY,
	title varchar(255),
	price integer
);

CREATE TABLE IF NOT EXISTS clients (
	id bigserial PRIMARY KEY,
	name varchar(255)
);

CREATE TABLE IF NOT EXISTS client_product (
	client_id bigint,
	product_id bigint,
	final_price integer,
	
	FOREIGN KEY (client_id) REFERENCES clients (id),
	FOREIGN KEY (product_id) REFERENCES products (id)
);

INSERT INTO products (title, price)
VALUES  ('PSU', 100),
		('Motherboard', 200),
		('Memory RAM', 300),
		('CPU', 400),
		('Graphic card', 500);
		
INSERT INTO clients (name)
VALUES  ('Andrey'),
		('Ivan'),
		('Mariya');

INSERT INTO client_product
VALUES  (1, 1, 100),
		(1, 3, 300),
		(2, 5, 500),
		(3, 2, 200),
		(3, 3, 300),
		(3, 4, 300);