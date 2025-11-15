package com.pruebatenica01.controller;


import com.pruebatenica01.dto.LoginRequest;
import com.pruebatenica01.dto.LoginResponse;
import com.pruebatenica01.dto.UsuarioResponse;
import com.pruebatenica01.entity.UsuarioEntity;
import com.pruebatenica01.security.AuthService;
import com.pruebatenica01.security.JwtHelper;
import com.pruebatenica01.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper ;

    public AuthController(UsuarioService usuarioService, AuthService authService, PasswordEncoder passwordEncoder, JwtHelper jwtHelper) {
        this.usuarioService = usuarioService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping( "/login")
    public Mono<ResponseEntity<LoginResponse>> login(LoginRequest loginRequest) {
        return authService.authenticate(loginRequest.email(), loginRequest.password())
                .flatMap(jwt ->
                        usuarioService.readRolesByEmail(loginRequest.email()) // ← devuelve UsuarioEntity
                                .map(usuario -> {
                                    List<String> rolesNames = Arrays.asList(usuario.getRoles().split(","));

                                    LoginResponse loginResponse = new LoginResponse(
                                            jwt,
                                            loginRequest.email(),
                                            rolesNames
                                    );

                                    return ResponseEntity.ok(loginResponse);
                                })
                )
                .onErrorResume(error -> {
                    log.error(error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }


//    @PostMapping("/register")
//    public Mono<ResponseEntity<UsuarioEntity>> createCustomer(@RequestBody UsuarioEntity usuario) {
//        log.info("POST auth/register");
//
//        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
//
//        return usuarioService.createCustomer(usuario)
//                .map(usuarioEntity -> ResponseEntity
//                        .created(URI.create("auth/register/" + usuarioEntity.getId()))
//                        .body(usuarioEntity))
//                .onErrorResume(IllegalArgumentException.class, error -> {
//                    log.error("POST  auth/register/ failed", error);
//                    return Mono.just(ResponseEntity.badRequest().build());
//                })
//                .onErrorResume(RuntimeException.class, error -> {
//                    log.error("POST  auth/register/ failed", error);
//                    return Mono.just(ResponseEntity.internalServerError().build());
//                });
//    }

    @PostMapping("/register")
    public Mono<ResponseEntity<UsuarioResponse>> createCustomer(@RequestBody UsuarioEntity usuario) {
        log.info("POST auth/register");

        // 1. Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioService.createCustomer(usuario)
                .flatMap(usuarioEntity -> {

                    // 2. Convertir String roles → List<String>
                    List<String> roles = Arrays.stream(usuarioEntity.getRoles().split(","))
                            .map(String::trim)
                            .toList();

                    // 3. Generar token JWT
                    String token = jwtHelper.generateToken(
                            usuarioEntity.getEmail(),
                            roles
                    );

                    // 4. Crear respuesta DTO
                    UsuarioResponse response = new UsuarioResponse(
                            usuarioEntity.getEmail(),
                            token
                    );

                    return Mono.just(
                            ResponseEntity
                                    .created(URI.create("auth/register/" + usuarioEntity.getId()))
                                    .body(response)
                    );
                })
                .onErrorResume(IllegalArgumentException.class, error -> {
                    log.error("POST auth/register failed", error);
                    return Mono.just(ResponseEntity.badRequest().build());
                })
                .onErrorResume(RuntimeException.class, error -> {
                    log.error("POST auth/register failed", error);
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }

}
