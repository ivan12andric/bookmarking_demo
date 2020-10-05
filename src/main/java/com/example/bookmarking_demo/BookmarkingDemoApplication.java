package com.example.bookmarking_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.bookmarking_demo.model.Korisnik;
import com.example.bookmarking_demo.service.KorisnikService;

@SpringBootApplication
public class BookmarkingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarkingDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(KorisnikService korisnikService) {
		return args -> {
			korisnikService.save(
					Korisnik.builder()
							.korisnickoIme("admin")
							.lozinka(new BCryptPasswordEncoder().encode("admin"))
							.aktivan(true)
							.build());
		};
	}

}
