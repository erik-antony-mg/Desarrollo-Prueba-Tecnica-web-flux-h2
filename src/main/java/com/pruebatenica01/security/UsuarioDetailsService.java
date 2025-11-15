package com.pruebatenica01.security;

import com.pruebatenica01.repository.UsuarioRepository;
import com.pruebatenica01.service.UsuarioService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Service
public class UsuarioDetailsService implements ReactiveUserDetailsService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

//    @Override
//    public Mono<UserDetails> findByUsername(String email) {
//        return this.usuarioRepository.findByEmail(email)
//                .flatMap(usuario -> this.usuarioService.readRolesByEmail(email)
//                        .map(roleMap-> {List<String> roles = roleMap.values()
//                        .stream().flatMap(List::stream)
//                                .toList();
//
//                            List<SimpleGrantedAuthority> authorities = roles.stream()
//                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                                    .toList();
//
//                            return User.builder()
//                                    .username(usuario.getEmail())
//                                    .password(usuario.getPassword())
//                                    .authorities(authorities)
//                                    .disabled(false)
//                                    .accountExpired(false)
//                                    .credentialsExpired(false)
//                                    .accountLocked(false)
//                                    .build();
//                        })
//                );
//    }

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return this.usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    List<SimpleGrantedAuthority> authorities = Arrays.stream(usuario.getRoles().split(","))
                            .map(String::trim)
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList();

                    return User.builder()
                            .username(usuario.getEmail())
                            .password(usuario.getPassword())
                            .authorities(authorities)
                            .disabled(false)
                            .accountExpired(false)
                            .credentialsExpired(false)
                            .accountLocked(false)
                            .build();
                });
    }

}
