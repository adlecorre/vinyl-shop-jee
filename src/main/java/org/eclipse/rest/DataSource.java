package org.eclipse.rest;

import java.util.ArrayList;
import java.util.List;

import GestionVinyle.Artiste;
import GestionVinyle.Categorie;
import GestionVinyle.Vinyle;

public class DataSource {

	public static final List<Vinyle> VINYLES = new ArrayList<Vinyle>();
	public static final List<Artiste> ARTISTES = new ArrayList<Artiste>();
	public static final List<Categorie> CATEGORIES = new ArrayList<Categorie>();
	static {
		ARTISTES.add(new Artiste(1, "Daft Punk"));
		ARTISTES.add(new Artiste(2, "Fanny J"));
		VINYLES.add(new Vinyle(1, "Random Access Memory", ARTISTES.get(0), "URL1", 10, 32.0, "Album de Daft Punk"));
		VINYLES.add(new Vinyle(2, "Secrets de femme", ARTISTES.get(1), "URL2", 3, 19.0, "Album de Fanny J"));
		CATEGORIES.add(new Categorie(1, "JAZZ"));
		CATEGORIES.add(new Categorie(2, "RNB"));
	}
}
