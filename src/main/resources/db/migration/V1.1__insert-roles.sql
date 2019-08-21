CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
INCREMENT 1
MINVALUE 1
START 1;

CREATE SEQUENCE IF NOT EXISTS public.permission_id_seq
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
	role_description varchar(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.permission
(
  id integer NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
  create_date_time timestamp without time zone,
  deleted_date_time timestamp without time zone,
  description character varying(1000),
  record_status character varying(255),
  update_date_time timestamp without time zone,
  version bigint,
  action character varying(255),
  module character varying(255),
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.permission_role
(
  permission_id integer NOT NULL,
  role_id integer NOT NULL,
  FOREIGN KEY (role_id) REFERENCES public.role (id),
  FOREIGN KEY (permission_id) REFERENCES public.permission (id)
);

BEGIN;
INSERT INTO role VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'ADMIN', 'Admin role');
COMMIT;