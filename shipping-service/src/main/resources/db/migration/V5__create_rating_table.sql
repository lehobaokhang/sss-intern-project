CREATE TABLE IF NOT EXISTS rating (
	id varchar(255) NOT NULL PRIMARY KEY,
	order_id varchar(255) NOT NULL,
	status varchar(255) NOT NULL
);