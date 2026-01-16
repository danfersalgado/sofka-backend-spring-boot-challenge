package com.sofka.accountservice.infrastructure.adapters.input.rest;

import com.sofka.accountservice.application.ports.input.MovimientoUseCase;
import com.sofka.accountservice.exceptions.GlobalExceptionHandler;
import com.sofka.accountservice.exceptions.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {MovementController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoUseCase movimientoUseCase;

    @Test
    void handleSaldoInsuficiente_ShouldReturn422AndHeader() throws Exception {
        // Simulamos que el caso de uso lanza la excepción de saldo insuficiente
        when(movimientoUseCase.registerMovement(any(), any()))
                .thenThrow(new SaldoInsuficienteException("Saldo no disponible"));

        mockMvc.perform(post("/movimientos")
                .contentType("application/json")
                .content("{\"numeroCuenta\":\"123\", \"valor\":-100}"))
                .andExpect(status().isUnprocessableEntity())
                // Verificamos el HEADER (Esto mata la mutación 1 de la línea 21)
                .andExpect(header().string("message", "Saldo no disponible"))
                // Verificamos el CUERPO (Esto mata la mutación de la línea 23)
                .andExpect(jsonPath("$.message").value("Saldo no disponible"));
    }
}
