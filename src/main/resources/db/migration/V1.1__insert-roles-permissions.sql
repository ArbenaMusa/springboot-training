CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1;

CREATE SEQUENCE IF NOT EXISTS public.permission_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1;

CREATE SEQUENCE IF NOT EXISTS public.role_permission_id_seq
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

BEGIN;
INSERT INTO role
VALUES (nextval('role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'ADMIN', 'Admin role');
INSERT INTO role
VALUES (nextval('role_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'CUSTOMER', 'Customer role');
COMMIT;

CREATE TABLE IF NOT EXISTS public.permission_role
(
    id                integer NOT NULL DEFAULT nextval('public.role_permission_id_seq'::regclass),
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

-- BEGIN INSERT OF ADMIN CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'admin');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'admin');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'admin');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'admin');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 1);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 2);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 3);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 4);
COMMIT;
-- END INSERT OF ADMIN PERMISSIONS

-- BEGIN INSERT OF ROLE CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'role');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'role');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'role');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'role');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 5);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 6);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 7);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 8);
COMMIT;
-- END INSERT OF ROLE CRUD PERMISSIONS

-- BEGIN INSERT OF PERMISSION CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'permission');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'permission');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'permission');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'permission');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 9);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 10);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 11);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 12);
COMMIT;
-- END INSERT OF PERMISSION CRUD PERMISSIONS

-- BEGIN INSERT OF PROFILE CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'profile');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'profile');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'profile');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'profile');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 13);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 14);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 15);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 16);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 13);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 14);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 15);
COMMIT;
-- END INSERT OF PROFILE CRUD PERMISSIONS

-- BEGIN INSERT OF ADDRESS CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'address');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'address');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'address');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'address');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 17);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 18);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 19);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 20);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 17);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 18);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 19);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 20);
COMMIT;
-- END INSERT OF ADDRESS CRUD PERMISSIONS

-- BEGIN INSERT OF PHONE CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'phone');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'phone');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'phone');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'phone');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 21);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 22);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 23);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 24);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 21);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 22);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 23);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 24);
COMMIT;
-- END INSERT OF PHONE CRUD PERMISSIONS

-- BEGIN INSERT OF PRODUCT CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'product');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'product');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'product');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'product');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 25);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 26);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 27);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 28);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 26);
COMMIT;
-- END INSERT OF PRODUCT CRUD PERMISSIONS

-- BEGIN INSERT OF PLATFORM CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'platform');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'platform');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'platform');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'platform');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 29);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 30);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 31);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 32);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 30);
COMMIT;
--END INSERT OF PLATFORM CRUD PERMISSIONS

--BEGIN INSERT OF BRAND CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'brand');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'brand');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'brand');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'brand');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 33);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 34);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 35);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 36);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 34);
COMMIT;
--END INSERT OF BRAND CRUD PERMISSIONS

--BEGIN INSERT OF ORDER CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'order');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'order');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'order');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'order');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 37);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 38);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 39);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 40);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 37);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 38);
COMMIT;
--END INSERT OF ORDER CRUD PERMISSIONS

--BEGIN INSERT OF CARTITEM CRUD PERMISSIONS
BEGIN;
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'create', 'cartitem');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'read', 'cartitem');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'update', 'cartitem');
INSERT INTO permission
VALUES (nextval('permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'delete', 'cartitem');
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 41);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 42);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 43);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 1, 44);
COMMIT;

BEGIN;
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 41);
INSERT INTO permission_role
VALUES (nextval('public.role_permission_id_seq'), now(), null, null, 'ACTIVE', null, 0, 2, 42);
COMMIT;
--END INSERT OF CARTITEM CRUD PERMISSIONS