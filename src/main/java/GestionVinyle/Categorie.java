package GestionVinyle;

public class Categorie {
	private int idCategorie;
	private String nomCategorie;

	
	public Categorie(int idCategorie, String nomCategorie) {
		
		this.idCategorie = idCategorie;
		this.nomCategorie = nomCategorie;
	}
	
	

	public Categorie(String nomCategorie) {
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

	public void setNomCategorie(String nom_categorie) {
		this.nomCategorie = nom_categorie;
	}

	@Override
	public String toString() {
		return "Categorie [id_categorie=" + idCategorie + ", nom_categorie=" + nomCategorie + "]";
	}

}
