CREATE TABLE IF NOT EXISTS users (
	id varchar(255) NOT NULL PRIMARY KEY,
	created_at timestamp NOT NULL,
	email varchar(255) NOT NULL UNIQUE,
	"password" varchar(255) NOT NULL,
	username varchar(255) NOT NULL UNIQUE,
	user_detail_id varchar(255) NULL,
	CONSTRAINT fk_users_detail_id FOREIGN KEY (user_detail_id) REFERENCES user_detail(id)
);