package org.eclipse.rest;

public class ArtisteDTO {
	private int idArtiste;
	private String nom;
	
	public ArtisteDTO() {
	}

	public ArtisteDTO(int idArtiste, String nom) {
		super();
		this.idArtiste = idArtiste;
		this.nom = nom;
	}

	public ArtisteDTO(String nom) {
		this.nom = nom;
	}

	public int getIdArtiste() {
		return idArtiste;
	}

	public void setIdArtiste(int id_artiste) {
		this.idArtiste = id_artiste;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}

