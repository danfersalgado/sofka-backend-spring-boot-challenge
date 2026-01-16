package com.sofka.accountservice.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.accountservice.application.ports.input.CuentaUseCase;
import com.sofka.accountservice.domain.model.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaUseCase cuentaUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_ShouldReturnList() throws Exception {
        Cuenta cuenta = Cuenta.builder().numeroCuenta("123").build();
        when(cuentaUseCase.getAllCuentas()).thenReturn(List.of(cuenta));

        mockMvc.perform(get("/cuentas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCuenta").value("123"));
    }

    @Test
    void getById_ShouldReturnAccount() throws Exception {
        Cuenta cuenta = Cuenta.builder().numeroCuenta("123").build();
        when(cuentaUseCase.getCuentaById("123")).thenReturn(cuenta);

        mockMvc.perform(get("/cuentas/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("123"));
    }

    @Test
    void create_ShouldReturnCreated() throws Exception {
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta("999999")
                .saldoInicial(new BigDecimal("100"))
                .build();

        when(cuentaUseCase.createCuenta(any())).thenReturn(cuenta);

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuenta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCuenta").value("999999"));
    }

    @Test
    void update_ShouldReturnUpdatedAccount() throws Exception {
        // Enviamos "ID_ERRONEO" en el cuerpo, pero "123" en la URL
        Cuenta cuentaBody = Cuenta.builder().numeroCuenta("ID_ERRONEO").tipoCuenta("Ahorros").build();
        Cuenta cuentaResponse = Cuenta.builder().numeroCuenta("123").tipoCuenta("Ahorros").build();
        
        when(cuentaUseCase.updateCuenta(any())).thenReturn(cuentaResponse);

        mockMvc.perform(put("/cuentas/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("123"));
        
        // Verificamos que el servicio recibió "123" (sobrescrito por el controlador)
        verify(cuentaUseCase).updateCuenta(argThat(c -> c.getNumeroCuenta().equals("123")));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(cuentaUseCase).deleteCuenta("123");

        mockMvc.perform(delete("/cuentas/123"))
                .andExpect(status().isNoContent());

        verify(cuentaUseCase).deleteCuenta("123");
    }
}
