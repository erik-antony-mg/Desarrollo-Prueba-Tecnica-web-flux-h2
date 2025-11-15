package com.pruebatenica01.controller;

import com.pruebatenica01.auditoria.AuditoriaCambio;
import com.pruebatenica01.repository.AuditoriaCambioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaCambioRepository auditoriaCambioRepository;

    public AuditoriaController(AuditoriaCambioRepository auditoriaCambioRepository) {
        this.auditoriaCambioRepository = auditoriaCambioRepository;
    }


    @GetMapping("/listar")
    public Flux<AuditoriaCambio> listarAuditoria() {
        return auditoriaCambioRepository.findAllByOrderByFechaDesc();
    }
}
