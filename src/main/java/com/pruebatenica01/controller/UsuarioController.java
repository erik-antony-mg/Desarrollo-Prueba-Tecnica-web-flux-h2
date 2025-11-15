package com.pruebatenica01.controller;

import com.pruebatenica01.entity.UsuarioEntity;
import com.pruebatenica01.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public Flux<UsuarioEntity> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

}
