package com.sofka.clientservice.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteDomainTest {

    @Test
    void testClienteLombokExhaustive() {
        Cliente c1 = Cliente.builder()
                .id(1L).nombre("Juan").genero("M").edad(30).identificacion("123")
                .direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true)
                .build();

        // Usamos el builder en lugar del constructor directo para evitar errores de herencia con Lombok
        Cliente c2 = Cliente.builder()
                .id(1L).nombre("Juan").genero("M").edad(30).identificacion("123")
                .direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true)
                .build();
        
        // 1. Verificación de Igualdad y HashCode básico
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotNull(c1.toString());
        assertTrue(c1.canEqual(c2));
        assertNotEquals(c1, null);
        assertNotEquals(c1, new Object());

        // 2. Matar mutantes de hashCode y equals probando CADA campo individualmente
        assertNotEquals(c1.hashCode(), Cliente.builder().id(2L).nombre("Juan").genero("M").edad(30).identificacion("123").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("diff").genero("M").edad(30).identificacion("123").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("F").edad(30).identificacion("123").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(20).identificacion("123").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(30).identificacion("diff").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(30).identificacion("123").direccion("diff").telefono("555").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(30).identificacion("123").direccion("Calle 1").telefono("diff").clienteId("C1").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(30).identificacion("123").direccion("Calle 1").telefono("555").clienteId("diff").contrasena("pass").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(30).identificacion("123").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("diff").estado(true).build().hashCode());
        assertNotEquals(c1.hashCode(), Cliente.builder().id(1L).nombre("Juan").genero("M").edad(30).identificacion("123").direccion("Calle 1").telefono("555").clienteId("C1").contrasena("pass").estado(false).build().hashCode());

        // 3. Verificación de toString detallada
        String ts = c1.toString();
        assertTrue(ts.contains("id=1"));
        assertTrue(ts.contains("nombre=Juan"));
        assertTrue(ts.contains("clienteId=C1"));

        // 4. Verificación del Builder
        Cliente.ClienteBuilder<?, ?> builder = Cliente.builder().id(1L).nombre("Juan");
        assertNotNull(builder.toString());
        Cliente built = builder.build();
        assertEquals("Juan", built.getNombre());

        // 5. Getters y Setters explícitos
        Cliente c3 = Cliente.builder().build();
        c3.setEstado(true);
        assertTrue(c3.getEstado());
        c3.setEstado(false);
        assertFalse(c3.getEstado());
        
        c3.setEdad(10);
        assertEquals(10, c3.getEdad());
    }
}
