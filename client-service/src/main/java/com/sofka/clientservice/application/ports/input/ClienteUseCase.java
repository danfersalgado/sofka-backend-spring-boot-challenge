package com.sofka.clientservice.application.ports.input;

import com.sofka.clientservice.domain.model.Cliente;
import java.util.List;

public interface ClienteUseCase {
    Cliente createCliente(Cliente cliente);
    Cliente updateCliente(Cliente cliente);
    void deleteCliente(Long id);
    Cliente getClienteById(Long id);
    Cliente getClienteByExternalId(String clienteId);
    List<Cliente> getAllClientes();
}
