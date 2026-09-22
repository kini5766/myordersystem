drop database if exists chohj_ordering;
create database chohj_ordering character set utf8;

CREATE TABLE ordering (
    id              BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id      BIGINT NOT NULL,
    member_id       BIGINT NOT NULL,
    quantity        INT NOT NULL,
    payment         INT NOT NULL,
    order_status    VARCHAR(255),
    created_date    DATETIME,
    updated_date    DATETIME
);