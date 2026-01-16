package com.sofka.clientservice.application.services;

import com.sofka.clientservice.application.ports.output.ClienteRepositoryPort;
import com.sofka.clientservice.domain.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteManagementServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepository;

    @InjectMocks
    private ClienteManagementService clienteService;

    @Test
    void createCliente_Success() {
        Cliente cliente = Cliente.builder().nombre("Juan Perez").build();
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente result = clienteService.createCliente(cliente);

        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void updateCliente_Success() {
        Cliente cliente = Cliente.builder().id(1L).nombre("Juan Actualizado").build();
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente result = clienteService.updateCliente(cliente);

        assertNotNull(result);
        assertEquals("Juan Actualizado", result.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void deleteCliente_Success() {
        doNothing().when(clienteRepository).deleteById(1L);
        clienteService.deleteCliente(1L);
        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void getClienteById_Success() {
        Cliente cliente = Cliente.builder().id(1L).build();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getClienteById_NotFound_ThrowsException() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            clienteService.getClienteById(1L);
        });
    }

    @Test
    void getClienteByExternalId_Success() {
        Cliente cliente = Cliente.builder().clienteId("EXT-123").build();
        when(clienteRepository.findByClienteId("EXT-123")).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteByExternalId("EXT-123");

        assertNotNull(result);
        assertEquals("EXT-123", result.getClienteId());
    }

    @Test
    void getClienteByExternalId_NotFound_ThrowsException() {
        when(clienteRepository.findByClienteId("EXT-999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            clienteService.getClienteByExternalId("EXT-999");
        });
    }

    @Test
    void getAllClientes_ShouldReturnList() {
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente()));
        List<Cliente> result = clienteService.getAllClientes();
        assertFalse(result.isEmpty());
    }
}
