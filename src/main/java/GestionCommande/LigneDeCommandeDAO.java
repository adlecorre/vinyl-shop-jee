package GestionCommande;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;

import GestionUtilisateurs.UtilisateurDAO;
import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;
import utilitaires.DAO;

public class LigneDeCommandeDAO extends DAO<LigneDeCommande> {
	
	public LigneDeCommandeDAO() {
	}

	@Override
	public LigneDeCommande findById(int id) {
		LigneDeCommande ligneDeCommande = null;
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"SELECT id_commande, id_vinyle, quantite FROM LigneCommande WHERE id_ligne  = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				CommandeDao commandeDao = new CommandeDao();
				VinyleDAO vinyleDao = new VinyleDAO();
				int idCommande = rs.getInt("id_commande");
				int idVinyle = rs.getInt("id_vinyle");
				int quantite = rs.getInt("quantite");
				Commande commande = commandeDao.findById(idCommande);
				Vinyle vinyle = vinyleDao.findById(idVinyle);
				ligneDeCommande = new LigneDeCommande(commande, vinyle, quantite);
			}
		} catch (Exception e) {
			System.out.println("ID introuvable !");
			e.printStackTrace();
		}
		return ligneDeCommande;
	}

	@Override
	public List<LigneDeCommande> findAll() {
		List<LigneDeCommande> lignes = new ArrayList<>();
		CommandeDao commandeDao = new CommandeDao();
		VinyleDAO vinyleDao = new VinyleDAO();
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement("SELECT * FROM LigneCommande");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idLigne = rs.getInt("id_ligne");
				int idCommande = rs.getInt("id_commande");
				int idVinyle = rs.getInt("id_vinyle");
				int quantite = rs.getInt("quantite");
				Commande commande = commandeDao.findById(idCommande);
				Vinyle vinyle = vinyleDao.findById(idVinyle);
				LigneDeCommande ligneDeCommande = new LigneDeCommande(idLigne, commande, vinyle, quantite);
				lignes.add(ligneDeCommande);
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer toutes les lignes de commande.");
			e.printStackTrace();
		}
		return lignes;
	}
	
	public List<LigneDeCommande> findByCommandeId(int idCommande) {
		List<LigneDeCommande> lignes = new ArrayList<>();
		CommandeDao commandeDao = new CommandeDao();
		VinyleDAO vinyleDao = new VinyleDAO();
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement("SELECT * FROM LigneCommande WHERE id_commande = ?");
			ps.setInt(1, idCommande);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idLigne = rs.getInt("id_ligne");
				int idVinyle = rs.getInt("id_vinyle");
				int quantite = rs.getInt("quantite");
				Commande commande = commandeDao.findById(idCommande);
				Vinyle vinyle = vinyleDao.findById(idVinyle);
				LigneDeCommande ligneDeCommande = new LigneDeCommande(idLigne, commande, vinyle, quantite);
				lignes.add(ligneDeCommande);
			}
		} catch (Exception e) {
			System.out.println("Impossible de récupérer toutes les lignes de commande.");
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public LigneDeCommande create(LigneDeCommande object) {
		if (!(object instanceof LigneDeCommande)) throw new IllegalArgumentException("L'objet n'est pas une ligne de commande.");
		try {
			// Création de la ligne de commande dans la table
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"INSERT INTO LigneCommande(id_commande, id_vinyle, quantite) VALUES (?, ?, ?)",
					 Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, object.getCommande().getIdCommande());
			ps.setInt(2, object.getVinyle().getId());
			ps.setInt(3, object.getQuantite());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int id = rs.getInt(1);
		        object.setIdLigne(id);
		    }
			return object;
			
		} catch (Exception e) {
			System.out.println("Impossible de créer une ligne de commande.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LigneDeCommande delete(LigneDeCommande object) {
		if (!(object instanceof LigneDeCommande)) throw new IllegalArgumentException("L'objet n'est pas une ligne de commande.");
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement("DELETE FROM LigneCommande WHERE id_ligne = ?");
			ps.setInt(1, object.getIdLigne());
			ps.executeUpdate();
			return object;
		} catch (Exception e) {
			System.out.println("Impossible de supprimer cette ligne de commande.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LigneDeCommande update(LigneDeCommande object) {
		if (!(object instanceof LigneDeCommande)) throw new IllegalArgumentException("L'objet n'est pas une Commande.");
		try {
			PreparedStatement ps = MySqlConnection.getConnection().prepareStatement(
					"UPDATE LigneCommande SET "
					+ "id_commande = ?, "
					+ "id_vinyle = ?, "
					+ "quantite = ?");
			
			ps.setInt(1, object.getCommande().getIdCommande());
			ps.setInt(2, object.getVinyle().getId());
			ps.setInt(3, object.getQuantite());
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Impossible de modifier la ligne de commande.");
			e.printStackTrace();
		}
		return object;
	}

}
