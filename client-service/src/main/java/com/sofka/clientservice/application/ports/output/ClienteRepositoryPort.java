package com.sofka.clientservice.application.ports.output;

import com.sofka.clientservice.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByClienteId(String clienteId);
    List<Cliente> findAll();
    void deleteById(Long id);
}
