package GestionVinyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import GestionUtilisateurs.Role;
import GestionUtilisateurs.Utilisateur;
import utilitaires.ConnexionBD;
import utilitaires.DAO;

public class VinyleDAO extends DAO<Vinyle>{
	private Connection connection;

    public VinyleDAO(Connection connection) {
        this.connection = connection;
    }

    private void addValues(PreparedStatement ps, Vinyle object) throws Exception {
		// Attributs obligatoires
		ps.setString(1,  object.getTitre());
		ps.setInt(3, object.getStock());
		ps.setDouble(4, object.getPrixVinyle());
		ps.setInt(6, object.getArtiste().getIdArtiste());


		// Marche seulement pour les updates
		if (object.getId() != 0) {
			ps.setInt(7, object.getId());
		}
					
		// Attributs optionnels
		if (object.getUrlPochette() != null) {
			ps.setString(2, object.getUrlPochette());
		}else {
			ps.setNull(2, Types.VARCHAR);
		}
					
		if (object.getDescription() != null) {
			ps.setString(5, object.getDescription());
		}else {
			ps.setNull(5, Types.VARCHAR);
		}
	}
    
	@Override
	public Vinyle findById(int id) {
		Vinyle vinyle = null;
		ArtisteDAO artisteDAO = new ArtisteDAO(connection);
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"SELECT * FROM vinyle WHERE id_vinyle  = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id_vinyle = rs.getInt("id_vinyle");
				String titre = rs.getString("titre");
				Artiste artiste = artisteDAO.findById(rs.getInt("id_artiste"));
				String url_pochette = rs.getString("url_pochette");
				int stock = rs.getInt("stock");
				double prix = rs.getInt("prix_vinyle");
				String description = rs.getString("description_vinyle");
				vinyle = new Vinyle(id_vinyle, titre, artiste, url_pochette, stock, prix, description);
			}
		} catch (Exception e) {
			System.out.println("ID introuvable !");
			e.printStackTrace();
		}
		return vinyle;
	}

	@Override
	public List<Vinyle> findAll() {
		List<Vinyle> vinyls = new ArrayList<>();
		ArtisteDAO artisteDAO = new ArtisteDAO(connection);
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("SELECT * FROM Vinyle");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id_vinyle = rs.getInt("id_vinyle");
				String titre = rs.getString("titre");
				Artiste artiste = artisteDAO.findById(rs.getInt("id_artiste"));
				String url_pochette = rs.getString("url_pochette");
				int stock = rs.getInt("stock");
				double prix = rs.getInt("prix_vinyle");
				String description = rs.getString("description_vinyle");
				Vinyle vinyle = new Vinyle(id_vinyle, titre, artiste, url_pochette, stock, prix, description);
				vinyls.add(vinyle);
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer tous les vinyls.");
			e.printStackTrace();
		}
		return vinyls;
	}

	@Override
	public Vinyle create(Vinyle object) {
		if (!(object instanceof Vinyle)) throw new IllegalArgumentException("L'objet n'est pas un Vinyle.");
		try {
			// Création de l'utilisateur dans la table
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"INSERT INTO vinyle(titre, url_pochette, stock, prix_vinyle, description_vinyle, id_artiste) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			addValues(ps, object);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int id = rs.getInt(1);
		        object.setIdVinyle(id);
		    }
			return object;
			
		} catch (Exception e) {
			System.out.println("Impossible de créer ce vinyle.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Vinyle delete(Vinyle object) {
		if (!(object instanceof Vinyle)) throw new IllegalArgumentException("L'objet n'est pas un Vinyle.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("DELETE FROM vinyle WHERE id_vinyle = ?");
			ps.setInt(1, object.getId());
			ps.executeUpdate();
			return object;
		} catch (Exception e) {
			System.out.println("Impossible de supprimer ce vinyle.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Vinyle update(Vinyle object) {
		if (!(object instanceof Vinyle)) throw new IllegalArgumentException("L'objet n'est pas un Vinyle.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"UPDATE vinyle SET "
					+ "titre = ?, "
					+ "url_pochette = ?, "
					+ "stock = ?, "
					+ "prix_vinyle = ?, "
					+ "description_vinyle = ? ,"
					+ "id_artiste = ? "
					+ "WHERE id_vinyle = ?");
			addValues(ps, object);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		return object;
	}
	
}
