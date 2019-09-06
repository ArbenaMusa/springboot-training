CREATE SEQUENCE IF NOT EXISTS public.product_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1;

CREATE SEQUENCE IF NOT EXISTS public.file_upload_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1;

CREATE table IF NOT EXISTS public.product
(
    id                  integer not null default nextval('public.product_id_seq'::regclass),
    create_date_time    timestamp,
    deleted_date_time   timestamp,
    description         varchar(1000),
    record_status       varchar(255),
    update_date_time    timestamp,
    version             bigint,
    name                varchar(255) unique,
    product_description varchar(1000),
    unit_price          numeric(19, 2),
    brand_id            integer NOT NULL,
    platform_id         integer NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES public.brand (id),
    FOREIGN KEY (platform_id) REFERENCES public.platform (id),

    PRIMARY KEY (id)
);

CREATE table IF NOT EXISTS public.file_upload
(
    id                integer not null default nextval('public.product_id_seq'::regclass),
    create_date_time  timestamp,
    deleted_date_time timestamp,
    description       varchar(1000),
    record_status     varchar(255),
    update_date_time  timestamp,
    version           bigint,
    file_extension    varchar(255),
    file_name         varchar(255),
    file_path         varchar(1000),
    product_id        integer NOT NULL,
    FOREIGN KEY (product_id) REFERENCES public.product (id),

    PRIMARY KEY (id)
);

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Assassins Creed', null, 56.5, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'ac4-cover', 'D:\springboot-training/upload/ac4-cover.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'MORTAL KOMBAT', null, 49.5, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mortal-kombat', 'D:\springboot-training/upload/mortal-kombat.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Battlefield 4', null, 35, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'bf4-cover', 'D:\springboot-training/upload/bf4-cover.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Hitman 2', null, 9.99, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'hitman', 'D:\springboot-training/upload/hitman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'GTA V', null, 45.99, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'gtav', 'D:\springboot-training/upload/gtav.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'DIRT', null, 35, 6, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'dirt', 'D:\springboot-training/upload/dirt.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Black Ops 2', null, 7.5, 7, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'cod-blackops2', 'D:\springboot-training/upload/cod-blackops2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Modern Warfare 3', null, 12.5, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'call_of_duty333', 'D:\springboot-training/upload/call_of_duty333.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Spider-Man', null, 65, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'spiderman', 'D:\springboot-training/upload/spiderman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'PES 2019', null, 22, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'pes2019', 'D:\springboot-training/upload/pes2019.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Super Mario Bros 3', null, 32, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mario', 'D:\springboot-training/upload/mario.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Halo 4', null, 5, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'halo4', 'D:\springboot-training/upload/halo4.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Fifa 19', null, 25, 6, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'fifa19', 'D:\springboot-training/upload/fifa19.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Battlefield V', null, 43, 7, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'bf5', 'D:\springboot-training/upload/bf5.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Man of Medan', null, 34, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'man-of-medan', 'D:\springboot-training/upload/man-of-medan.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Control', null, 24, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'control', 'D:\springboot-training/upload/control.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Control', null, 24, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'control', 'D:\springboot-training/upload/control.jpg', currval('public.product_id_seq'));
COMMIT;