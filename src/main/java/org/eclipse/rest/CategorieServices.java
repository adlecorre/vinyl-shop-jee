package org.eclipse.rest;

import java.util.ArrayList;
import java.util.List;

import GestionVinyle.Categorie;

public class CategorieServices {

	public static CategorieDTO toDto(Categorie c) {
		CategorieDTO dto = new CategorieDTO();
		dto.setIdCategorie(c.getIdCategorie());
		dto.setNomCategorie(c.getNomCategorie());
		return dto;
	}

	// CREATE
	public CategorieDTO create(CategorieDTO dto) {
		int newID = DataSource.CATEGORIES.size() + 1;
		Categorie c = new Categorie();
		c.setIdCategorie(newID);
		c.setNomCategorie(dto.getNomCategorie());
		DataSource.CATEGORIES.add(c);
		return toDto(c);
	}

	// READ
	public CategorieDTO findBy(int id) {
		for (Categorie c : DataSource.CATEGORIES) {
			if (c.getIdCategorie() == id) {
				return toDto(c);
			}
		}
		return null;
	}

	// LIST
	public List<CategorieDTO> findAll() {
		List<CategorieDTO> categories = new ArrayList<CategorieDTO>();
		for (Categorie c : DataSource.CATEGORIES) {
			categories.add(toDto(c));
		}
		return categories;
	}

	// UPDATE
	public CategorieDTO update(int id, CategorieDTO dto) {
		for (Categorie c : DataSource.CATEGORIES) {
			if (c.getIdCategorie() == id) {
				// Mise à jour des champs
				c.setIdCategorie(dto.getIdCategorie());
				c.setNomCategorie(dto.getNomCategorie());
				return toDto(c);
			}
		}
		return null;
	}

	// DELETE
	public boolean delete(int id) {
		for (int i = 0; i < DataSource.CATEGORIES.size(); i++) {
			Categorie c = DataSource.CATEGORIES.get(i);
			if (c.getIdCategorie() == id) {
				DataSource.CATEGORIES.remove(i);
				return true;
			}
		}
		return false;
	}
}
