package GestionVinyle;

public class Artiste {
	private int idArtiste;
	private String nom;

	public Artiste(int idArtiste, String nom) {
		super();
		this.idArtiste = idArtiste;
		this.nom = nom;
	}

	public Artiste(String nom) {
		this.nom = nom;
	}

	public Artiste() {
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

	@Override
	public String toString() {
		return "Artiste [id_artiste=" + idArtiste + ", nom=" + nom + "]";
	}

}
