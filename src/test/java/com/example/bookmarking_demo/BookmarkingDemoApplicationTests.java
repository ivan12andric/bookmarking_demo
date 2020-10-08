package com.example.bookmarking_demo;

import static com.example.bookmarking_demo.util.AppConstants.BOOKMARK_VRSTA_JAVNI;
import static com.example.bookmarking_demo.util.AppConstants.BOOKMARK_VRSTA_PRIVATNI;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bookmarking_demo.model.Bookmark;
import com.example.bookmarking_demo.model.Korisnik;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkingDemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	ObjectWriter jsonObjectWriter = new ObjectMapper()
			.disable(MapperFeature.USE_ANNOTATIONS) // disable JsonProperty anotation for testing
			.writer()
			.withDefaultPrettyPrinter();

	@Test
	@Order(1)
	public void testNeautoriziranogPristupa() throws Exception {
		this.mockMvc.perform(
				get("/")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized());

	}

	@Test
	@Order(2)
	@WithMockUser("admin")
	public void testPregledKorisnika() throws Exception {
		this.mockMvc.perform(
				get("/api/korisnici")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$[0].korisnickoIme", is("admin")));

	}

	@Test
	@Order(3)
	@WithMockUser("admin")
	public void testKreiranjeNovogKorisnika() throws Exception {

		Korisnik korisnik = Korisnik.builder()
				.korisnickoIme("pero")
				.lozinka("pero")
				.build();

		this.mockMvc.perform(
				post("/api/korisnik")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(korisnik)))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	@Order(4)
	@WithMockUser("admin")
	public void testKreiranjeNovogJavnogBookmarka() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.naziv("bookmark1")
				.url("http://bla1")
				.vrsta(BOOKMARK_VRSTA_JAVNI)
				.build();

		this.mockMvc.perform(
				post("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	@Order(5)
	@WithMockUser("admin")
	public void testKreiranjeNovogPrivatnogBookmarkaUserAdmin() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.naziv("bookmark2")
				.url("http://bla2")
				.vrsta(BOOKMARK_VRSTA_PRIVATNI)
				.build();

		this.mockMvc.perform(
				post("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	@Order(6)
	@WithMockUser("pero")
	public void testKreiranjeNovogPrivatnogBookmarkaUserPero() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.naziv("bookmark3")
				.url("http://bla3")
				.vrsta(BOOKMARK_VRSTA_PRIVATNI)
				.build();

		this.mockMvc.perform(
				post("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	@Order(7)
	@WithMockUser("admin")
	public void testPregledJavnihBookmarkova() throws Exception {
		this.mockMvc.perform(
				get("/api/bookmark-javni")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$[0].naziv", is("bookmark1")));

	}

	@Test
	@Order(8)
	@WithMockUser("admin")
	public void testPregledPrivatnihBookmarkovaUserAdmin() throws Exception {
		this.mockMvc.perform(
				get("/api/bookmark-privatni")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$[0].naziv", is("bookmark2")));

	}

	@Test
	@Order(9)
	@WithMockUser("pero")
	public void testPregledPrivatnihBookmarkovaUserPero() throws Exception {
		this.mockMvc.perform(
				get("/api/bookmark-privatni")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", isA(ArrayList.class)))
				.andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$[0].naziv", is("bookmark3")));

	}

	@Test
	@Order(10)
	@WithMockUser("admin")
	public void testAžuriranjePrivatnogBookmarkaNaKojiNemamoPravo() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.id(3L)
				.naziv("bookmark3_neispravna_izmjena")
				.url("http://bla3_neispravna_izmjena")
				.vrsta(BOOKMARK_VRSTA_PRIVATNI)
				.build();

		this.mockMvc.perform(
				post("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.opisGreske", is("Privatni bookmark može ažurirati samo vlasnik")));

	}

	@Test
	@Order(11)
	@WithMockUser("admin")
	public void testAžuriranjePrivatnogBookmarkaNaKojiImamoPravo() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.id(2L)
				.naziv("bookmark2_ispravna_izmjena")
				.url("http://bla2_ispravna_izmjena")
				.vrsta(BOOKMARK_VRSTA_PRIVATNI)
				.build();

		this.mockMvc.perform(
				post("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	@Order(12)
	@WithMockUser("admin")
	public void testBrisanjePrivatnogBookmarkaNaKojiNemamoPravo() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.id(3L)
				.build();

		this.mockMvc.perform(
				delete("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.opisGreske", is("Privatni bookmark može brisati samo vlasnik")));

	}

	@Test
	@Order(13)
	@WithMockUser("admin")
	public void testBrisanjePrivatnogBookmarkaNaKojiImamoPravo() throws Exception {

		Bookmark bookmark = Bookmark.builder()
				.id(2L)
				.build();

		this.mockMvc.perform(
				delete("/api/bookmark")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObjectWriter.writeValueAsString(bookmark)))
				.andDo(print())
				.andExpect(status().isOk());

	}

}
