package com.pruebatenica01.repository;

import com.pruebatenica01.auditoria.AuditoriaCambio;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AuditoriaCambioRepository extends R2dbcRepository<AuditoriaCambio, Long> {
    Flux<AuditoriaCambio> findAllByOrderByFechaDesc();
}
