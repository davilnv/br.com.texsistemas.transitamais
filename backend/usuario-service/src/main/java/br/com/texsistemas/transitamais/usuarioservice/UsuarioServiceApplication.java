package br.com.texsistemas.transitamais.usuarioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UsuarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioServiceApplication.class, args);
	}

}
