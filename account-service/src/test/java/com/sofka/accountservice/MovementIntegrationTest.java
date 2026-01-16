package com.sofka.accountservice;

import com.sofka.accountservice.application.ports.input.CuentaUseCase;
import com.sofka.accountservice.application.ports.input.MovimientoUseCase;
import com.sofka.accountservice.domain.model.Cuenta;
import com.sofka.accountservice.domain.model.Movimiento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class MovementIntegrationTest {

    @Autowired
    private CuentaUseCase cuentaUseCase;

    @Autowired
    private MovimientoUseCase movimientoUseCase;

    @Test
    public void testBalanceUpdate() {
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta("478758")
                .saldoInicial(new BigDecimal("2000"))
                .tipoCuenta("Ahorros")
                .estado(true)
                .build();
        
        cuentaUseCase.createCuenta(cuenta);

        Movimiento mv = movimientoUseCase.registerMovement("478758", new BigDecimal("-575"));

        assertEquals(0, new BigDecimal("1425").compareTo(mv.getSaldo()));
    }
}
