/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.bookmarking_demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ApiModel(description = "Korisnik - korisnički račun")
public class Korisnik implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@EqualsAndHashCode.Include
	@ApiModelProperty(notes = "Jedinistveni identifikator korisnika", position = 1)
	private Long id;

	@Basic(optional = false)
	@Column(name = "KORISNICKO_IME")
	@ApiModelProperty(notes = "Korisničko ime", position = 2)
	private String korisnickoIme;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Basic(optional = false)
	@ApiModelProperty(notes = "Lozinka", position = 3)
	private String lozinka;

	@JsonIgnore
	@OneToMany(mappedBy = "vlasnik")
	private List<Bookmark> bookmarkList;

	@JsonIgnore
	@Basic(optional = false)
	private Boolean aktivan;

}
