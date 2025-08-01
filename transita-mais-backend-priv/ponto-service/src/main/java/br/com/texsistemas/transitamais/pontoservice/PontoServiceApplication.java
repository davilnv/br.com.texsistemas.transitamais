package br.com.texsistemas.transitamais.pontoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PontoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontoServiceApplication.class, args);
	}

}
