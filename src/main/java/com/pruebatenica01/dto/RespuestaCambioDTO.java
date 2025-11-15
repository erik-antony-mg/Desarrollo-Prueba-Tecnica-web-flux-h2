package com.pruebatenica01.dto;

public record RespuestaCambioDTO(
        Double montoOriginal,
        Double montoConvertido,
        Double tipoCambio,
        String monedaOrigen,
        String monedaDestino
) {}

