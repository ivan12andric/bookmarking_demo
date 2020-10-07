package com.example.bookmarking_demo.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookmarking_demo.model.Korisnik;
import com.example.bookmarking_demo.repository.KorisnikRepository;

@Service
public class KorisnikService {

	@Autowired
	KorisnikRepository korisnikRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Optional<Korisnik> findById(Long id) {

		if (id == null) {
			return null;
		}

		return korisnikRepository.findByIdAndAktivan(id, true);
	}

	public List<Korisnik> findAll() {

		return korisnikRepository.findByAktivan(true);
	}

	public Korisnik findAuthenticatedKorisnik() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		return korisnikRepository.findByKorisnickoImeAndAktivan(currentPrincipalName, true).orElse(null);
	}

	public Korisnik findByKorisnickoIme(String korisnickoIme) {
		return korisnikRepository.findByKorisnickoImeAndAktivan(korisnickoIme, true).orElse(null);
	}

	public Korisnik save(Korisnik korisnik) throws Exception {

		if (korisnik == null) {
			return null;
		}

		boolean isNew = korisnik.getId() == null || findById(korisnik.getId()).isEmpty();

		//enkripcija lozinke za novog korisnika
		if (isNew) {

			if (StringUtils.isBlank(korisnik.getLozinka())) {
				throw new Exception("Lozinka je obavezna za novog korisnika");
			}

			korisnik.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));

		} else {// lozinka se ne može ažurirati za postojećeg korisnika

			if (StringUtils.isNotBlank(korisnik.getLozinka())) {
				throw new Exception("Lozinka se ne može ažurirati za postojećeg korisnika");
			}

			//dohvati i postavi trenutnu lozinku
			Korisnik existingKorisnik = findById(korisnik.getId()).get();
			korisnik.setLozinka(existingKorisnik.getLozinka());
		}

		//postavi aktivan
		korisnik.setAktivan(true);

		return korisnikRepository.save(korisnik);
	}

}
