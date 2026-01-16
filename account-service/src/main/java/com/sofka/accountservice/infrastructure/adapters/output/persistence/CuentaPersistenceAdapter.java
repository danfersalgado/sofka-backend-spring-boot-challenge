package com.sofka.accountservice.infrastructure.adapters.output.persistence;

import com.sofka.accountservice.application.ports.output.CuentaRepositoryPort;
import com.sofka.accountservice.domain.model.Cuenta;
import com.sofka.accountservice.infrastructure.adapters.output.persistence.entity.CuentaEntity;
import com.sofka.accountservice.infrastructure.adapters.output.persistence.repository.JpaCuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CuentaPersistenceAdapter implements CuentaRepositoryPort {

    private final JpaCuentaRepository repository;

    @Override
    public Cuenta save(Cuenta cuenta) {
        CuentaEntity entity = toEntity(cuenta);
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Cuenta> findById(String numeroCuenta) {
        return repository.findById(numeroCuenta).map(this::toDomain);
    }

    @Override
    public List<Cuenta> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String numeroCuenta) {
        repository.deleteById(numeroCuenta);
    }

    private CuentaEntity toEntity(Cuenta domain) {
        return CuentaEntity.builder()
                .numeroCuenta(domain.getNumeroCuenta())
                .tipoCuenta(domain.getTipoCuenta())
                .saldoInicial(domain.getSaldoInicial())
                .estado(domain.getEstado())
                .clientId(domain.getClientId())
                .build();
    }

    private Cuenta toDomain(CuentaEntity entity) {
        return Cuenta.builder()
                .numeroCuenta(entity.getNumeroCuenta())
                .tipoCuenta(entity.getTipoCuenta())
                .saldoInicial(entity.getSaldoInicial())
                .estado(entity.getEstado())
                .clientId(entity.getClientId())
                .build();
    }
}
