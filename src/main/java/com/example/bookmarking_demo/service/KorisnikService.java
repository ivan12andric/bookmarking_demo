package com.example.bookmarking_demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmarking_demo.model.Korisnik;
import com.example.bookmarking_demo.repository.KorisnikRepository;

@Service
public class KorisnikService {

	@Autowired
	KorisnikRepository korisnikRepository;

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}

	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

}
