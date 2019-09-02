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
    id                integer not null default nextval('public.role_id_seq'::regclass),
    create_date_time  timestamp,
    deleted_date_time timestamp,
    description       varchar(1000),
    record_status     varchar(255),
    update_date_time  timestamp,
    version           bigint,
    name              varchar(255) unique,
    role_description  varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.permission
(
    id                integer NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
    create_date_time  timestamp without time zone,
    deleted_date_time timestamp without time zone,
    description       character varying(1000),
    record_status     character varying(255),
    update_date_time  timestamp without time zone,
    version           bigint,
    action            character varying(255),
    module            character varying(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.permission_role
(
    id                integer NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
    create_date_time  timestamp without time zone,
    deleted_date_time timestamp without time zone,
    description       character varying(1000),
    record_status     character varying(255),
    update_date_time  timestamp without time zone,
    version           bigint,
    role_id           integer NOT NULL,
    permission_id     integer NOT NULL,
    FOREIGN KEY (role_id) REFERENCES public.role (id),
    FOREIGN KEY (permission_id) REFERENCES public.permission (id)
);

BEGIN;
INSERT INTO role
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'ADMIN', 'Admin role');
INSERT INTO role
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'CUSTOMER', 'Customer role');
COMMIT;

-- BEGIN INSERT OF ADMIN PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'admin');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'admin');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'admin');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'admin');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 0, 0);
INSERT INTO permission_role
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 0, 1);
INSERT INTO permission_role
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 0, 2);
INSERT INTO permission_role
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 0, 3);
COMMIT;
-- END INSERT OF ADMIN PERMISSIONS

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'role');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'role');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'role');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'role');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'permission');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'permission');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'permission');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'permission');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'profile');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'profile');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'profile');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'profile');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'address');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'address');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'address');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'address');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'phone');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'phone');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'phone');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'phone');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'product');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'product');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'product');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'product');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'platform');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'platform');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'platform');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'platform');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'brand');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'brand');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'brand');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'brand');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'order');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'order');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'order');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'order');
COMMIT;

BEGIN;
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'cartitem');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'cartitem');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'cartitem');
INSERT INTO permission
VALUES (nextval('public.role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'cartitem');
COMMIT;
