package com.example.bookmarking_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmarking_demo.model.Bookmark;
import com.example.bookmarking_demo.service.BookmarkService;
import com.example.bookmarking_demo.service.KorisnikService;

@RestController
@RequestMapping(value = "/api")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	@Autowired
	KorisnikService korisnikService;

	@GetMapping(value = "/bookmark-javni")
	public List<Bookmark> getJavni() {
		List<Bookmark> bookmarkList = bookmarkService.findJavni();

		return bookmarkList;
	}

	@GetMapping(value = "/bookmark-privatni")
	public List<Bookmark> getPrivatni() {
		List<Bookmark> bookmarkList = bookmarkService.findPrivatniForVlasnik(
				korisnikService.findAuthenticatedKorisnik());

		return bookmarkList;
	}

	@PostMapping(value = "/bookmark")
	public void save(@RequestBody Bookmark bookmark) throws Exception {
		bookmarkService.save(bookmark);
	}

	@DeleteMapping(value = "/bookmark")
	public void delete(@RequestBody Bookmark bookmark) throws Exception {
		bookmarkService.delete(bookmark);
	}

}
