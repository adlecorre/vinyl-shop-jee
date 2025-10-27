package GestionVinyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utilitaires.ConnexionBD;
import utilitaires.DAO;

public class ArtisteDAO extends DAO<Artiste>{
	private Connection connection;

    public ArtisteDAO(Connection connection) {
        this.connection = connection;
    }
    
    private void addValues(PreparedStatement ps, Artiste object) throws Exception {
		ps.setString(1,  object.getNom());
		
		if (object.getIdArtiste() != 0) {
			ps.setInt(2, object.getIdArtiste());
		}
	}

	@Override
	public Artiste findById(int id) {
		Artiste artiste = null;
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"SELECT * FROM artiste WHERE id_artiste  = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id_artiste = rs.getInt("id_artiste");
				String nom = rs.getString("nom_artiste");
				artiste = new Artiste(id_artiste, nom);
			}
		} catch (Exception e) {
			System.out.println("ID introuvable !");
			e.printStackTrace();
		}
		return artiste;
	}
	
	public Artiste findByName(String nom) {
		Artiste artiste = null;
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"SELECT * FROM artiste WHERE nom_artiste  = ?");
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id_artiste = rs.getInt("id_artiste");
				artiste = new Artiste(id_artiste, nom);
			}
		} catch (Exception e) {
			System.out.println("Nom introuvable !");
			e.printStackTrace();
		}
		return artiste;
	}

	@Override
	public List<Artiste> findAll() {
		List<Artiste> artistes = new ArrayList<>();
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("SELECT * FROM Artiste");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id_artiste = rs.getInt("id_artiste");
				String nom = rs.getString("nom_artiste");
				artistes.add(new Artiste(id_artiste, nom));
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer tous les artistes.");
			e.printStackTrace();
		}
		return artistes;
	}

	@Override
	public Artiste create(Artiste object) {
		if (!(object instanceof Artiste)) throw new IllegalArgumentException("L'objet n'est pas un Artiste.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"INSERT INTO artiste(nom_artiste) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			addValues(ps, object);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int id = rs.getInt(1);
		        object.setIdArtiste(id);
		    }
			return object;
			
		} catch (Exception e) {
			System.out.println("Impossible de créer cet artiste.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Artiste delete(Artiste object) {
		if (!(object instanceof Artiste)) throw new IllegalArgumentException("L'objet n'est pas un Artiste.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("DELETE FROM artiste WHERE id_artiste = ?");
			ps.setInt(1, object.getIdArtiste());
			ps.executeUpdate();
			return object;
		} catch (Exception e) {
			System.out.println("Impossible de supprimer cet artiste.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Artiste update(Artiste object) {
		if (!(object instanceof Artiste)) throw new IllegalArgumentException("L'objet n'est pas un Artiste.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"UPDATE artiste SET "
					+ "nom_artiste = ? "
					+ "WHERE id_artiste = ?");
			addValues(ps, object);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		return object;
	}

}
