package GestionPanier;

import GestionVinyle.Vinyle;

public class LignePanier {
    private Vinyle vinyle;
    private int quantite;

    public LignePanier(Vinyle vinyle, int quantite) {
        this.vinyle = vinyle;
        this.quantite = quantite;
    }

    public Vinyle getVinyle() {
        return vinyle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getSousTotal() {
        return vinyle.getPrixVinyle() * quantite;
    }
}
