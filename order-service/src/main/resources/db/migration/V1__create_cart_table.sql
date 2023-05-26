CREATE TABLE IF NOT EXISTS cart (
	id varchar(255) NOT NULL PRIMARY KEY,
	price int4 NOT NULL,
	product_id varchar(255) NOT NULL,
	quantity int4 NOT NULL,
	user_id varchar(255) NOT NULL,
	CONSTRAINT unique_cart_user_product UNIQUE (user_id, product_id)
);