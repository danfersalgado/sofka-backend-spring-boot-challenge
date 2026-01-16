package com.sofka.clientservice.infrastructure.adapters.output.persistence;

import com.sofka.clientservice.domain.model.Cliente;
import com.sofka.clientservice.infrastructure.adapters.output.persistence.entity.ClienteEntity;
import com.sofka.clientservice.infrastructure.adapters.output.persistence.repository.JpaClienteRepository;
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
class ClientePersistenceAdapterTest {

    @Mock
    private JpaClienteRepository repository;

    @InjectMocks
    private ClientePersistenceAdapter adapter;

    @Test
    void adapter_AllMethods() {
        Cliente domain = Cliente.builder().id(1L).clienteId("C1").build();
        ClienteEntity entity = ClienteEntity.builder().id(1L).clienteId("C1").build();

        when(repository.save(any())).thenReturn(entity);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByClienteId("C1")).thenReturn(Optional.of(entity));
        when(repository.findAll()).thenReturn(List.of(entity));
        doNothing().when(repository).deleteById(1L);

        assertNotNull(adapter.save(domain));
        assertTrue(adapter.findById(1L).isPresent());
        assertTrue(adapter.findByClienteId("C1").isPresent());
        assertFalse(adapter.findAll().isEmpty());
        adapter.deleteById(1L);

        verify(repository).deleteById(1L);
    }
    
    @Test
    void testEntityLombok() {
        ClienteEntity e1 = ClienteEntity.builder()
                .id(1L).nombre("J").genero("M").edad(20).identificacion("1").direccion("D").telefono("T")
                .clienteId("C").contrasena("P").estado(true).build();
        
        assertEquals(1L, e1.getId());
        assertEquals("J", e1.getNombre());
        assertEquals("M", e1.getGenero());
        assertEquals(20, e1.getEdad());
        assertEquals("1", e1.getIdentificacion());
        assertEquals("D", e1.getDireccion());
        assertEquals("T", e1.getTelefono());
        assertEquals("C", e1.getClienteId());
        assertEquals("P", e1.getContrasena());
        assertTrue(e1.getEstado());
    }
}
