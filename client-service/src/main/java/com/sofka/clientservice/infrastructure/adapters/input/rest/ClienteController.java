package com.sofka.clientservice.infrastructure.adapters.input.rest;

import com.sofka.clientservice.application.ports.input.ClienteUseCase;
import com.sofka.clientservice.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @GetMapping
    public List<Cliente> getAll() {
        return clienteUseCase.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteUseCase.getClienteById(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteUseCase.createCliente(cliente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return ResponseEntity.ok(clienteUseCase.updateCliente(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteUseCase.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
