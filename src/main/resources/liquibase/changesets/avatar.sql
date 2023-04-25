--liquibase formatted sql

--changeset akucher:create avatar table
create table avatar(

    image      varchar(255)  not null  primary key,
    view       bytea)