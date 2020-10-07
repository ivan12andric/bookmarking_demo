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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	@Autowired
	KorisnikService korisnikService;

	@GetMapping(value = "/bookmark-javni")
	@ApiOperation(value = "Pregled javnih bookmark-ova")
	public List<Bookmark> getJavni() {
		List<Bookmark> bookmarkList = bookmarkService.findJavni();

		return bookmarkList;
	}

	@GetMapping(value = "/bookmark-privatni")
	@ApiOperation(value = "Pregled privatnih bookmark-ova")
	public List<Bookmark> getPrivatni() {
		List<Bookmark> bookmarkList = bookmarkService.findPrivatniForVlasnik(
				korisnikService.findAuthenticatedKorisnik());

		return bookmarkList;
	}

	@PostMapping(value = "/bookmark")
	@ApiOperation(value = "Kreiranje / ažuriranje bookmark-a. Za ažuriranje je potrebno navesti jedinstveni identifikator.")
	public void save(@ApiParam(value = "Bookmark koji se kreira / ažurira", required = true, name = "Bookmark") @RequestBody Bookmark bookmark) throws Exception {
		bookmarkService.save(bookmark);
	}

	@DeleteMapping(value = "/bookmark")
	@ApiOperation(value = "Brisanje bookmark-a. Za brisanje je potrebno navesti jedinstveni identifikator")
	public void delete(@ApiParam(value = "Bookmark koji se briše", required = true, name = "Korisnik") @RequestBody Bookmark bookmark) throws Exception {
		bookmarkService.delete(bookmark);
	}

}
