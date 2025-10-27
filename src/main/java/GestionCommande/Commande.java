package GestionCommande;

import java.sql.Date;

import GestionUtilisateurs.Utilisateur;

public class Commande {
	private int idCommande;
	private StatutCommande statutCommande;
	private Date dateCommande;
	private Utilisateur utilisateur;

	public Commande(int idCommande, StatutCommande statutCommande) {
		super();
		this.idCommande = idCommande;
		this.statutCommande = statutCommande;
	}
	
	public Commande(int idCommande, StatutCommande statutCommande, Date dateCommande, Utilisateur utilisateur) {
		super();
		this.idCommande = idCommande;
		this.statutCommande = statutCommande;
		this.utilisateur = utilisateur;
	}

	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}

	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public StatutCommande getStatutCommande() {
		return statutCommande;
	}

	public void setStatutCommande(StatutCommande statutCommande) {
		this.statutCommande = statutCommande;
	}

	
	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public String toString() {
		return "Commande [id_commande=" + idCommande + ", statut_commande=" + statutCommande + ", date_commande="
				+ dateCommande + ", utilisateur=" + utilisateur + "]";
	}

	

	

}
