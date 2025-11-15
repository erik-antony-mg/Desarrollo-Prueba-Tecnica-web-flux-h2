package com.pruebatenica01.repository;

import com.pruebatenica01.entity.TipoCambio;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface TipoCambioRepositorio extends R2dbcRepository<TipoCambio, Long> {
    Mono<TipoCambio> findByMonedaOrigenAndMonedaDestino(String origen, String destino);
}

