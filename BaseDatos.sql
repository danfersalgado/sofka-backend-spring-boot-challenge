-- Script de creación de tablas para Sofka Challenge

-- Microservicio de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT,
    identificacion VARCHAR(20) UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    cliente_id VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

-- Microservicio de Cuentas
CREATE TABLE IF NOT EXISTS cuentas (
    numero_cuenta VARCHAR(20) PRIMARY KEY,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    client_id VARCHAR(50) NOT NULL
);

-- Microservicio de Movimientos
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    FOREIGN KEY (numero_cuenta) REFERENCES cuentas(numero_cuenta)
);
