package com.sofka.clientservice;

import com.sofka.clientservice.domain.model.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteUnitTest {
    @Test
    public void testClienteCreation() {
        Cliente cliente = Cliente.builder()
                .nombre("Jose Lema")
                .clienteId("123456")
                .estado(true)
                .build();

        assertEquals("Jose Lema", cliente.getNombre());
        assertEquals("123456", cliente.getClienteId());
        assertTrue(cliente.getEstado());
    }
}
