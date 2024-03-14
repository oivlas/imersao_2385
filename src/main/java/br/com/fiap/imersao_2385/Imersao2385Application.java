package br.com.fiap.imersao_2385;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.fiap.imersao_2385")
public class Imersao2385Application {

	public static void main(String[] args) {
		SpringApplication.run(Imersao2385Application.class, args);
	}

}
