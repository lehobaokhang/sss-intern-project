CREATE TABLE IF NOT EXISTS orders (
	id varchar(255) NOT NULL PRIMARY KEY,
	created_at timestamp NOT NULL,
	price_total int4 NOT NULL,
	shipping_fee int4 NULL,
	user_id varchar(255) NOT NULL
);