CREATE TABLE IF NOT EXISTS sedes (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    estado VARCHAR(30) NOT NULL
    );