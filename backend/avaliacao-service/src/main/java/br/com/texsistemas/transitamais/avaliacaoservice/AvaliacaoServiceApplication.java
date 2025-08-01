package br.com.texsistemas.transitamais.avaliacaoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AvaliacaoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoServiceApplication.class, args);
	}

}
