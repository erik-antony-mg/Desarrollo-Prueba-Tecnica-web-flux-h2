CREATE TABLE usuarios (
                          id BIGINT PRIMARY KEY IDENTITY,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          roles VARCHAR(255) -- Ejemplo: 'ADMIN,USER'
);

CREATE TABLE tipos_cambio (
                              id BIGINT PRIMARY KEY IDENTITY,
                              moneda_origen VARCHAR(10),
                              moneda_destino VARCHAR(10),
                              tipo_cambio DOUBLE
);

CREATE TABLE auditorias_cambio (
                                   id BIGINT PRIMARY KEY IDENTITY,
                                   usuario VARCHAR(100) NOT NULL,   -- usuario del JWT
                                   accion VARCHAR(20) NOT NULL,     -- CREAR, ACTUALIZAR, ELIMINAR
                                   detalle VARCHAR(500) NOT NULL,   -- texto completo de la operación
                                   fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
