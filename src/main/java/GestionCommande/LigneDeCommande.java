package GestionCommande;

import GestionVinyle.Vinyle;

public class LigneDeCommande {
	private int idLigne;
	private Commande commande;
	private Vinyle vinyle;
	private int quantite;
	
	public LigneDeCommande(int idLigne, Commande commande, Vinyle vinyle, int quantite) {
		this.idLigne = idLigne;
		this.commande = commande;
		this.vinyle = vinyle;
		this.quantite = quantite;
	}

	public LigneDeCommande(Commande commande, Vinyle vinyle, int quantite) {
		this.commande = commande;
		this.vinyle = vinyle;
		this.quantite = quantite;
	}
	
	

	public int getIdLigne() {
		return idLigne;
	}

	public void setIdLigne(int idLigne) {
		this.idLigne = idLigne;
	}

	public Vinyle getVinyle() {
		return vinyle;
	}

	public void setVinyle(Vinyle vinyle) {
		this.vinyle = vinyle;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	@Override
	public String toString() {
		return "LigneDeCommande [idLigne=" + idLigne + ", commande=" + commande + ", vinyle=" + vinyle + ", quantite="
				+ quantite + "]";
	}

	
	
	
}
