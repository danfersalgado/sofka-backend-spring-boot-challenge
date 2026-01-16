package com.sofka.clientservice.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.clientservice.application.ports.input.ClienteUseCase;
import com.sofka.clientservice.domain.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteUseCase clienteUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_ShouldReturnList() throws Exception {
        Cliente cliente = Cliente.builder().id(1L).nombre("Juan").build();
        when(clienteUseCase.getAllClientes()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void getById_ShouldReturnCliente() throws Exception {
        Cliente cliente = Cliente.builder().id(1L).nombre("Juan").build();
        when(clienteUseCase.getClienteById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void create_ShouldReturnCreated() throws Exception {
        Cliente cliente = Cliente.builder().nombre("Juan").build();
        when(clienteUseCase.createCliente(any())).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void update_ShouldReturnUpdatedCliente() throws Exception {
        Cliente cliente = Cliente.builder().id(1L).nombre("Juan Actualizado").build();
        when(clienteUseCase.updateCliente(any())).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"));
        
        verify(clienteUseCase).updateCliente(argThat(c -> c.getId().equals(1L)));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(clienteUseCase).deleteCliente(1L);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
        
        verify(clienteUseCase).deleteCliente(1L);
    }
}
