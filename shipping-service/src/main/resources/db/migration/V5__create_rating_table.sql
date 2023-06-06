CREATE TABLE IF NOT EXISTS rating (
	id varchar(255) NOT NULL PRIMARY KEY,
	user_id varchar(255) NOT NULL,
	product_id varchar(255) NOT NULL,
	rating int4 NOT NULL,
	review varchar(255) NOT NULL,
	CONSTRAINT unique_user_id_product_id UNIQUE (user_id, product_id)
);