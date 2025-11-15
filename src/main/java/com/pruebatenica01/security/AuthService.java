package com.pruebatenica01.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioDetailsService usuarioDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public Mono<String> authenticate(String email, String password) {
        return usuarioDetailsService.findByUsername(email)
                .filter(userDetails -> this.passwordEncoder.matches(password, userDetails.getPassword()))
                .map(userDetails -> {
                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                            .toList();
                    return this.jwtHelper.generateToken(userDetails.getUsername(), roles);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Credenciales Invalidas")));
    }
}
