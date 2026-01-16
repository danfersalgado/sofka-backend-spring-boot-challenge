package com.sofka.accountservice.infrastructure.adapters.input.rest;

import com.sofka.accountservice.application.ports.input.MovimientoUseCase;
import com.sofka.accountservice.domain.model.Movimiento;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final MovimientoUseCase movimientoUseCase;

    @GetMapping
    public List<ReportDTO> getReport(
            @RequestParam String numeroCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        
        return movimientoUseCase.getReport(numeroCuenta, inicio, fin).stream()
                .map(m -> ReportDTO.builder()
                        .fecha(m.getFecha().toString())
                        .cliente(m.getCuenta().getClientId())
                        .numeroCuenta(m.getCuenta().getNumeroCuenta())
                        .tipo(m.getCuenta().getTipoCuenta())
                        .saldoInicial(m.getSaldo().subtract(m.getValor()))
                        .estado(m.getCuenta().getEstado())
                        .movimiento(m.getValor())
                        .saldoDisponible(m.getSaldo())
                        .build())
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class ReportDTO {
        private String fecha;
        private String cliente;
        private String numeroCuenta;
        private String tipo;
        private java.math.BigDecimal saldoInicial;
        private Boolean estado;
        private java.math.BigDecimal movimiento;
        private java.math.BigDecimal saldoDisponible;
    }
}
