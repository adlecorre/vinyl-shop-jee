package GestionCommande;

import java.time.LocalDate;

public class Commande {
	private int id_commande;
	private StatutCommande statut_commande;
	private LocalDate date_commande;

	public Commande(int id_commande, StatutCommande statut_commande) {
		super();
		this.id_commande = id_commande;
		this.statut_commande = statut_commande;
	}

	public Commande(LocalDate date_commande) {
		super();
		this.date_commande = date_commande;
	}

	public int getId_commande() {
		return id_commande;
	}

	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public StatutCommande getStatut_commande() {
		return statut_commande;
	}

	public void setStatut_commande(StatutCommande statut_commande) {
		this.statut_commande = statut_commande;
	}

	
	public LocalDate getDate_commande() {
		return date_commande;
	}

	public void setDate_commande(LocalDate date_commande) {
		this.date_commande = date_commande;
	}

	@Override
	public String toString() {
		return "Commande [id_commande=" + id_commande + ", statut_commande=" + statut_commande + ", date_commande="
				+ date_commande + "]";
	}

	

}
