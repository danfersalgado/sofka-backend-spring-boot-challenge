package com.sofka.clientservice.application.ports.output;

import com.sofka.clientservice.domain.model.Cliente;

public interface EventPublisherPort {
    void publishClienteCreated(Cliente cliente);
}
