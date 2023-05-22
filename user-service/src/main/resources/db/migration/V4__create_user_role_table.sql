CREATE TABLE IF NOT EXISTS user_role (
	user_id varchar(255) NOT NULL,
	role_id varchar(255) NOT NULL,
	CONSTRAINT fk_user_role PRIMARY KEY (user_id, role_id),
	CONSTRAINT pk_user_role_role_id FOREIGN KEY (role_id) REFERENCES public.roles(id),
	CONSTRAINT pk_user_role_user_id FOREIGN KEY (user_id) REFERENCES public.users(id)
);