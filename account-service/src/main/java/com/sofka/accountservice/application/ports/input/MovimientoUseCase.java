package com.sofka.accountservice.application.ports.input;

import com.sofka.accountservice.domain.model.Movimiento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoUseCase {
    Movimiento registerMovement(String numeroCuenta, BigDecimal valor);
    List<Movimiento> getReport(String numeroCuenta, LocalDateTime start, LocalDateTime end);
}
