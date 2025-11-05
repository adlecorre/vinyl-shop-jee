package org.eclipse.rest;

public class VinyleDTO {
	private int idVinyle;
	private String titre;
	private ArtisteDTO artisteDTO;
	private String urlPochette;
	private int stock;
	private double prixVinyle;
	private String description;

	public VinyleDTO(int idVinyle, String titre, ArtisteDTO artisteDTO, String urlPochette, int stock, Double prixVinyle, String description) {
		super();
		this.idVinyle = idVinyle;
		this.titre = titre;
		this.artisteDTO = artisteDTO;
		this.urlPochette = urlPochette;
		this.stock = stock;
		this.prixVinyle = prixVinyle;
		this.description = description;
	}

	public VinyleDTO(String titre, ArtisteDTO artisteDTO, int stock, double prixVinyle) {
		this.titre = titre;
		this.artisteDTO = artisteDTO;
		this.stock = stock;
		this.prixVinyle = prixVinyle;
	}

	public VinyleDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getIdVinyle() {
		return idVinyle;
	}

	public void setIdVinyle(int idVinyle) {
		this.idVinyle = idVinyle;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getUrlPochette() {
		return urlPochette;
	}

	public void setUrlPochette(String urlPochette) {
		this.urlPochette = urlPochette;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Double getPrixVinyle() {
		return prixVinyle;
	}

	public void setPrixVinyle(double prixVinyle) {
		this.prixVinyle = prixVinyle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return this.idVinyle;
	}

	public ArtisteDTO getArtisteDTO() {
		return artisteDTO;
	}

	public void setArtisteDTO(ArtisteDTO artisteDTO) {
		this.artisteDTO = artisteDTO;
	}

	
	
	

}

