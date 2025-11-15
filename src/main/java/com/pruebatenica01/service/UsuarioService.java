package com.pruebatenica01.service;

import com.pruebatenica01.entity.UsuarioEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public interface UsuarioService {

    Mono<UsuarioEntity> createCustomer(UsuarioEntity usuarioEntity);
    Mono<UsuarioEntity> readRolesByEmail(String email);
    Mono<Void> deleteCustomer(Long id);
    Flux<UsuarioEntity> listarUsuarios();


}
