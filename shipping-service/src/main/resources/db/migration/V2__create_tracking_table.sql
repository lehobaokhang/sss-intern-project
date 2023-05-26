CREATE TABLE IF NOT EXISTS tracking (
	id varchar(255) NOT NULL PRIMARY KEY,
	locate varchar(255) NOT NULL,
	ship_id varchar(255) NOT NULL,
	CONSTRAINT fk_tracking_ship_id FOREIGN KEY (ship_id) REFERENCES ship(id)
);