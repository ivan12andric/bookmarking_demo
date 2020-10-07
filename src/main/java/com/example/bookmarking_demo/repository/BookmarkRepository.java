package com.example.bookmarking_demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookmarking_demo.model.Bookmark;
import com.example.bookmarking_demo.model.Korisnik;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByIdAndAktivan(Long id, Boolean aktivan);

	List<Bookmark> findByVrstaAndAktivan(String vrsta, Boolean aktivan);

	List<Bookmark> findByVlasnikAndVrstaAndAktivan(Korisnik korisnik, String vrsta, Boolean aktivan);

}
