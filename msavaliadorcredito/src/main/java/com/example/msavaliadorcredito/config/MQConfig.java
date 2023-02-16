package com.example.msavaliadorcredito.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.emissao-cartoes}")
    private String emissaoCartoesFila;


    // método que cria a fila emissaoCartoesFila
    @Bean
    public Queue queueEmissaoCartoes(){

        // nome da fila, e se ela eh durável
        return new Queue(emissaoCartoesFila, true);

    }
}
