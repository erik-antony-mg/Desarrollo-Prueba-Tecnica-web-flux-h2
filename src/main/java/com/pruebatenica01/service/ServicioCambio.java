package com.pruebatenica01.service;

import com.pruebatenica01.auditoria.AuditoriaCambio;
import com.pruebatenica01.dto.RespuestaCambioDTO;
import com.pruebatenica01.dto.SolicitudCambioDTO;
import com.pruebatenica01.repository.AuditoriaCambioRepository;
import com.pruebatenica01.repository.TipoCambioRepositorio;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ServicioCambio {

    private final TipoCambioRepositorio repo;
    private final AuditoriaCambioRepository auditoriaRepo;

    public ServicioCambio(TipoCambioRepositorio repo, AuditoriaCambioRepository auditoriaRepo) {
        this.repo = repo;
        this.auditoriaRepo = auditoriaRepo;
    }

    public Mono<RespuestaCambioDTO> convertir(SolicitudCambioDTO dto) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())   // usuario del JWT
                .flatMap(usuario ->

                        repo.findByMonedaOrigenAndMonedaDestino(dto.monedaOrigen(), dto.monedaDestino())
                                .switchIfEmpty(Mono.error(new RuntimeException("Tipo de cambio no encontrado")))
                                .flatMap(tc -> {

                                    double convertido = dto.monto() * tc.getTipoCambio();

                                    // Crear detalle con el formato de tu entidad actual
                                    String detalle = String.format(
                                            "Conversión %s → %s | monto %.2f | convertido %.2f | tasa %.4f",
                                            dto.monedaOrigen(),
                                            dto.monedaDestino(),
                                            dto.monto(),
                                            convertido,
                                            tc.getTipoCambio()
                                    );

                                    AuditoriaCambio audit = AuditoriaCambio.builder()
                                            .usuario(usuario)
                                            .accion("CONVERTIR")  // acción clara
                                            .detalle(detalle)
                                            .fecha(LocalDateTime.now())
                                            .build();

                                    return auditoriaRepo.save(audit)
                                            .thenReturn(
                                                    new RespuestaCambioDTO(
                                                            dto.monto(),
                                                            convertido,
                                                            tc.getTipoCambio(),
                                                            dto.monedaOrigen(),
                                                            dto.monedaDestino()
                                                    )
                                            );
                                })
                );
    }

}
