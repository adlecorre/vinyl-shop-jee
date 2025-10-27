package GestionCommande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.eclipse.config.MySqlConnection;

import GestionUtilisateurs.Role;
import GestionUtilisateurs.Utilisateur;
import GestionUtilisateurs.UtilisateurDAO;
import utilitaires.DAO;

public class CommandeDao extends DAO<Commande> {
	private Connection connection;

    public CommandeDao(Connection connection) {
        this.connection = connection;
    }
    
	public CommandeDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Commande findById(int id) {
		Commande commande = null;
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"SELECT id_commande, date_commande, statut_commande, id_utilisateur FROM commande WHERE id_commande  = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				UtilisateurDAO utilisateurDao = new UtilisateurDAO();
				int idUtilisateur = rs.getInt("id_utilisateur");
				Date dateCommande = rs.getDate("date_commande");
				StatutCommande statutCommande = StatutCommande.valueOf(rs.getString("statut_commande"));
				Utilisateur utilisateur = utilisateurDao.findById(idUtilisateur);
				commande = new Commande(id, statutCommande, dateCommande, utilisateur);
			}
		} catch (Exception e) {
			System.out.println("ID introuvable !");
			e.printStackTrace();
		}
		return commande;
	}
	
	public List<Commande> findByUtilisateurId(int idUtilisateur) {
		List<Commande> commandes = null;
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"SELECT * FROM commande WHERE id_utilisateur  = ?");
			ps.setInt(1, idUtilisateur);
			ResultSet rs = ps.executeQuery();
			commandes = new ArrayList<Commande>();
			while(rs.next()) {
				UtilisateurDAO utilisateurDao = new UtilisateurDAO();
				Utilisateur utilisateur = utilisateurDao.findById(idUtilisateur);
				int idCommande = rs.getInt("id_commande");
				Date dateCommande = rs.getDate("date_commande");
				StatutCommande statutCommande = StatutCommande.valueOf(rs.getString("statut_commande"));
				Commande commande = new Commande(idCommande, statutCommande, dateCommande, utilisateur);
				
				// Charger et remplir les lignes de commande
	            LigneDeCommandeDAO ligneDao = new LigneDeCommandeDAO();
	            var lignes = ligneDao.findByCommandeId(idCommande);
	            for (LigneDeCommande l : lignes) {
	                commande.getVinyles().put(l.getVinyle(), l.getQuantite());
	            }

	            commandes.add(commande);
			}
		} catch (Exception e) {
			System.out.println("Problème de récupération des commandes");
			e.printStackTrace();
		}
		return commandes;
	}

	@Override
	public List<Commande> findAll() {
		List<Commande> commandes = new ArrayList<>();
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement("SELECT * FROM commande");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UtilisateurDAO utilisateurDao = new UtilisateurDAO();
				int id = rs.getInt("id_commande");
				Date dateCommande = rs.getDate("date_commande");
				StatutCommande statutCommande = StatutCommande.valueOf(rs.getString("statut_commande"));
				int idUtilisateur = rs.getInt("id_utilisateur");
				Utilisateur utilisateur = utilisateurDao.findById(idUtilisateur);
				Commande commande = new Commande(id, statutCommande, dateCommande, utilisateur);
				commandes.add(commande);
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer toutes les commandes.");
			e.printStackTrace();
		}
		return commandes;
	}

	@Override
	public Commande create(Commande object) {
		if (!(object instanceof Commande)) throw new IllegalArgumentException("L'objet n'est pas une Commande.");
		try {
			// Création de la commande dans la table
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"INSERT INTO commande(date_commande, statut_commande, id_utilisateur) VALUES (?, ?, ?)",
					 Statement.RETURN_GENERATED_KEYS);
			
			if (object.getDateCommande() != null) {
				ps.setDate(1, object.getDateCommande());
			}else {
				ps.setNull(1, Types.DATE);
			}
			
			ps.setString(2, object.getStatutCommande().toString());
			ps.setInt(3, object.getUtilisateur().getId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int id = rs.getInt(1);
		        object.setIdCommande(id);
		    }
			return object;
			
		} catch (Exception e) {
			System.out.println("Impossible de créer une commande.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Commande delete(Commande object) {
		if (!(object instanceof Commande)) throw new IllegalArgumentException("L'objet n'est pas une Commande.");
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement("DELETE FROM commande WHERE id_commande = ?");
			ps.setInt(1, object.getIdCommande());
			ps.executeUpdate();
			return object;
		} catch (Exception e) {
			System.out.println("Impossible de supprimer cette commande.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Commande update(Commande object) {
		if (!(object instanceof Commande)) throw new IllegalArgumentException("L'objet n'est pas une Commande.");
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"UPDATE commande SET "
					+ "date_commande = ?, "
					+ "statut_commande = ?, "
					+ "id_utilisateur = ?");
			
			if (object.getDateCommande() != null) {
				ps.setDate(1, object.getDateCommande());
			}else {
				ps.setNull(1, Types.DATE);
			}
			
			ps.setString(2, object.getStatutCommande().toString());
			ps.setInt(3, object.getUtilisateur().getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		return object;
	}


}
