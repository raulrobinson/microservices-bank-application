drop table if exists clients;

create table clients (
    id serial primary key,
    password varchar(255) not null,
    is_active boolean not null,
    name varchar(255) not null,
    dni varchar(255) not null,
    gender varchar(255) not null,
    age integer not null,
    phone varchar(255) not null,
    address varchar(255) not null,
    primary key (id)
);

insert into clients (password, is_active, name, dni, gender, age, phone, address) values
    ('123456', true, 'Juan Perez', '111', 'M', 25, '12345678', 'Calle 1231'),
    ('123456', true, 'Maria Lopez', '222', 'F', 30, '87654321', 'Calle 3211');
insert into clients (password, is_active, name, dni, gender, age, phone, address) values
    ('123456', true, 'Jose Perez', '333', 'M', 35, '11331133', 'Calle 1232'),
    ('123456', true, 'Mercedes Lopez', '444', 'F', 45, '11441144', 'Calle 3212');
