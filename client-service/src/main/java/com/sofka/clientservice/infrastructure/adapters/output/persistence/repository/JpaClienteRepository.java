package com.sofka.clientservice.infrastructure.adapters.output.persistence.repository;

import com.sofka.clientservice.infrastructure.adapters.output.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByClienteId(String clienteId);
}
