package com.sofka.accountservice.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Object> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Saldo no disponible");

        return new ResponseEntity<>(body, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
