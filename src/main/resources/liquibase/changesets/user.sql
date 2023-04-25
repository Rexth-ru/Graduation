--liquibase formatted sql
--changeset akucher:create user table
create table user_entity
(
    id         serial       not null primary key,
    email      varchar(255) not null,
    password   varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    phone      varchar(255) not null,
    role       varchar,
    enabled    boolean,
    image      varchar
)



