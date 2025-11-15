DROP TABLE IF EXISTS tipo_cambio;

CREATE TABLE tipo_cambio (
                             id IDENTITY PRIMARY KEY,
                             moneda_origen VARCHAR(3),
                             moneda_destino VARCHAR(3),
                             tipo_cambio DECIMAL(10,4)
);

INSERT INTO tipo_cambio (moneda_origen, moneda_destino, tipo_cambio)
VALUES ('USD','PEN', 3.85),
       ('PEN','USD', 0.2597);

SELECT * FROM tipo_cambio;