package com.sofka.accountservice.infrastructure.adapters.input.rest;

import com.sofka.accountservice.application.ports.input.MovimientoUseCase;
import com.sofka.accountservice.domain.model.Movimiento;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovementController {

    private final MovimientoUseCase movimientoUseCase;

    @PostMapping
    public ResponseEntity<Movimiento> register(@RequestBody MovementRequest request) {
        return new ResponseEntity<>(
                movimientoUseCase.registerMovement(request.getNumeroCuenta(), request.getValor()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/reporte")
    public List<Movimiento> getReport(
            @RequestParam String numeroCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return movimientoUseCase.getReport(numeroCuenta, inicio, fin);
    }

    @Data
    public static class MovementRequest {
        private String numeroCuenta;
        private BigDecimal valor;
    }
}
