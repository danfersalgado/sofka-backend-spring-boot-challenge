package com.sofka.accountservice.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.accountservice.application.ports.input.MovimientoUseCase;
import com.sofka.accountservice.domain.model.Movimiento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovementController.class)
class MovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoUseCase movimientoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturnCreated() throws Exception {
        MovementController.MovementRequest request = new MovementController.MovementRequest();
        request.setNumeroCuenta("123");
        request.setValor(new BigDecimal("100"));

        Movimiento movimiento = Movimiento.builder().id(1L).valor(new BigDecimal("100")).build();
        when(movimientoUseCase.registerMovement(eq("123"), eq(new BigDecimal("100")))).thenReturn(movimiento);

        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getReport_ShouldReturnList() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Movimiento movimiento = Movimiento.builder().id(1L).build();
        when(movimientoUseCase.getReport(any(), any(), any())).thenReturn(List.of(movimiento));

        mockMvc.perform(get("/movimientos/reporte")
                .param("numeroCuenta", "123")
                .param("inicio", now.toString())
                .param("fin", now.plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
    
    @Test
    void testMovementRequestLombokFull() {
        MovementController.MovementRequest r1 = new MovementController.MovementRequest();
        r1.setNumeroCuenta("123");
        r1.setValor(BigDecimal.TEN);
        
        MovementController.MovementRequest r2 = new MovementController.MovementRequest();
        r2.setNumeroCuenta("123");
        r2.setValor(BigDecimal.TEN);
        
        MovementController.MovementRequest r3 = new MovementController.MovementRequest();
        r3.setNumeroCuenta("456");
        r3.setValor(BigDecimal.ONE);

        // Matar mutantes de equals, hashCode y toString
        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
        assertNotEquals(r1, new Object());
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotNull(r1.toString());
        assertTrue(r1.canEqual(r2));
        
        assertEquals("123", r1.getNumeroCuenta());
        assertEquals(BigDecimal.TEN, r1.getValor());
    }
}
