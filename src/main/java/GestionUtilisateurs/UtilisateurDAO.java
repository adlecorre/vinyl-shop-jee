package GestionUtilisateurs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.config.MySqlConnection;

import utilitaires.ConnexionBD;
import utilitaires.DAO;

public class UtilisateurDAO extends DAO<Utilisateur> {
	private Connection connection;

    public UtilisateurDAO(Connection connection) {
        this.connection = connection;
    }
    
	public UtilisateurDAO() {
		// TODO Auto-generated constructor stub
	}

	private void addValues(PreparedStatement ps, Utilisateur object) throws Exception {
		// Attributs obligatoires
		ps.setString(1, object.getNom());
		ps.setString(2,  object.getPrenom());
		ps.setString(3, object.getMotDePasse());
		ps.setString(5, object.getEmail());
		
		// Marche seulement pour les updates
		if (object.getId() != 0) {
			ps.setInt(9, object.getId());
		}
					
		// Attributs optionnels
		if (object.getDateNaissance() != null) {
			ps.setDate(4, (java.sql.Date) object.getDateNaissance());
		}else {
			ps.setNull(4, Types.DATE);
		}
					
		if (object.getAdresse() != null) {
			ps.setString(6, object.getAdresse());
		}else {
			ps.setNull(6, Types.VARCHAR);
		}
					
		if (object.getNumTel() != null) {
			ps.setString(7, object.getNumTel());
		}else {
			ps.setNull(7, Types.VARCHAR);
		}
					
		if (object.getRole() != null) {
			ps.setString(8, object.getRole().name());
		}else {
			ps.setString(8, "CLIENT");
		}
	}

	@Override
	public Utilisateur findById(int id) {
		Utilisateur utilisateur = null;
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"SELECT id_utilisateur, nom_utilisateur, prenom_utilisateur, email_utilisateur, role_utilisateur FROM utilisateur WHERE id_utilisateur  = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id_utilisateur = rs.getInt("id_utilisateur");
				String nom = rs.getString("nom_utilisateur");
				String prenom = rs.getString("prenom_utilisateur");
				String email = rs.getString("email_utilisateur");
				Role role = Role.valueOf(rs.getString("role_utilisateur"));
				utilisateur = new Utilisateur(id_utilisateur, nom, prenom, email, role);
			}
		} catch (Exception e) {
			System.out.println("ID introuvable !");
			e.printStackTrace();
		}
		return utilisateur;
	}
	
	public Utilisateur findByEmailEtMdp(String email, String mdp) {
	    String sql = "SELECT * FROM utilisateur WHERE email_utilisateur = ? AND mot_de_passe = ?";

	    try (Connection connection = MySqlConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setString(1, email);
	        ps.setString(2, mdp);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Utilisateur u = new Utilisateur();
	                u.setId(rs.getInt("id_utilisateur"));
	                u.setNom(rs.getString("nom_utilisateur"));
	                u.setPrenom(rs.getString("prenom_utilisateur"));
	                u.setMotDePasse(rs.getString("mot_de_passe"));
	                u.setEmail(rs.getString("email_utilisateur"));
	                u.setAdresse(rs.getString("adresse_utilisateur"));
	                u.setNumTel(rs.getString("tel_utilisateur"));
	                u.setRole(Role.fromString(rs.getString("role_utilisateur")));

	                java.sql.Date dateSql = rs.getDate("date_naissance");
	                if (dateSql != null) {
	                    u.setDateNaissance(new java.util.Date(dateSql.getTime()));
	                }
	                return u;
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("❌ Erreur lors de la recherche utilisateur : " + e.getMessage());
	    }

	    return null;
	}


	@Override
	public List<Utilisateur> findAll() {
		List<Utilisateur> utilisateurs = new ArrayList<>();
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("SELECT * FROM utilisateur");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id_utilisateur");
				String nom = rs.getString("nom_utilisateur");
				String prenom = rs.getString("prenom_utilisateur");
				String motDePasse = rs.getString("mot_de_passe");
				Date dateNaissance = rs.getDate("date_naissance");
				String email = rs.getString("email_utilisateur");
				String adresse = rs.getString("adresse_utilisateur");
				String numTel = rs.getString("tel_utilisateur");
				Role role = Role.valueOf(rs.getString("role_utilisateur"));
				Utilisateur utilisateur = new Utilisateur(id, nom, prenom, dateNaissance, email, adresse, numTel, role, motDePasse);
				utilisateurs.add(utilisateur);
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer tous les utilisateurs.");
			e.printStackTrace();
		}
		return utilisateurs;
	}

	@Override
	public Utilisateur create(Utilisateur object) {
		if (!(object instanceof Utilisateur)) throw new IllegalArgumentException("L'objet n'est pas un Utilisateur.");
		try {
			// Création de l'utilisateur dans la table
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"INSERT INTO utilisateur(nom_utilisateur, prenom_utilisateur, mot_de_passe, date_naissance, email_utilisateur, adresse_utilisateur, tel_utilisateur, role_utilisateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
					 Statement.RETURN_GENERATED_KEYS);
			
			addValues(ps, object);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int id = rs.getInt(1);
		        object.setId(id);
		    }
			return object;
			
		} catch (Exception e) {
			System.out.println("Impossible de créer utilisateur.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Utilisateur delete(Utilisateur object) {
		if (!(object instanceof Utilisateur)) throw new IllegalArgumentException("L'objet n'est pas un Utilisateur.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement("DELETE FROM utilisateur WHERE id_utilisateur = ?");
			ps.setInt(1, object.getId());
			ps.executeUpdate();
			return object;
		} catch (Exception e) {
			System.out.println("Impossible de supprimer cet utilisateur.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Utilisateur update(Utilisateur object) {
		if (!(object instanceof Utilisateur)) throw new IllegalArgumentException("L'objet n'est pas un Utilisateur.");
		try {
			PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(
					"UPDATE utilisateur SET "
					+ "nom_utilisateur = ?, "
					+ "prenom_utilisateur = ?, "
					+ "mot_de_passe = ?, "
					+ "date_naissance = ?, "
					+ "email_utilisateur = ?, "
					+ "adresse_utilisateur = ?, "
					+ "tel_utilisateur = ?, "
					+ "role_utilisateur = ? "
					+ "WHERE id_utilisateur = ?");
			
			addValues(ps, object);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		return object;
	}


}
