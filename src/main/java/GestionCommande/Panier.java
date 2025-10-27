package GestionCommande;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GestionVinyle.Vinyle;

public class Panier {
	private int id_panier;
	private double prix_total;
	private Map<Vinyle, Integer> panier = new HashMap<Vinyle, Integer>();

	public Panier(int id_panier, double prix_total) {
		super();
		this.id_panier = id_panier;
		this.prix_total = prix_total;
	}

	public Panier() {
	}

	public Map<Vinyle, Integer> getPanier() {
		return panier;
	}

	public void setPanier(Map<Vinyle, Integer> panier) {
		this.panier = panier;
	}

	public int getId_panier() {
		return id_panier;
	}

	public void setId_panier(int id_panier) {
		this.id_panier = id_panier;
	}

	public double getPrix_total() {
		return prix_total;
	}

	public void setPrix_total(double prix_total) {
		this.prix_total = prix_total;
	}

	@Override
	public String toString() {
		return "Panier [id_panier=" + id_panier + ", prix_total=" + prix_total + "]";
	}

	public void afficher() {
		if (panier.isEmpty()) {
			System.out.println("🛒 Votre panier est vide.");
			return;
		}
		System.out.println("\n=== CONTENU DU PANIER ===");
		double total = 0;
		for (Map.Entry<Vinyle, Integer> entry : panier.entrySet()) {
			Vinyle v = entry.getKey();
			int qte = entry.getValue();
			double sousTotal = qte * v.getPrixVinyle();
			total += sousTotal;
			System.out.printf("%s x%d -> %.2f€%n", v.getTitre(), qte, sousTotal);
		}
		System.out.printf("TOTAL : %.2f€%n", total);
	}
	
	public void ajouter(Vinyle v, int quantite) {
        if (v == null) return;
        int actuel = panier.getOrDefault(v, 0);
        panier.put(v, actuel + quantite);
        System.out.println("🛒 " + quantite + " exemplaire(s) de \"" + v.getTitre() + "\" ajouté(s) au panier.");
    }

}
