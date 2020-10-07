package com.example.bookmarking_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmarking_demo.model.Korisnik;
import com.example.bookmarking_demo.service.KorisnikService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api")
public class KorisnikController {

	@Autowired
	KorisnikService korisnikService;

	@GetMapping(value = "/korisnici")
	@ApiOperation(value = "Pregled korisnika")
	public List<Korisnik> getAll() {
		List<Korisnik> korisnikList = korisnikService.findAll();

		return korisnikList;
	}

	@PostMapping(value = "/korisnik")
	@ApiOperation(value = "Kreiranje / ažuriranje korisnika")
	public void save(@ApiParam(value = "Korisnik koji se kreira / ažurira", required = true, name = "Korisnik") @RequestBody Korisnik korisnik) throws Exception {
		korisnikService.save(korisnik);
	}

}
