package org.eclipse.rest;

import java.util.ArrayList;
import java.util.List;

import GestionVinyle.Artiste;
import GestionVinyle.Vinyle;

public class ArtisteServices {
	
	public static ArtisteDTO toDto (Artiste a) {
		ArtisteDTO dto = new ArtisteDTO();
		dto.setIdArtiste(a.getIdArtiste());
		dto.setNom(a.getNom());
		return dto;
	}
	
	// CREATE
	public ArtisteDTO create(ArtisteDTO dto) {
		int newID = DataSource.ARTISTES.size() + 1;
		Artiste a = new Artiste(newID, dto.getNom());
		DataSource.ARTISTES.add(a);
		return toDto(a);
	}
	
	// READ
	public ArtisteDTO findById(int id) {
		for (Artiste a : DataSource.ARTISTES) {
			if (a.getIdArtiste() == id) {
				return toDto(a);
			}
		}
		return null;
	}
	
	// LIST
	public List<ArtisteDTO> findAll(){
		List<ArtisteDTO> artistes = new ArrayList<ArtisteDTO>();
		for (Artiste a : DataSource.ARTISTES) {
			artistes.add(toDto(a));
		}
		return artistes;
	}
	
	// UPDATE
	public ArtisteDTO update(int id, ArtisteDTO dto) {
		for (Artiste a : DataSource.ARTISTES) {
			if(a.getIdArtiste() == id) {
				a.setNom(dto.getNom());
				return toDto(a);
			}
		}
		return null;
	}
	
	// DELETE
	public boolean delete(int id) {
		for(int i = 0; i < DataSource.ARTISTES.size(); i++) {
			Artiste a = DataSource.ARTISTES.get(i);
			if(a.getIdArtiste() == id) {
				DataSource.ARTISTES.remove(i);
				return true;
			}
		}
		return false;
	}


}
