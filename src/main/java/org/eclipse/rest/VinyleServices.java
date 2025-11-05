package org.eclipse.rest;

import java.util.ArrayList;
import java.util.List;

import GestionVinyle.Artiste;
import GestionVinyle.Vinyle;

public class VinyleServices {
	
	public static VinyleDTO toDto (Vinyle v) {
		VinyleDTO dto = new VinyleDTO();
		dto.setArtisteDTO(new ArtisteDTO(v.getArtiste().getIdArtiste(), v.getArtiste().getNom()));
		dto.setDescription(v.getDescription());
		dto.setIdVinyle(v.getIdVinyle());
		dto.setPrixVinyle(v.getPrixVinyle());
		dto.setStock(v.getStock());
		dto.setTitre(v.getTitre());
		dto.setUrlPochette(v.getUrlPochette());
		return dto;
	}

	// CREATE
	public VinyleDTO create(VinyleDTO dto) {
		int newID = DataSource.VINYLES.size()+1;
		Vinyle v = new Vinyle();
		v.setIdVinyle(newID);
		Artiste a = new Artiste(dto.getArtisteDTO().getIdArtiste(), dto.getArtisteDTO().getNom());
		v.setArtiste(a);
		v.setDescription(dto.getDescription());
		v.setPrix_vinyle(dto.getPrixVinyle());
		v.setStock(dto.getStock());
		v.setTitre(dto.getTitre());
		v.setUrl_pochette(dto.getUrlPochette());
		DataSource.VINYLES.add(v);
		return toDto(v);
	}
	
	// READ
	public VinyleDTO findBy(int id) {
		for (Vinyle v : DataSource.VINYLES) {
			if(v.getId() == id) {
				return toDto(v);
			}
		}
		return null;
	}
	
	// LIST
	public List<VinyleDTO> findAll() {
		List<VinyleDTO> vinyles = new ArrayList<VinyleDTO>();
		for(Vinyle v : DataSource.VINYLES) {
			vinyles.add(toDto(v));
		}
		return vinyles;
	}
	
	// UPDATE
	public VinyleDTO update(int id, VinyleDTO dto) {
		for(Vinyle v: DataSource.VINYLES) {
			if(v.getId() == id) {
				// Mise à jour des champs
				Artiste a = new Artiste(dto.getArtisteDTO().getIdArtiste(), dto.getArtisteDTO().getNom());
				v.setArtiste(a);
				v.setDescription(dto.getDescription());
				v.setPrix_vinyle(dto.getPrixVinyle());
				v.setStock(dto.getStock());
				v.setTitre(dto.getTitre());
				v.setUrl_pochette(dto.getUrlPochette());
				return toDto(v);
			}
		}
		return null;
	}
	
	// DELETE
	public boolean delete(int id) {
		for(int i = 0; i < DataSource.VINYLES.size(); i++) {
			Vinyle v = DataSource.VINYLES.get(i);
			if(v.getId() == id) {
				DataSource.VINYLES.remove(i);
				return true;
			}
		}
		return false;
	}
}
