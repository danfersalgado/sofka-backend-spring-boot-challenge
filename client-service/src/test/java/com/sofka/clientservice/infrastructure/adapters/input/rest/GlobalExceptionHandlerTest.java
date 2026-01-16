package com.sofka.clientservice.infrastructure.adapters.input.rest;

import com.sofka.clientservice.application.ports.input.ClienteUseCase;
import com.sofka.clientservice.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ClienteController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteUseCase clienteUseCase;

    @Test
    void handleRuntimeException_ShouldReturnBadRequest() throws Exception {
        // Simulamos que el caso de uso lanza una RuntimeException
        when(clienteUseCase.getClienteById(anyLong()))
                .thenThrow(new RuntimeException("Error de prueba"));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error de prueba"));
    }
}
