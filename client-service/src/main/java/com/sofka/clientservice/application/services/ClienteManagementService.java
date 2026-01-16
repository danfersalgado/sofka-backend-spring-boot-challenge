package com.sofka.clientservice.application.services;

import com.sofka.clientservice.application.ports.input.ClienteUseCase;
import com.sofka.clientservice.application.ports.output.ClienteRepositoryPort;
import com.sofka.clientservice.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteManagementService implements ClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;

    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public Cliente getClienteByExternalId(String clienteId) {
        return clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
}
