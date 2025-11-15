package com.pruebatenica01.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> manejarValidaciones(WebExchangeBindException ex) {

        Map<String, Object> errores = new HashMap<>();
        errores.put("status", 400);

        List<String> lista = ex.getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        errores.put("errores", lista);

        return ResponseEntity.badRequest().body(errores);
    }
}