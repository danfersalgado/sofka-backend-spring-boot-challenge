package com.sofka.accountservice.application.services;

import com.sofka.accountservice.application.ports.output.CuentaRepositoryPort;
import com.sofka.accountservice.application.ports.output.MovimientoRepositoryPort;
import com.sofka.accountservice.domain.model.Cuenta;
import com.sofka.accountservice.domain.model.Movimiento;
import com.sofka.accountservice.exceptions.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountManagementServiceTest {

    @Mock
    private CuentaRepositoryPort cuentaRepository;
    @Mock
    private MovimientoRepositoryPort movimientoRepository;

    @InjectMocks
    private AccountManagementService accountService;

    private Cuenta cuentaTest;

    @BeforeEach
    void setUp() {
        cuentaTest = Cuenta.builder()
                .numeroCuenta("123456")
                .saldoInicial(new BigDecimal("1000"))
                .estado(true)
                .build();
    }

    @Test
    void createCuenta_ShouldSave() {
        when(cuentaRepository.save(any())).thenReturn(cuentaTest);
        Cuenta result = accountService.createCuenta(cuentaTest);
        assertNotNull(result);
        verify(cuentaRepository).save(cuentaTest);
    }

    @Test
    void updateCuenta_ShouldSave() {
        when(cuentaRepository.save(any())).thenReturn(cuentaTest);
        Cuenta result = accountService.updateCuenta(cuentaTest);
        assertNotNull(result);
        verify(cuentaRepository).save(cuentaTest);
    }

    @Test
    void deleteCuenta_ShouldDelete() {
        doNothing().when(cuentaRepository).deleteById("123456");
        accountService.deleteCuenta("123456");
        verify(cuentaRepository).deleteById("123456");
    }

    @Test
    void getCuentaById_Success() {
        when(cuentaRepository.findById("123456")).thenReturn(Optional.of(cuentaTest));
        Cuenta result = accountService.getCuentaById("123456");
        assertEquals("123456", result.getNumeroCuenta());
    }

    @Test
    void getCuentaById_NotFound_ThrowsException() {
        when(cuentaRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> accountService.getCuentaById("999"));
    }

    @Test
    void getAllCuentas_ShouldReturnList() {
        when(cuentaRepository.findAll()).thenReturn(List.of(cuentaTest));
        List<Cuenta> result = accountService.getAllCuentas();
        assertFalse(result.isEmpty());
    }

    @Test
    void registerMovement_Success_Deposito() {
        when(cuentaRepository.findById("123456")).thenReturn(Optional.of(cuentaTest));
        when(movimientoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Movimiento result = accountService.registerMovement("123456", new BigDecimal("500"));

        assertEquals(new BigDecimal("1500"), result.getSaldo());
        assertEquals("Deposito", result.getTipoMovimiento());
        verify(cuentaRepository).save(argThat(c -> c.getSaldoInicial().equals(new BigDecimal("1500"))));
    }

    @Test
    void registerMovement_Success_Retiro() {
        when(cuentaRepository.findById("123456")).thenReturn(Optional.of(cuentaTest));
        when(movimientoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Movimiento result = accountService.registerMovement("123456", new BigDecimal("-400"));

        assertEquals(new BigDecimal("600"), result.getSaldo());
        assertEquals("Retiro", result.getTipoMovimiento());
    }

    @Test
    void registerMovement_Fail_SaldoInsuficiente() {
        when(cuentaRepository.findById("123456")).thenReturn(Optional.of(cuentaTest));

        assertThrows(SaldoInsuficienteException.class, () -> {
            accountService.registerMovement("123456", new BigDecimal("-1100"));
        });
    }

    @Test
    void getReport_ShouldReturnList() {
        LocalDateTime now = LocalDateTime.now();
        when(movimientoRepository.findByCuentaAndFechaBetween(any(), any(), any())).thenReturn(List.of(new Movimiento()));
        List<Movimiento> result = accountService.getReport("123456", now, now);
        assertFalse(result.isEmpty());
    }
}
