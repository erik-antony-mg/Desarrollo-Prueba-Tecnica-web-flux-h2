package com.pruebatenica01.repository;

import com.pruebatenica01.entity.UsuarioEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends R2dbcRepository <UsuarioEntity, Long> {
    @Query("SELECT * FROM usuarios WHERE email = :email")
    Mono<UsuarioEntity> findByEmail(String email);

    @Query("SELECT COUNT(*) > 0 FROM usuarios WHERE email = :email")
    Mono<Boolean> existsByEmail(String email);
}
