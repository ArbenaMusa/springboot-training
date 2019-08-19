CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
INCREMENT 1
MINVALUE 1
START 1;

create table IF NOT EXISTS public.role
(
	id integer not null default nextval('public.role_id_seq'::regclass),
	create_date_time timestamp,
	deleted_date_time timestamp,
	description varchar(1000),
	record_status varchar(255),
	update_date_time timestamp,
	version bigint,
	name varchar(255) unique,
	role_description varchar(255)
);

BEGIN;
INSERT INTO role VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'ADMIN', 'Admin role');
COMMIT;