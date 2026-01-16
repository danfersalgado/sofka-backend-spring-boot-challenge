package com.sofka.accountservice.application.services;

import com.sofka.accountservice.application.ports.input.CuentaUseCase;
import com.sofka.accountservice.application.ports.input.MovimientoUseCase;
import com.sofka.accountservice.application.ports.output.CuentaRepositoryPort;
import com.sofka.accountservice.application.ports.output.MovimientoRepositoryPort;
import com.sofka.accountservice.domain.model.Cuenta;
import com.sofka.accountservice.domain.model.Movimiento;
import com.sofka.accountservice.exceptions.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountManagementService implements CuentaUseCase, MovimientoUseCase {

    private final CuentaRepositoryPort cuentaRepository;
    private final MovimientoRepositoryPort movimientoRepository;

    @Override
    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta updateCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void deleteCuenta(String numeroCuenta) {
        cuentaRepository.deleteById(numeroCuenta);
    }

    @Override
    public Cuenta getCuentaById(String numeroCuenta) {
        return cuentaRepository.findById(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Override
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional
    public Movimiento registerMovement(String numeroCuenta, BigDecimal valor) {
        Cuenta cuenta = getCuentaById(numeroCuenta);
        BigDecimal nuevoSaldo = cuenta.getSaldoInicial().add(valor);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        Movimiento mv = Movimiento.builder()
                .cuenta(cuenta)
                .fecha(LocalDateTime.now())
                .valor(valor)
                .saldo(nuevoSaldo)
                .tipoMovimiento(valor.compareTo(BigDecimal.ZERO) > 0 ? "Deposito" : "Retiro")
                .build();

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        return movimientoRepository.save(mv);
    }

    @Override
    public List<Movimiento> getReport(String numeroCuenta, LocalDateTime start, LocalDateTime end) {
        return movimientoRepository.findByCuentaAndFechaBetween(numeroCuenta, start, end);
    }
}
