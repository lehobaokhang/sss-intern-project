CREATE TABLE IF NOT EXISTS order_product (
	id varchar(255) NOT NULL PRIMARY KEY,
	product_id varchar(255) NOT NULL,
	quantity int4 NOT NULL,
	order_id varchar(255) NOT NULL,
	price int4 NOT NULL,
	CONSTRAINT fk_order_product_id FOREIGN KEY (order_id) REFERENCES orders(id)
);