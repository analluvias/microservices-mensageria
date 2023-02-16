package com.example.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.util.ConditionalOnBootstrapEnabled;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient // se cadastra no servidor eureka
@EnableDiscoveryClient //you can use it to discover service instances from the Eureka Server.
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}

	// este é o local em que vou redirecionar de localhost:8080/algum-ms para
	// o microsserviço de fato
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
					//  lb://msclientes -> nome que cadastrei no application yml
					.route(r -> r.path("/clientes/**").uri("lb://msclientes"))
					.route(r -> r.path("/cartoes/**").uri("lb://mscartoes"))
					.route(r -> r.path("/avaliacoes-credito/**").uri("lb://msavaliadorcredito"))
				.build();
	}
}
