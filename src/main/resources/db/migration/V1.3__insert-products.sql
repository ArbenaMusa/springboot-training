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

create index if not exists name_idx
    on product (name);

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
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'ac4-cover.jpg', 'D:\springboot-training/upload/ac4-cover.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'MORTAL KOMBAT', null, 49.5, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mortal-kombat.jpg', 'D:\springboot-training/upload/mortal-kombat.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Battlefield 4', null, 35, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'bf4-cover.jpg', 'D:\springboot-training/upload/bf4-cover.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Hitman 2', null, 9.99, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'hitman.jpg', 'D:\springboot-training/upload/hitman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'GTA V', null, 45.99, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'gtav.jpg', 'D:\springboot-training/upload/gtav.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'DIRT', null, 35, 6, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'dirt.jpg', 'D:\springboot-training/upload/dirt.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Black Ops 2', null, 7.5, 7, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'cod-blackops2.jpg', 'D:\springboot-training/upload/cod-blackops2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Modern Warfare 3', null, 12.5, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'call_of_duty333.jpg', 'D:\springboot-training/upload/call_of_duty333.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Spider-Man', null, 65, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'spiderman.jpg', 'D:\springboot-training/upload/spiderman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'PES 2019', null, 22, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'pes2019.jpg', 'D:\springboot-training/upload/pes2019.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Super Mario Bros 3', null, 32, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mario.jpg', 'D:\springboot-training/upload/mario.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Halo 4', null, 5, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'halo4.jpg', 'D:\springboot-training/upload/halo4.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Fifa 19', null, 25, 6, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'fifa19.jpg', 'D:\springboot-training/upload/fifa19.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Battlefield V', null, 43, 7, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'bf5.jpg', 'D:\springboot-training/upload/bf5.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Man of Medan', null, 34, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'man-of-medan.jpg', 'D:\springboot-training/upload/man-of-medan.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Batman Arkham Knight', null, 27, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'batman.jpg', 'D:\springboot-training/upload/batman.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Cyberpunk 2077', null, 67, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'cyberpunk2077.jpg', 'D:\springboot-training/upload/cyberpunk2077.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'GTA IV', null, 8, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'gta-iv.jpg', 'D:\springboot-training/upload/gta-iv.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Injustice 2', null, 53, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'injustice.jpg', 'D:\springboot-training/upload/injustice.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Wolfenstein', null, 67, 6, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'wolfenstein.jpg', 'D:\springboot-training/upload/wolfenstein.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Star Wars', null, 51, 7, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'starwars.jpg', 'D:\springboot-training/upload/starwars.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Assassins Creed Rogue', null, 8, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'acrogue.jpg', 'D:\springboot-training/upload/acrogue.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'The Witcher 3', null, 30, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'witcher.jpg', 'D:\springboot-training/upload/witcher.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Mortal Kombat 9', null, 29, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'mk9.jpg', 'D:\springboot-training/upload/mk9.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Deus Ex: Mankind Divided', null, 47.99, 3, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'deus-ex.jpg', 'D:\springboot-training/upload/deus-ex.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Resident Evil 2', null, 15.99, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'resident-evil-2.jpg', 'D:\springboot-training/upload/resident-evil-2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Borderlands 3', null, 49.99, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'borderlands-3.jpg', 'D:\springboot-training/upload/borderlands-3.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Dishonored 2', null, 30.50, 6, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'dishonored2.jpg', 'D:\springboot-training/upload/dishonored2.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Just Cause 4', null, 22, 4, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'jc4.jpg', 'D:\springboot-training/upload/jc4.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'DOOM', null, 19.99, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'doom.jpg', 'D:\springboot-training/upload/doom.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Assassins Creed Odyssey', null, 59.99, 5, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'asodyssey.jpg', 'D:\springboot-training/upload/asodyssey.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Shadow of Mordor: Middle Earth', null, 30, 1, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'shadowm.jpg', 'D:\springboot-training/upload/shadowm.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Ghost Recon: Wildlands', null, 17, 7, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'grw.jpg', 'D:\springboot-training/upload/grw.jpg', currval('public.product_id_seq'));
COMMIT;

BEGIN;
INSERT INTO product VALUES (nextval('public.product_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'Kingdom Come: Deliverance', null, 14, 2, 1);
INSERT INTO file_upload VALUES (nextval('public.file_upload_id_seq'), now(), null, null, 'ACTIVE', null, 0, 'jpg', 'kcd.jpg', 'D:\springboot-training/upload/kcd.jpg', currval('public.product_id_seq'));
COMMIT;