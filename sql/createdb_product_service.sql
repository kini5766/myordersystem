drop database if exists chohj_product;
create database chohj_product character set utf8;

CREATE TABLE product (
    id              BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    price           INT,
    stock_quantity  INT,
    is_lock         TINYINT(1) NOT NULL DEFAULT 0,
    member_id       BIGINT NOT NULL,
    creation_date   DATETIME,
    update_date     DATETIME
);