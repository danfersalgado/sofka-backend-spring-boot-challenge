# Sofka Technical Challenge - Microservicios de Banca

Este proyecto es una solución robusta y escalable para el reto técnico de Sofka, desarrollada bajo estándares de nivel **Senior**. Implementa un sistema de gestión de clientes, cuentas y movimientos bancarios utilizando una arquitectura moderna y mejores prácticas de ingeniería de software.

## 🚀 Arquitectura y Diseño

El proyecto aplica **Arquitectura Hexagonal (Puertos y Adaptadores)** para garantizar el desacoplamiento total entre la lógica de negocio y los frameworks externos.

### Capas del Proyecto:
- **Domain**: Modelos de negocio puros (POJOs) y lógica central.
- **Application**: Casos de uso (Input Ports) e interfaces de repositorio (Output Ports).
- **Infrastructure**: Adaptadores de entrada (REST Controllers) y salida (Spring Data JPA, Event Publishers).

### Principios Aplicados:
- **SOLID**: Inversión de dependencias, Responsabilidad única y Segregación de interfaces.
- **Clean Code**: Código autodocumentado, nombres significativos y funciones pequeñas.
- **Herencia**: Implementación de herencia entre `Persona` y `Cliente` tanto en dominio como en persistencia.
- **Comunicación Asincrónica**: Estructura preparada para mensajería mediante RabbitMQ.

## 🛠️ Tecnologías Utilizadas

- **Java 17** (LTS)
- **Spring Boot 3.2.1**
- **Gradle 8.8**
- **H2 Database** (Base de datos en memoria para pruebas rápidas)
- **PostgreSQL** (Soportado mediante Docker)
- **Lombok** (Reducción de código boilerplate)
- **SpringDoc OpenAPI (Swagger)** (Documentación de API)
- **JaCoCo** (Reportes de cobertura de código)
- **PITest** (Pruebas de mutación para asegurar la calidad de los tests)

## 🏃 Cómo Ejecutar el Proyecto

### Requisitos Previos:
- JDK 17 instalado.
- Gradle 8.8 (o usar el wrapper incluido).

### Ejecución Local (Sin Docker):
Desde la raíz del proyecto, ejecuta los siguientes comandos en terminales separadas:

```sh
# Iniciar Microservicio de Clientes (Puerto 8082)
./gradlew :client-service:bootRun

# Iniciar Microservicio de Cuentas (Puerto 8081)
./gradlew :account-service:bootRun
```

### Ejecución con Docker:
```sh
docker-compose up -d
```

## 📝 Documentación de la API (Swagger)

Una vez iniciados los servicios, puedes acceder a la documentación interactiva:

- **Clientes**: [http://localhost:8082/api/swagger-ui/index.html](http://localhost:8082/api/swagger-ui/index.html)
- **Cuentas**: [http://localhost:8081/api/swagger-ui/index.html](http://localhost:8081/api/swagger-ui/index.html)

## 🧪 Pruebas y Calidad de Código

El proyecto incluye una suite completa de pruebas unitarias y de integración.

### Ejecutar Tests y Generar Reportes JaCoCo:
```sh
./gradlew test
```
Los reportes se encuentran en: `[modulo]/build/reports/jacoco/test/html/index.html`

### Ejecutar Pruebas de Mutación (PITest):
```sh
./gradlew pitest
```
Los reportes se encuentran en: `[modulo]/build/reports/pitest/index.html`

## 📂 Entregables Incluidos

- **BaseDatos.sql**: Script de creación de esquema y tablas en la raíz del proyecto.
- **Colección Postman**: Archivo JSON listo para importar en la raíz del proyecto.
- **Reportes de Cobertura**: Generados automáticamente en la carpeta `build/reports`.

## 🛠️ Endpoints Principales (Prefijo /api)

### Clientes
- `GET /clientes`: Listar todos.
- `POST /clientes`: Crear nuevo cliente.
- `PUT /clientes/{id}`: Actualizar.
- `DELETE /clientes/{id}`: Eliminar.

### Cuentas y Movimientos
- `GET /cuentas`: Listar cuentas.
- `POST /movimientos`: Registrar depósito o retiro (Valida saldo disponible).
- `GET /reportes`: Generar estado de cuenta por rango de fechas y cliente.

---
Desarrollado por Daniel Salgado para el reto técnico de Sofka.
