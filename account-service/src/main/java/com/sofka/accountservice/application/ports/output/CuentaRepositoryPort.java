package com.sofka.accountservice.application.ports.output;

import com.sofka.accountservice.domain.model.Cuenta;
import java.util.List;
import java.util.Optional;

public interface CuentaRepositoryPort {
    Cuenta save(Cuenta cuenta);
    Optional<Cuenta> findById(String numeroCuenta);
    List<Cuenta> findAll();
    void deleteById(String numeroCuenta);
}
