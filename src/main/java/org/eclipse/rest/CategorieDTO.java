package org.eclipse.rest;

import GestionVinyle.Categorie;

public class CategorieDTO {

	private int idCategorie;
	private String nomCategorie;

	public CategorieDTO() {

	}

	public CategorieDTO(int idCategorie, String nomCategorie) {
		super();
		this.idCategorie = idCategorie;
		this.nomCategorie = nomCategorie;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	public Categorie getCategorieDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
