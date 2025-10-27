package GestionVinyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utilitaires.ConnexionBD;
import utilitaires.DAO;

public class CategorieDAO extends DAO<Categorie> {
	private Connection connection;

	public CategorieDAO(Connection connection) {
		this.connection = connection;
	}

	// ajouter valeurs
	private void addValues(PreparedStatement ps, Categorie object) throws Exception {
		// Attributs obligatoires
		//ps.setInt(2, object.getIdCategorie());
		ps.setString(1, object.getNomCategorie());

	}

	@Override
	public Categorie findById(int id) {
		Categorie categorie = null;
		try {
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement("SELECT id_categorie, nom_categorie FROM Categorie WHERE id_categorie  = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id_categorie = rs.getInt("id_categorie");
				String nom_categorie = rs.getString("nom_categorie");
				categorie = new Categorie(id_categorie, nom_categorie);
			}
		} catch (Exception e) {
			System.out.println("ID introuvable !");
			e.printStackTrace();
		}
		return categorie;
	}

	@Override
	public List<Categorie> findAll() {
		List<Categorie> categories = new ArrayList<>();
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("SELECT * FROM Categorie");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id_categorie = rs.getInt("id_categorie");
				String nom_categorie = rs.getString("nom_categorie");
				Categorie categorie = new Categorie(id_categorie, nom_categorie);
				categories.add(categorie);
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer toutes les categories.");
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public Categorie create(Categorie object) {
		if (!(object instanceof Categorie))
			throw new IllegalArgumentException("L'objet n'est pas une Categorie.");
		try {
			// Création de la categorie dans la table
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement("INSERT INTO Categorie(nom_categorie) VALUES (?)",
							Statement.RETURN_GENERATED_KEYS);

			addValues(ps, object);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int id = rs.getInt(1);
		        object.setIdCategorie(id);
		    }
			return object;

		} catch (Exception e) {
			System.out.println("Impossible de créer la categorie.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Categorie delete(Categorie object) {
		if (!(object instanceof Categorie))
			throw new IllegalArgumentException("L'objet n'est pas une categorie.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement("DELETE FROM Categorie WHERE id_categorie = ?");
			ps.setInt(1, object.getIdCategorie());
			ps.executeUpdate();
			return object;
		} catch (Exception e) {
			System.out.println("Impossible de supprimer cette categorie.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Categorie update(Categorie object) {
		if (!(object instanceof Categorie))
			throw new IllegalArgumentException("L'objet n'est pas une categorie.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement("UPDATE categorie SET " + "nom_categorie = ?, " + "WHERE id_categorie = ?");

			addValues(ps, object);
			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		return object;
	}

}
