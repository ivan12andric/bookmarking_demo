package com.example.bookmarking_demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookmarking_demo.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	List<Korisnik> findByAktivan(Boolean aktivan);

	Optional<Korisnik> findByIdAndAktivan(Long id, Boolean aktivan);

	Optional<Korisnik> findByKorisnickoImeAndAktivan(String korisnickoIme, Boolean aktivan);

}
