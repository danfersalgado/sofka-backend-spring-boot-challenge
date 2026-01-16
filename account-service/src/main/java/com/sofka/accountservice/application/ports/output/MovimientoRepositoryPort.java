package com.sofka.accountservice.application.ports.output;

import com.sofka.accountservice.domain.model.Movimiento;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepositoryPort {
    Movimiento save(Movimiento movimiento);
    List<Movimiento> findByCuentaAndFechaBetween(String numeroCuenta, LocalDateTime start, LocalDateTime end);
}
