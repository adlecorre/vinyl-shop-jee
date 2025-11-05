package org.eclipse.rest;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;
import org.eclipse.config.MySqlConnection;

public class VinyleServices {

    private VinyleDAO vinyleDAO;

    public VinyleServices() {
        try {
            vinyleDAO = new VinyleDAO(MySqlConnection.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Erreur connexion BDD", e);
        }
    }

    private VinyleDTO toDto(Vinyle v) {
        VinyleDTO dto = new VinyleDTO();
        dto.setIdVinyle(v.getId());
        dto.setTitre(v.getTitre());
        dto.setPrixVinyle(v.getPrixVinyle());
        dto.setStock(v.getStock());
        dto.setDescription(v.getDescription());
        dto.setUrlPochette(v.getUrlPochette());
        if (v.getArtiste() != null) {
            dto.setArtisteDTO(new ArtisteDTO(v.getArtiste().getIdArtiste(), v.getArtiste().getNom()));
        }
        return dto;
    }

    // LIST ALL
    public List<VinyleDTO> findAll() {
        List<Vinyle> vinyles = vinyleDAO.findAll();
        return vinyles.stream().map(this::toDto).collect(Collectors.toList());
    }

    // FIND BY ID
    public VinyleDTO findBy(int id) {
        Vinyle v = vinyleDAO.findById(id);
        if (v == null) return null;
        return toDto(v);
    }

    // CREATE
    public VinyleDTO create(VinyleDTO dto) {
        Vinyle v = new Vinyle();
        v.setTitre(dto.getTitre());
        v.setPrix_vinyle(dto.getPrixVinyle());
        v.setUrl_pochette(dto.getUrlPochette());
        v.setStock(dto.getStock());
        v.setDescription(dto.getDescription());
        if (dto.getArtisteDTO() != null) {
            v.setArtiste(new GestionVinyle.Artiste(dto.getArtisteDTO().getIdArtiste(), dto.getArtisteDTO().getNom()));
        }
        vinyleDAO.create(v);
        return toDto(v);
    }

    // UPDATE
    public VinyleDTO update(int id, VinyleDTO dto) {
        Vinyle v = vinyleDAO.findById(id);
        if (v == null) return null;

        v.setTitre(dto.getTitre());
        v.setPrix_vinyle(dto.getPrixVinyle());
        v.setStock(dto.getStock());
        v.setDescription(dto.getDescription());
        v.setUrl_pochette(dto.getUrlPochette());
        if (dto.getArtisteDTO() != null) {
            v.setArtiste(new GestionVinyle.Artiste(dto.getArtisteDTO().getIdArtiste(), dto.getArtisteDTO().getNom()));
        }
        vinyleDAO.update(v);
        return toDto(v);
    }

    // DELETE
    public boolean delete(int id) {
        Vinyle v = vinyleDAO.findById(id);
        if (v == null) return false;
        if (vinyleDAO.delete(v) == null) {
        	return false;
        }
        return true;
    }
}
