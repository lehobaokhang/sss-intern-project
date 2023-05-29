CREATE TABLE IF NOT EXISTS districts (
	id int4 NOT NULL PRIMARY KEY,
	district_name varchar(255) NOT NULL,
	district_full_name varchar(255) NOT NULL,
	province_id int4 NOT NULL,
    CONSTRAINT fk_districts_province_id FOREIGN KEY (province_id) REFERENCES provinces(id)
);