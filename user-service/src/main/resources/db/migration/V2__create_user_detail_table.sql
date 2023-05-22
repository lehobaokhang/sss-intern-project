CREATE TABLE IF NOT EXISTS user_detail (
	id varchar(255) NOT NULL PRIMARY KEY,
	district_id int4 NULL,
	dob date NULL,
	full_name varchar(255) NOT NULL,
	gender bool NULL,
	phone varchar(255) NULL,
	province_id int4 NULL,
	ward_id int4 NULL
);