CREATE TABLE IF NOT EXISTS rating (
	id varchar(255) NOT NULL PRIMARY KEY,
	user_id varchar(255) NOT NULL,
	product_id varchar(255) NOT NULL,
	rating int4 NOT NULL,
	review varchar(255) NOT NULL
);