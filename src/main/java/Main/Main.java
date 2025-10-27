package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import GestionCommande.Panier;
import GestionUtilisateurs.Role;
import GestionUtilisateurs.Utilisateur;
import GestionUtilisateurs.UtilisateurDAO;
import GestionVinyle.Categorie;
import GestionVinyle.CategorieDAO;
import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;
import utilitaires.Clavier;
import utilitaires.ConnexionBD;

public class Main {
	private static UtilisateurDAO dao;
	private static CategorieDAO cao;
	private static VinyleDAO vao;
	static Panier panier = new Panier();

	public static void main(String[] args) {
		dao = new UtilisateurDAO(ConnexionBD.getConnection());
		cao = new CategorieDAO(ConnexionBD.getConnection());
		vao = new VinyleDAO(ConnexionBD.getConnection());
		Utilisateur utilisateurConnecte = null;
		boolean quitter = false;
		Scanner sc = Clavier.getScanner();
		System.out.println("\n=== Bienvenue sur la plateforme de vente de vinyles ===");

		while (!quitter) {
			if (utilisateurConnecte == null) {
				afficherMenuConnexion();
				int choix = Clavier.lireInt("👉 Votre choix : ", -1);
				switch (choix) {
				case 1 -> utilisateurConnecte = connexionUtilisateur();
				case 0 -> quitter = true;
				default -> System.out.println("⚠️ Choix invalide, réessayez.");
				}
			} else {
				if (utilisateurConnecte.getRole() == Role.ADMIN)
					quitter = menuAdmin(utilisateurConnecte);
				else
					quitter = menuClient(utilisateurConnecte);

				if (!quitter) {
					String reponse = Clavier.lireLigne("\nSe déconnecter ? (o/n) : ");
					if (reponse.equalsIgnoreCase("o"))
						utilisateurConnecte = null;
				}
			}
		}

		sc.close();
		ConnexionBD.fermerConnexion();
		System.out.println("👋 Au revoir !");

	}

	private static void afficherMenuConnexion() {
		System.out.println("\n=== MENU PRINCIPAL ===");
		System.out.println("1. Se connecter");
		System.out.println("0. Quitter");
	}

	private static Utilisateur connexionUtilisateur() {
		System.out.println("\n=== CONNEXION ===");
		String email = Clavier.lireLigne("Email : ");
		String mdp = Clavier.lireLigne("Mot de passe : ");
		Utilisateur u = dao.findByEmailEtMdp(email, mdp);
		if (u != null) {
			System.out.println("✅ Connecté en tant que " + u.getPrenom() + " (" + u.getRole() + ")");
		} else {
			System.out.println("❌ Identifiants incorrects.");
		}
		return u;
	}

	private static boolean menuAdmin(Utilisateur admin) {
		System.out.println("\n=== MENU ADMIN (" + admin.getNom() + ") ===");
		System.out.println("1. Ajouter un utilisateur");
		System.out.println("2. Afficher les utilisateurs");
		System.out.println("3. Supprimer un utilisateur");
		System.out.println("4. Afficher une catégorie");
		System.out.println("5. Ajouter une catégorie");
		System.out.println("6. Supprimer une catégorie");
		System.out.println("0. Quitter");
		int choix = Clavier.lireInt("👉 Votre choix : ", -1);

		switch (choix) {
		case 1 -> ajouterUtilisateur();
		case 2 -> afficherUtilisateurs();
		case 3 -> supprimerUtilisateur();
		case 4 -> afficherCategories();
		case 5 -> ajouterCategorie();
		case 6 -> supprimerCategorie();
		case 0 -> {
			return true;
		}
		default -> System.out.println("⚠️ Choix invalide !");
		}
		return false;
	}

	private static boolean menuClient(Utilisateur client) {
		System.out.println("\n=== MENU CLIENT (" + client.getNom() + ") ===");
		System.out.println("1. Consulter le catalogue");
		System.out.println("2. Voir le panier");
		System.out.println("3. Valider la commande");
		System.out.println("0. Quitter");
		int choix = Clavier.lireInt("👉 Votre choix : ", -1);

		switch (choix) {
		case 1 -> consulterCatalogue(panier);
		case 2 -> panier.afficher();
		case 3 -> System.out.println("Fonctionnalité à venir....");
		case 0 -> {
			return true;
		}
		default -> System.out.println("⚠️ Choix invalide !");
		}
		return false;
	}

	private static void ajouterUtilisateur() {
		System.out.println("\n=== AJOUT D'UN UTILISATEUR ===");
		String nom = Clavier.lireLigne("Nom : ");
		String prenom = Clavier.lireLigne("Prénom : ");
		String email = Clavier.lireLigne("Email : ");
		String mdp = Clavier.lireLigne("Mot de passe : ");
		Role role = Role.fromString(Clavier.lireLigne("Rôle (CLIENT / ADMIN) : "));
		Utilisateur u = new Utilisateur(nom, prenom, email, mdp, role);
		dao.create(u);
	}

	private static void afficherUtilisateurs() {
		List<Utilisateur> liste = dao.findAll();
		if (liste.isEmpty())
			System.out.println("Aucun utilisateur enregistré.");
		else
			liste.forEach(System.out::println);
	}

	private static void supprimerUtilisateur() {
		int id = Clavier.lireInt("\nID à supprimer : ", -1);
		dao.delete(dao.findById(id));
	}
	
	//gestion des actions pour la categorie
	private static void afficherCategories() {
		List<Categorie> listeC = cao.findAll();
		if (listeC.isEmpty())
			System.out.println("Aucune catégorie enregistré.");
		else
			listeC.forEach(System.out::println);
	}
	
	private static void ajouterCategorie() {
		System.out.println("\n=== AJOUT D'UNE CATEGORIE ===");
		int id_categorie = Clavier.lireInt("\nID à créer : ", -1);
		String nomCategorie = Clavier.lireLigne("Nom de la categorie: ");
		Categorie c = new Categorie(id_categorie, nomCategorie);
		cao.create(c);
	}
	
	private static void supprimerCategorie() {
		int id = Clavier.lireInt("\nID à supprimer : ", -1);
		cao.delete(cao.findById(id));
	}
	
	private static void consulterCatalogue(Panier panier) {
        List<Vinyle> vinyles = vao.findAll();
        if (vinyles.isEmpty()) {
            System.out.println("Aucun vinyle disponible.");
            return;
        }

        System.out.println("\n=== CATALOGUE DES VINYLES ===");
        vinyles.forEach(System.out::println);

        int id = Clavier.lireInt("\nEntrez l'ID du vinyle à ajouter au panier (0 pour annuler) : ", 0);
        if (id == 0) return;

        Vinyle v = vao.findById(id);
        if (v == null) {
            System.out.println("⚠️ Aucun vinyle trouvé avec cet ID.");
            return;
        }

        int qte = Clavier.lireInt("Quantité à ajouter : ", 0);
        if (qte <= 0) {
            System.out.println("⚠️ Quantité invalide.");
            return;
        }

        if (qte > v.getStock()) {
            System.out.println("⚠️ Stock insuffisant !");
            return;
        }

        panier.ajouter(v, qte);
    }
}
