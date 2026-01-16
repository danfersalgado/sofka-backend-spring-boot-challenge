package com.sofka.accountservice.application.ports.input;

import com.sofka.accountservice.domain.model.Cuenta;
import java.util.List;

public interface CuentaUseCase {
    Cuenta createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(Cuenta cuenta);
    void deleteCuenta(String numeroCuenta);
    Cuenta getCuentaById(String numeroCuenta);
    List<Cuenta> getAllCuentas();
}
