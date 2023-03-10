package com.example.msavaliadorcredito;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients // permite comunicação entre os microsservicos
@EnableRabbit // para permitir o envio de mensagens
public class MsavaliadorcreditoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsavaliadorcreditoApplication.class, args);
    }

}
