create table if not exists users (
    id bigserial primary key not null,
    username varchar(128) not null unique,
    password varchar(64) not null,
    enabled boolean not null default false
);

create table if not exists roles (
    id bigserial primary key not null,
    name varchar(128) not null unique
);

create table if not exists users_roles (
    user_id bigint references users(id),
    role_id bigint references roles(id)
);

insert into users (username, password, enabled)
VALUES
('admin', '$2a$12$ceaV6lsFESjPYbj0sxvMl.1vZ/BKOrzRkY0Iue2Q9B325OWUhWJ2u', true),
('manager', '$2a$12$qGqr6T2/cXdD/HUT9w2KpOpnkhahpNbY/TXLx2YaHa2XgI0cp6zaW', true),
('user', '$2a$12$C4CmyvinB6utYfp7WAZCqe3qrdX5KDyvldbesg8mxH.hqGBk9eCVG', true);

insert into roles (name)
values ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_USER');

insert into users_roles
values (1,1), (2,2), (3,3);