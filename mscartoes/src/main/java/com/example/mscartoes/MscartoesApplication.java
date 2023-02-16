package com.example.mscartoes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit // permite a configuraçao do rabbitmq
@Slf4j // permite logs
public class MscartoesApplication {

    public static void main(String[] args) {
        // tipos de logs -> mostra assim que iniciar o programa
        log.info("Informação {}", "teste info");
        log.error("Erro {}", "teste erro");
        log.warn("Aviso {}", "teste aviso");

        SpringApplication.run(MscartoesApplication.class, args);
    }

}
