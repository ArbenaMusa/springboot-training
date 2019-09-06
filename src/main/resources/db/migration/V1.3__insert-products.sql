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