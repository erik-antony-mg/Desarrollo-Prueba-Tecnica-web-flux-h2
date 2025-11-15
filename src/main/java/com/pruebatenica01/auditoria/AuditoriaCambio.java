package com.pruebatenica01.auditoria;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@Table("auditorias_cambio")
public class AuditoriaCambio {
    @Id
    private Long id;

    private String usuario;
    private String accion;   // CREAR, MODIFICAR, ELIMINAR
    private String detalle;  // "USD → PEN con tasa 3.77"
    private LocalDateTime fecha;
}
