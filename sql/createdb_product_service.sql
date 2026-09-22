drop database if exists chohj_product;
create database chohj_product character set utf8;

drop table product;
CREATE TABLE product (
    id              BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    price           INT,
    stock_quantity  INT,
    is_lock         TINYINT(1) NOT NULL DEFAULT 0,
    member_id       BIGINT NOT NULL,
    created_date    DATETIME,
    updated_date    DATETIME
);
select * from product;