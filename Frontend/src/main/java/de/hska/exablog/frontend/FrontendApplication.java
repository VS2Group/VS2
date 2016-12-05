package de.hska.exablog.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(de.hska.exablog.datenbank.DatenbankApplication.class, args);
	}

}
