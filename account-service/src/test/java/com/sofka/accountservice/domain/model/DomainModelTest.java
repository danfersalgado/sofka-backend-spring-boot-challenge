package com.sofka.accountservice.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DomainModelTest {

    @Test
    void testCuentaLombokExhaustive() {
        Cuenta cuenta1 = Cuenta.builder()
                .numeroCuenta("123")
                .tipoCuenta("Ahorros")
                .saldoInicial(BigDecimal.TEN)
                .estado(true)
                .clientId("C1")
                .build();

        Cuenta cuenta2 = new Cuenta("123", "Ahorros", BigDecimal.TEN, true, "C1");
        
        // 1. Equality and basic methods
        assertEquals(cuenta1, cuenta2);
        assertEquals(cuenta1, cuenta1);
        assertNotEquals(cuenta1, null);
        assertNotEquals(cuenta1, new Object());
        assertEquals(cuenta1.hashCode(), cuenta2.hashCode());
        
        // 2. toString verification
        String toString = cuenta1.toString();
        assertTrue(toString.contains("numeroCuenta=123"));
        assertTrue(toString.contains("tipoCuenta=Ahorros"));
        assertTrue(toString.contains("saldoInicial=10"));
        assertTrue(toString.contains("estado=true"));
        assertTrue(toString.contains("clientId=C1"));

        // 3. canEqual
        assertTrue(cuenta1.canEqual(cuenta2));
        assertFalse(cuenta1.canEqual(new Object()));

        // 4. Getters and Setters (including boolean coverage)
        Cuenta cuenta3 = new Cuenta();
        cuenta3.setNumeroCuenta("456");
        cuenta3.setTipoCuenta("Corriente");
        cuenta3.setSaldoInicial(BigDecimal.ZERO);
        cuenta3.setEstado(false);
        cuenta3.setClientId("C2");

        assertEquals("456", cuenta3.getNumeroCuenta());
        assertEquals("Corriente", cuenta3.getTipoCuenta());
        assertEquals(BigDecimal.ZERO, cuenta3.getSaldoInicial());
        assertFalse(cuenta3.getEstado());
        assertEquals("C2", cuenta3.getClientId());
        
        // Kill mutation: replaced Boolean return with True for getEstado
        cuenta3.setEstado(true);
        assertTrue(cuenta3.getEstado());
        cuenta3.setEstado(false);
        assertFalse(cuenta3.getEstado());

        // 5. Inequality for each field (to kill mutations in equals/hashCode)
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta(null).tipoCuenta("Ahorros").saldoInicial(BigDecimal.TEN).estado(true).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta(null).saldoInicial(BigDecimal.TEN).estado(true).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").saldoInicial(null).estado(true).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").saldoInicial(BigDecimal.TEN).estado(null).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").saldoInicial(BigDecimal.TEN).estado(true).clientId(null).build());
        
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("diff").tipoCuenta("Ahorros").saldoInicial(BigDecimal.TEN).estado(true).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("diff").saldoInicial(BigDecimal.TEN).estado(true).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").saldoInicial(BigDecimal.ZERO).estado(true).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").saldoInicial(BigDecimal.TEN).estado(false).clientId("C1").build());
        assertNotEquals(cuenta1, Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").saldoInicial(BigDecimal.TEN).estado(true).clientId("diff").build());

        // 6. Builder toString
        assertNotNull(Cuenta.builder().toString());
        
        // 7. HashCode consistency and difference
        int initialHash = cuenta1.hashCode();
        assertEquals(initialHash, cuenta1.hashCode());
        assertNotEquals(0, initialHash);
    }

    @Test
    void testMovimientoLombokExhaustive() {
        Cuenta cuenta = Cuenta.builder().numeroCuenta("123").build();
        LocalDateTime now = LocalDateTime.now();
        
        Movimiento m1 = Movimiento.builder()
                .id(1L)
                .fecha(now)
                .tipoMovimiento("Deposito")
                .valor(BigDecimal.TEN)
                .saldo(BigDecimal.TEN)
                .cuenta(cuenta)
                .build();

        Movimiento m2 = new Movimiento(1L, now, "Deposito", BigDecimal.TEN, BigDecimal.TEN, cuenta);
        
        // 1. Equality
        assertEquals(m1, m2);
        assertEquals(m1, m1);
        assertNotEquals(m1, null);
        assertNotEquals(m1, new Object());
        assertEquals(m1.hashCode(), m2.hashCode());
        
        // 2. toString
        String toString = m1.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("tipoMovimiento=Deposito"));
        assertTrue(toString.contains("valor=10"));
        assertTrue(toString.contains("saldo=10"));

        // 3. canEqual
        assertTrue(m1.canEqual(m2));
        assertFalse(m1.canEqual(new Object()));

        // 4. Getters and Setters
        Movimiento m3 = new Movimiento();
        m3.setId(2L);
        m3.setFecha(now);
        m3.setTipoMovimiento("Retiro");
        m3.setValor(BigDecimal.ONE);
        m3.setSaldo(BigDecimal.ZERO);
        m3.setCuenta(cuenta);
        
        assertEquals(2L, m3.getId());
        assertEquals(now, m3.getFecha());
        assertEquals("Retiro", m3.getTipoMovimiento());
        assertEquals(BigDecimal.ONE, m3.getValor());
        assertEquals(BigDecimal.ZERO, m3.getSaldo());
        assertEquals(cuenta, m3.getCuenta());

        // 5. Inequality for each field
        assertNotEquals(m1, Movimiento.builder().id(null).fecha(now).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(null).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento(null).valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento("Deposito").valor(null).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(null).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(null).build());

        assertNotEquals(m1, Movimiento.builder().id(2L).fecha(now).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now.plusDays(1)).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento("diff").valor(BigDecimal.TEN).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento("Deposito").valor(BigDecimal.ZERO).saldo(BigDecimal.TEN).cuenta(cuenta).build());
        assertNotEquals(m1, Movimiento.builder().id(1L).fecha(now).tipoMovimiento("Deposito").valor(BigDecimal.TEN).saldo(BigDecimal.ZERO).cuenta(cuenta).build());

        // 6. Builder toString
        assertNotNull(Movimiento.builder().toString());
        
        // 7. HashCode
        assertNotEquals(0, m1.hashCode());
    }
}
