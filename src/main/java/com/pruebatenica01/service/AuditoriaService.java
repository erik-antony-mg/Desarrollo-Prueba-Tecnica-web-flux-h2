package com.pruebatenica01.service;

import com.pruebatenica01.auditoria.AuditoriaCambio;
import com.pruebatenica01.repository.AuditoriaCambioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaCambioRepository repo;

    public Mono<AuditoriaCambio> registrar(String usuario,
                                           String accion,
                                           String detalle) {

        AuditoriaCambio audit = AuditoriaCambio.builder()
                .usuario(usuario)
                .accion(accion)
                .detalle(detalle)
                .fecha(LocalDateTime.now())
                .build();

        return repo.save(audit);
    }
}
