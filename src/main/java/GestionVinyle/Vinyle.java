package GestionVinyle;

public class Vinyle {

	private int idVinyle;
	private String titre;
	private Artiste artiste;
	private String urlPochette;
	private int stock;
	private double prixVinyle;
	private String description;

	public Vinyle(int idVinyle, String titre, Artiste artiste, String urlPochette, int stock, Double prixVinyle, String description) {
		super();
		this.idVinyle = idVinyle;
		this.titre = titre;
		this.artiste = artiste;
		this.urlPochette = urlPochette;
		this.stock = stock;
		this.prixVinyle = prixVinyle;
		this.description = description;
	}

	public Vinyle(String titre, Artiste artiste, int stock, double prixVinyle) {
		this.titre = titre;
		this.artiste = artiste;
		this.stock = stock;
		this.prixVinyle = prixVinyle;
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

	public void setUrl_pochette(String urlPochette) {
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

	public void setPrix_vinyle(double prixVinyle) {
		this.prixVinyle = prixVinyle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public Artiste getArtiste() {
		return artiste;
	}

	public void setArtiste(Artiste artiste) {
		this.artiste = artiste;
	}

	

	@Override
	public String toString() {
		return "Vinyle [id_vinyle=" + idVinyle + ", titre=" + titre + ", artiste=" + artiste + ", url_pochette="
				+ urlPochette + ", stock=" + stock + ", prix_vinyle=" + prixVinyle + ", description=" + description
				+ "]";
	}

	public int getId() {
		return this.idVinyle;
	}

}
