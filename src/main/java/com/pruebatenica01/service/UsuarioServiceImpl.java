package com.pruebatenica01.service;

import com.pruebatenica01.entity.UsuarioEntity;
import com.pruebatenica01.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Mono<UsuarioEntity> createCustomer(UsuarioEntity usuarioEntity) {
        return usuarioRepository.existsByEmail(usuarioEntity.getEmail())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new IllegalArgumentException("El correo ya está registrado"));
                    }
                    return usuarioRepository.save(usuarioEntity);
                });
    }



    @Override
    public Mono<UsuarioEntity> readRolesByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    @Override
    public Mono<Void> deleteCustomer(Long id) {
        return null;
    }

    @Override
    public Flux<UsuarioEntity> listarUsuarios() {
        return usuarioRepository.findAll();

    }
}
