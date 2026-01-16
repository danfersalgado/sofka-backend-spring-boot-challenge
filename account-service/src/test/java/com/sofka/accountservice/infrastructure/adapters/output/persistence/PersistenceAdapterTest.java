package com.sofka.accountservice.infrastructure.adapters.output.persistence;

import com.sofka.accountservice.domain.model.Cuenta;
import com.sofka.accountservice.domain.model.Movimiento;
import com.sofka.accountservice.infrastructure.adapters.output.persistence.entity.CuentaEntity;
import com.sofka.accountservice.infrastructure.adapters.output.persistence.entity.MovimientoEntity;
import com.sofka.accountservice.infrastructure.adapters.output.persistence.repository.JpaCuentaRepository;
import com.sofka.accountservice.infrastructure.adapters.output.persistence.repository.JpaMovimientoRepository;
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
class PersistenceAdapterTest {

    @Mock
    private JpaCuentaRepository jpaCuentaRepository;
    @Mock
    private JpaMovimientoRepository jpaMovimientoRepository;

    @InjectMocks
    private CuentaPersistenceAdapter cuentaAdapter;
    @InjectMocks
    private MovimientoPersistenceAdapter movimientoAdapter;

    @Test
    void cuentaAdapter_AllMethods() {
        Cuenta cuenta = Cuenta.builder().numeroCuenta("123").build();
        CuentaEntity entity = CuentaEntity.builder().numeroCuenta("123").build();

        when(jpaCuentaRepository.save(any())).thenReturn(entity);
        when(jpaCuentaRepository.findById("123")).thenReturn(Optional.of(entity));
        when(jpaCuentaRepository.findAll()).thenReturn(List.of(entity));
        doNothing().when(jpaCuentaRepository).deleteById("123");

        assertNotNull(cuentaAdapter.save(cuenta));
        assertTrue(cuentaAdapter.findById("123").isPresent());
        assertFalse(cuentaAdapter.findAll().isEmpty());
        cuentaAdapter.deleteById("123");

        verify(jpaCuentaRepository).deleteById("123");
    }

    @Test
    void movimientoAdapter_AllMethods() {
        Cuenta cuenta = Cuenta.builder().numeroCuenta("123").build();
        Movimiento mov = Movimiento.builder().id(1L).cuenta(cuenta).build();
        MovimientoEntity entity = MovimientoEntity.builder().id(1L).cuenta(CuentaEntity.builder().numeroCuenta("123").build()).build();

        when(jpaMovimientoRepository.save(any())).thenReturn(entity);
        when(jpaMovimientoRepository.findByCuentaNumeroCuentaAndFechaBetween(any(), any(), any())).thenReturn(List.of(entity));

        assertNotNull(movimientoAdapter.save(mov));
        assertFalse(movimientoAdapter.findByCuentaAndFechaBetween("123", LocalDateTime.now(), LocalDateTime.now()).isEmpty());
    }
    
    @Test
    void testEntitiesLombok() {
        CuentaEntity c1 = CuentaEntity.builder().numeroCuenta("1").tipoCuenta("A").saldoInicial(BigDecimal.ZERO).estado(true).clientId("C").build();
        assertEquals("1", c1.getNumeroCuenta());
        assertEquals("A", c1.getTipoCuenta());
        assertEquals(BigDecimal.ZERO, c1.getSaldoInicial());
        assertTrue(c1.getEstado());
        assertEquals("C", c1.getClientId());
        
        MovimientoEntity m1 = MovimientoEntity.builder().id(1L).fecha(LocalDateTime.now()).tipoMovimiento("D").valor(BigDecimal.ONE).saldo(BigDecimal.ONE).cuenta(c1).build();
        assertEquals(1L, m1.getId());
        assertNotNull(m1.getFecha());
        assertEquals("D", m1.getTipoMovimiento());
        assertEquals(BigDecimal.ONE, m1.getValor());
        assertEquals(BigDecimal.ONE, m1.getSaldo());
        assertEquals(c1, m1.getCuenta());
    }
}
