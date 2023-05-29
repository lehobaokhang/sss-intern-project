CREATE TABLE IF NOT EXISTS ship (
	id varchar(255) NOT NULL PRIMARY KEY,
	order_id varchar(255) NOT NULL UNIQUE,
	status varchar(255) NOT NULL
);