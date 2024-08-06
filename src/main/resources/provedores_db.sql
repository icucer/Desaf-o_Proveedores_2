CREATE DATABASE provedoresdb;

USE provedoresdb;

CREATE TABLE provedores
(
    id                int primary key,
    nombre            varchar(255),
    rut               varchar(50),
    direccion         varchar(255),
    correo            varchar(100),
    telefono          varchar(20),
    contacto          varchar(100),
    telefono_contacto varchar(20)
);
