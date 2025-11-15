package com.pruebatenica01.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("tipos_cambio")
public class TipoCambio {
    @Id
    private Long id;
    @NotBlank(message = "La moneda de origen no puede estar vacía")
    private String monedaOrigen;

    @NotBlank(message = "La moneda de destino no puede estar vacía")
    private String monedaDestino;

    @NotNull(message = "El tipo de cambio es obligatorio")
    @Positive(message = "El tipo de cambio debe ser mayor a 0")
    private Double tipoCambio;
}
