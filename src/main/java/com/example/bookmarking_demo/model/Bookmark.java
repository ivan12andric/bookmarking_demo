/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.bookmarking_demo.model;

import static com.example.bookmarking_demo.util.AppConstants.BOOKMARK_VRSTA_JAVNI;
import static com.example.bookmarking_demo.util.AppConstants.BOOKMARK_VRSTA_PRIVATNI;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@ApiModel(description = "Bookmark - link na web stranicu")
public class Bookmark implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@EqualsAndHashCode.Include
	@ApiModelProperty(notes = "Jedinistveni identifikator bookmarka", position = 1)
	private Long id;

	@Basic(optional = false)
	@ApiModelProperty(notes = "Naziv bookmarka", position = 2)
	private String naziv;

	@Basic(optional = false)
	@ApiModelProperty(notes = "Url bookmarka - link", position = 3)
	private String url;

	@Basic(optional = false)
	@ApiModelProperty(notes = "Vrsta bookmarka - JAVNI ili PRIVATNI", position = 4)
	private String vrsta;

	@ManyToOne
	@JoinColumn(name = "KORISNIK_ID")
	@ApiModelProperty(notes = "Vlasnik bookmarka", position = 5)
	private Korisnik vlasnik;

	@JsonIgnore
	@Basic(optional = false)
	private Boolean aktivan;

	@JsonIgnore
	public boolean isJavni() {
		return BOOKMARK_VRSTA_JAVNI.equals(vrsta);
	}

	@JsonIgnore
	public boolean isPrivatni() {
		return BOOKMARK_VRSTA_PRIVATNI.equals(vrsta);
	}

}
