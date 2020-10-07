package com.example.bookmarking_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookmarking_demo.model.Korisnik;
import com.example.bookmarking_demo.service.KorisnikService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BookmarkingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarkingDemoApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.bookmarking_demo.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	CommandLineRunner initDatabase(KorisnikService korisnikService) {
		return args -> {
			korisnikService.save(
					Korisnik.builder()
							.korisnickoIme("admin")
							.lozinka("admin")
							.aktivan(true)
							.build());
		};
	}

}
