package com.example.bookmarking_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookmarking_demo.model.Korisnik;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	KorisnikService korisnikService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Korisnik korisnik = korisnikService.findByKorisnickoIme(username);

		if (korisnik == null || !korisnik.getAktivan()) {
			throw new UsernameNotFoundException(username);
		}

		return User.builder()
				.username(korisnik.getKorisnickoIme())
				.password(korisnik.getLozinka())
				.disabled(!korisnik.getAktivan())
				.roles("bla")
				.build();
	}

}
