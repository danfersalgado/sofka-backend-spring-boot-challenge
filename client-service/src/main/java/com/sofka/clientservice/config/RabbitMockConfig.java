package com.sofka.clientservice.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mockito.Mockito;

@Configuration
public class RabbitMockConfig {

    @Bean
    public RabbitTemplate rabbitTemplate() {
        // Retornamos un objeto simulado para que el constructor de ClienteService no falle
        return Mockito.mock(RabbitTemplate.class);
    }
    
    @Bean
    public ConnectionFactory connectionFactory() {
        return Mockito.mock(ConnectionFactory.class);
    }
}
