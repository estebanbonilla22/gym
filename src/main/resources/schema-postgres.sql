CREATE TABLE IF NOT EXISTS sedes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    estado VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS maquinas (
                                        id BIGSERIAL PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    sede_id BIGINT NOT NULL
    );