package com.sofka.accountservice.infrastructure.adapters.output.persistence.repository;

import com.sofka.accountservice.infrastructure.adapters.output.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaMovimientoRepository extends JpaRepository<MovimientoEntity, Long> {
    List<MovimientoEntity> findByCuentaNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime start, LocalDateTime end);
}
