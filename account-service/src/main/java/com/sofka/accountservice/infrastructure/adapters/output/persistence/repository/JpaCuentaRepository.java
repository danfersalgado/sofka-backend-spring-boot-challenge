package com.sofka.accountservice.infrastructure.adapters.output.persistence.repository;

import com.sofka.accountservice.infrastructure.adapters.output.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCuentaRepository extends JpaRepository<CuentaEntity, String> {
}
