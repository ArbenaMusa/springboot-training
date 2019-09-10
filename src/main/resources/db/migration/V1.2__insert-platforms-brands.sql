CREATE SEQUENCE IF NOT EXISTS public.platform_id_seq
INCREMENT 1
MINVALUE 1
START 1;

CREATE SEQUENCE IF NOT EXISTS public.brand_id_seq
INCREMENT 1
MINVALUE 1
START 1;

create table IF NOT EXISTS public.platform
(
	id integer not null default nextval('public.platform_id_seq'::regclass),
	create_date_time timestamp,
	deleted_date_time timestamp,
	comment varchar(1000),
	record_status varchar(255),
	update_date_time timestamp,
	version bigint,
	name varchar(255) unique,
	PRIMARY KEY (id)
);

create table IF NOT EXISTS public.brand
(
	id integer not null default nextval('public.brand_id_seq'::regclass),
	create_date_time timestamp,
	deleted_date_time timestamp,
	comment varchar(1000),
	record_status varchar(255),
	update_date_time timestamp,
	version bigint,
	name varchar(255) unique,
	brand_link varchar(1000),
	image_link varchar(1000),
	PRIMARY KEY (id)
);

BEGIN;
INSERT INTO platform VALUES (nextval('public.platform_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'PC');
INSERT INTO platform VALUES (nextval('public.platform_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'XBOX');
INSERT INTO platform VALUES (nextval('public.platform_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'PS4');
COMMIT;

BEGIN;
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'EA', null, NULL);
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Rockstar', null, NULL);
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Activision', null, NULL);
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Konami', null, NULL);
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Blizzard', null, NULL);
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Bethesda', null, NULL);
INSERT INTO brand VALUES (nextval('public.brand_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'NaughtyDog', null, NULL);
COMMIT;