package com.docencia.com.examen.procesos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.docencia.com.examen.procesos.controllers.CliController;

/**
 * Clase principal de la aplicacion Spring Boot.
 * Inicializa el contexto de Spring y ejecuta la aplicacion CLI.
 */
@SpringBootApplication
public class ProcCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcCliApplication.class, args);
	}

	/**
	 * Bean que se ejecuta al iniciar la aplicacion.
	 * 
	 * @param controller Inyeccion del controlador CLI.
	 * @return CommandLineRunner que invoca el menu de consola.
	 */
	@Bean
	CommandLineRunner demo(CliController controller) {
		return args -> {
			controller.consoleMenu();
		};
	}
}
