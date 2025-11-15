package com.pruebatenica01.dto;

public record SolicitudCambioDTO(
        String monedaOrigen,
        String monedaDestino,
        Double monto
) {}

