package com.example.bookmarking_demo.service;

import static com.example.bookmarking_demo.util.AppConstants.BOOKMARK_VRSTA_JAVNI;
import static com.example.bookmarking_demo.util.AppConstants.BOOKMARK_VRSTA_PRIVATNI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmarking_demo.model.Bookmark;
import com.example.bookmarking_demo.model.Korisnik;
import com.example.bookmarking_demo.repository.BookmarkRepository;

@Service
public class BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	KorisnikService korisnikService;

	public Optional<Bookmark> findById(Long id) {

		if (id == null) {
			return null;
		}

		return bookmarkRepository.findByIdAndAktivan(id, true);
	}

	public List<Bookmark> findPrivatniForVlasnik(Korisnik vlasnik) {
		return bookmarkRepository.findByVlasnikAndVrstaAndAktivan(vlasnik, BOOKMARK_VRSTA_PRIVATNI, true);
	}

	public List<Bookmark> findJavni() {
		return bookmarkRepository.findByVrstaAndAktivan(BOOKMARK_VRSTA_JAVNI, true);
	}

	public Bookmark save(Bookmark bookmark) throws Exception {

		if (bookmark == null) {
			return null;
		}

		boolean isNew = bookmark.getId() == null || findById(bookmark.getId()).isEmpty();

		//bookmark može biti javni ili privatni
		if (!bookmark.isJavni() && !bookmark.isPrivatni()) {
			throw new Exception("Nepoznata vrsta bookmark-a");
		}

		//postavi trenutno prijavljenog korisnika za novi privatni bookmark
		if (isNew && bookmark.isPrivatni()) {

			bookmark.setVlasnik(korisnikService.findAuthenticatedKorisnik());

		}

		if (!isNew) // postojeći bookmark
		{

			Bookmark existingBookmark = findById(bookmark.getId()).get();

			if (existingBookmark.isPrivatni() && !existingBookmark.getVlasnik().equals(korisnikService.findAuthenticatedKorisnik())) // privatni bookmark može ažurirati samo vlasnik
			{
				throw new Exception("Privatni bookmark može ažurirati samo vlasnik");
			}

			if (bookmark.getVlasnik() != null) {
				throw new Exception("Vlasnik se ne može ažurirati za postojeći bookmark");
			}

			//postavi trenutnog vlasnika

			bookmark.setVlasnik(existingBookmark.getVlasnik());
		}

		//postavi aktivan
		bookmark.setAktivan(true);

		return bookmarkRepository.save(bookmark);
	}

	public void delete(Bookmark bookmark) throws Exception {

		if (bookmark == null) {
			return;
		}

		boolean isNew = bookmark.getId() == null || findById(bookmark.getId()).isEmpty();

		//ne može se brisati nepostojeći bookmark
		if (isNew) {
			throw new Exception("Ne može se brisati nepostojeći bookmark");
		}

		Bookmark existingBookmark = findById(bookmark.getId()).get();

		if (!isNew && existingBookmark.isPrivatni()) // privatni bookmark može brisati samo vlasnik
		{
			if (!existingBookmark.getVlasnik().equals(korisnikService.findAuthenticatedKorisnik())) {
				throw new Exception("Privatni bookmark može brisati samo vlasnik");
			}

		}

		//deaktivacija - brisanje
		existingBookmark.setAktivan(false);

		bookmarkRepository.save(existingBookmark);
	}

}
