CREATE TABLE IF NOT EXISTS product (
	id varchar(255) NOT NULL PRIMARY KEY,
	is_deleted bool NOT NULL DEFAULT false,
	price int4 NOT NULL,
	product_image bytea NULL,
	product_name varchar(255) NOT NULL,
	product_size varchar(255) NOT NULL,
	product_weight int4 NOT NULL,
	quantity int4 NOT NULL,
	seller_id varchar(255) NOT NULL,
	category_id varchar(255) NOT NULL,
	CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);