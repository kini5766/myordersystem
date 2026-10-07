drop database if exists chohj_board;
create database chohj_board character set utf8;

DROP TABLE board;
CREATE TABLE board (
    no                   BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    board_type           VARCHAR(15) NOT NULL,
    title                VARCHAR(255) NOT NULL,
    content              MEDIUMTEXT NOT NULL,
    created_date         TIMESTAMP,
    updated_date         TIMESTAMP
);
select * from board;

DROP TABLE board_display_post;
CREATE TABLE board_display_post (
    no                 BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    board_no           BIGINT NOT NULL,
    display_order      INT NOT NULL,
    display_start_date TIMESTAMP,
    display_end_date   TIMESTAMP,
    active             BOOLEAN NOT NULL DEFAULT TRUE,
    created_date       TIMESTAMP,
    updated_date       TIMESTAMP,

    CONSTRAINT fk_board_display_post_board FOREIGN KEY (board_no) REFERENCES board (no),
    CONSTRAINT ck_board_display_post_period CHECK (display_start_date IS NULL OR display_end_date IS NULL OR display_start_date <= display_end_date)
);
CREATE INDEX ix_board_display_post_board_order ON board_display_post (board_no, display_order);
select * from board_display_post;