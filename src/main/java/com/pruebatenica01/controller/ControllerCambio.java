package com.pruebatenica01.controller;

import com.pruebatenica01.dto.RespuestaCambioDTO;
import com.pruebatenica01.dto.SolicitudCambioDTO;
import com.pruebatenica01.entity.TipoCambio;
import com.pruebatenica01.repository.TipoCambioRepositorio;
import com.pruebatenica01.service.AuditoriaService;
import com.pruebatenica01.service.ServicioCambio;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cambio")
public class ControllerCambio {

    private final ServicioCambio servicio;
    private final TipoCambioRepositorio repo;
    private final AuditoriaService auditoriaService;

    public ControllerCambio(ServicioCambio servicio, TipoCambioRepositorio repo, AuditoriaService auditoriaService) {
        this.servicio = servicio;
        this.repo = repo;
        this.auditoriaService = auditoriaService;
    }


    @PostMapping("/convertir")
    public Mono<ResponseEntity<RespuestaCambioDTO>> convertir(
            @RequestBody SolicitudCambioDTO solicitud) {

        return servicio.convertir(solicitud)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping("/registrar")
    public Mono<ResponseEntity<?>> registrar(@Valid @RequestBody TipoCambio tipoCambio) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> {

                    String usuario = auth.getName();  // ← este es el usuario del JWT

                    return repo.save(tipoCambio)
                            .flatMap(guardado ->
                                    auditoriaService.registrar(
                                            usuario,
                                            "CREAR",
                                            guardado.getMonedaOrigen()
                                                    + " → "
                                                    + guardado.getMonedaDestino()
                                                    + " tasa: " + guardado.getTipoCambio()
                                    ).thenReturn(guardado)
                            );
                })
                .map(guardado -> {

                    String msg = guardado.getMonedaOrigen()
                            + " equivale a "
                            + guardado.getTipoCambio()
                            + " "
                            + guardado.getMonedaDestino();

                    Map<String, Object> body = new HashMap<>();
                    body.put("status", 200);
                    body.put("mensaje", msg);

                    return ResponseEntity.ok(body);
                });
    }

    @PutMapping("/actualizar")
    public Mono<ResponseEntity<?>> actualizar(@Valid @RequestBody TipoCambio tipoCambioActualizado) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> {

                    String usuario = auth.getName(); // usuario del JWT

                    return repo.findByMonedaOrigenAndMonedaDestino(
                                    tipoCambioActualizado.getMonedaOrigen(),
                                    tipoCambioActualizado.getMonedaDestino()
                            )
                            .switchIfEmpty(Mono.error(
                                    new RuntimeException("No existe el tipo de cambio para esas monedas")
                            ))
                            .flatMap(existente -> {

                                double tasaAnterior = existente.getTipoCambio();

                                existente.setTipoCambio(tipoCambioActualizado.getTipoCambio());

                                return repo.save(existente)
                                        .flatMap(guardado -> {

                                            // ------- Auditoría -------
                                            String detalle = String.format(
                                                    "Modificación %s → %s | tasa anterior %.4f | tasa nueva %.4f",
                                                    guardado.getMonedaOrigen(),
                                                    guardado.getMonedaDestino(),
                                                    tasaAnterior,
                                                    guardado.getTipoCambio()
                                            );

                                            return auditoriaService.registrar(
                                                    usuario,
                                                    "MODIFICAR",
                                                    detalle
                                            ).thenReturn(
                                                    // ESTE ES EL OBJETO QUE RETORNAS
                                                    Map.of(
                                                            "monedaOrigen", guardado.getMonedaOrigen(),
                                                            "monedaDestino", guardado.getMonedaDestino(),
                                                            "tipoCambioAnterior", tasaAnterior,
                                                            "tipoCambio", guardado.getTipoCambio()
                                                    )
                                            );
                                        });
                            });
                })
                .map(respuesta -> ResponseEntity.ok(respuesta));
    }



}
