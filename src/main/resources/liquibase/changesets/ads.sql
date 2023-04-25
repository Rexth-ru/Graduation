--liquibase formatted sql

--changeset akucher:create ads table
create table ad
(
    id          serial       not null primary key,
    title       varchar(255) not null,
    description varchar(255) not null,
    price       integer      not null,
    image       varchar,
    author_id      integer references user_entity (id)
)
