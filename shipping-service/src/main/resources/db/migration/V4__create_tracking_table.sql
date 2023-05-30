CREATE TABLE IF NOT EXISTS tracking (
	id varchar(255) NOT NULL PRIMARY KEY,
	locate int4 NOT NULL,
	ship_id varchar(255),
	tracking_at timestamp NOT NULL,
	CONSTRAINT fk_tracking_ship_id FOREIGN KEY (ship_id) REFERENCES ship(id),
	CONSTRAINT fk_tracking_locate FOREIGN KEY (locate) REFERENCES districts(id),
	CONSTRAINT unique_locate_ship_id UNIQUE (locate, ship_id)
);