--liquibase formatted sql

--changeset akucher:create comment table
create table comment_entity
(
    id         serial       not null primary key,
    text       varchar(255) not null,
    created_at timestamp    not null,
    author_id  integer references user_entity (id),
    ad_id      integer references ad (id)
)