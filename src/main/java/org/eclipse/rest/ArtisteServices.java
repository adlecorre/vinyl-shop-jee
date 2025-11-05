package org.eclipse.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.config.MySqlConnection;

import GestionVinyle.Artiste;
import GestionVinyle.ArtisteDAO;
import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;

public class ArtisteServices {
	
	private ArtisteDAO artisteDAO;
	
	public ArtisteServices() {
        try {
            artisteDAO = new ArtisteDAO(MySqlConnection.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Erreur connexion BDD", e);
        }
    }
	
	private ArtisteDTO toDto (Artiste a) {
		ArtisteDTO dto = new ArtisteDTO();
		dto.setIdArtiste(a.getIdArtiste());
		dto.setNom(a.getNom());
		return dto;
	}
	
	// CREATE
	public ArtisteDTO create(ArtisteDTO dto) {
		Artiste a = new Artiste();
		a.setNom(dto.getNom());
		artisteDAO.create(a);
		return toDto(a);
	}
	
	// FIND BY ID
	public ArtisteDTO findById(int id) {
		Artiste a = artisteDAO.findById(id);
		if (a == null) return null;
		return toDto(a);
	}
	
	// LIST ALL
	public List<ArtisteDTO> findAll(){
		List<Artiste> artistes = artisteDAO.findAll();
        return artistes.stream().map(this::toDto).collect(Collectors.toList());
	}
	
	// UPDATE
	public ArtisteDTO update(int id, ArtisteDTO dto) {
		Artiste a = artisteDAO.findById(id);
		a.setNom(dto.getNom());
		artisteDAO.update(a);
		return toDto(a);
	}
	
	// DELETE
	public boolean delete(int id) {
		Artiste a = artisteDAO.findById(id);
		if (a == null) return false;
		if (artisteDAO.delete(a) == null) return false;
		return true;
	}


}
