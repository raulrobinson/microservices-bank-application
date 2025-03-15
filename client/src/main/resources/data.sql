drop table if exists clients;

-- H2 Embedded Database
CREATE TABLE clients (
    id UUID PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    name VARCHAR(255) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    gender CHAR(1) NULL, --CHECK (gender IN ('M', 'F')),
    age INTEGER NOT NULL CHECK (age > 0),
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    client_code VARCHAR(255) NOT NULL,
    client_type VARCHAR(255) NOT NULL,
    dni_type VARCHAR(255) NOT NULL
);

INSERT INTO clients (id, password, is_active, name, dni, gender, age, phone, address, client_code, client_type, dni_type) VALUES
      (RANDOM_UUID(), '123456', TRUE, 'Juan Perez', '72111222', 'M', 25, '12345678', 'Calle 1231', 'CLI-1712345678901', 'NATURAL_PERSON', 'CC'),
      (RANDOM_UUID(), '123456', TRUE, 'Jose Velez', '101202', 'M', 35, '11331133', 'Calle 1232', 'CLI-1712345678902', 'FOREIGN_PERSON', 'CE'),
      (RANDOM_UUID(), '123456', TRUE, 'Developers Team', '890100200-5', '', 30, '87654321', 'Calle 3211', 'CLI-1712345678903', 'PRIVATE_ENTITY', 'NIT');

-- Postgres Database
-- CREATE EXTENSION IF NOT EXISTS "pgcrypto"; -- Necesario para gen_random_uuid()
--
-- CREATE TABLE clients (
--      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--      password VARCHAR(255) NOT NULL,
--      is_active BOOLEAN NOT NULL DEFAULT TRUE,
--      name VARCHAR(255) NOT NULL,
--      dni VARCHAR(20) UNIQUE NOT NULL,
--      gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
--      age INTEGER NOT NULL CHECK (age > 0),
--      phone VARCHAR(20) NOT NULL,
--      address TEXT NOT NULL
-- );
--
-- INSERT INTO clients (password, is_active, name, dni, gender, age, phone, address) VALUES
--       ('123456', TRUE, 'Juan Perez', '111', 'M', 25, '12345678', 'Calle 1231'),
--       ('123456', TRUE, 'Jose Velez', '333', 'M', 35, '11331133', 'Calle 1232'),
--       ('123456', TRUE, 'Maria Lopez', '222', 'F', 30, '87654321', 'Calle 3211');

-- create table clients (
--     id serial primary key,
--     password varchar(255) not null,
--     is_active boolean not null,
--     name varchar(255) not null,
--     dni varchar(255) not null,
--     gender varchar(255) not null,
--     age integer not null,
--     phone varchar(255) not null,
--     address varchar(255) not null,
--     primary key (id)
-- );
--
-- insert into clients (password, is_active, name, dni, gender, age, phone, address) values
--     ('123456', true, 'Juan Perez', '111', 'M', 25, '12345678', 'Calle 1231'),
--     ('123456', true, 'Jose Velez', '333', 'M', 35, '11331133', 'Calle 1232'),
--     ('123456', true, 'Maria Lopez', '222', 'F', 30, '87654321', 'Calle 3211');
