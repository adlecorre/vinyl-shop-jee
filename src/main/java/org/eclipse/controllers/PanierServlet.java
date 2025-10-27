package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.eclipse.config.MySqlConnection;

import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;
import GestionPanier.LignePanier;
import GestionUtilisateurs.Utilisateur;
import utilitaires.ConnexionBD;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VinyleDAO vinyleDAO;

	@Override
	public void init() throws ServletException {
		try {
			vinyleDAO = new VinyleDAO(MySqlConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/panier.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession();
	    Map<Integer, LignePanier> panier = (Map<Integer, LignePanier>) session.getAttribute("panier");

	    if (panier == null) {
	        panier = new HashMap<>();
	    }

	    String action = request.getParameter("action");
	    String idParam = request.getParameter("id");
	    int idVinyle = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

	    // On ne cherche le vinyle que pour les actions liées à un article
	    Vinyle v = null;
	    if (!"valider".equals(action)) {
	        v = vinyleDAO.findById(idVinyle);
	        if (v == null) {
	            response.sendRedirect("catalogue");
	            return;
	        }
	    }

	    LignePanier ligne = panier.get(idVinyle);

	    switch (action) {
	        case "ajouter":
	            if (ligne == null)
	                panier.put(idVinyle, new LignePanier(v, 1));
	            else
	                ligne.setQuantite(ligne.getQuantite() + 1);
	            break;

	        case "plus":
	            if (ligne != null)
	                ligne.setQuantite(ligne.getQuantite() + 1);
	            break;

	        case "moins":
	            if (ligne != null) {
	                int qte = ligne.getQuantite() - 1;
	                if (qte > 0)
	                    ligne.setQuantite(qte);
	                else
	                    panier.remove(idVinyle);
	            }
	            break;

	        case "valider":
	            if (!panier.isEmpty()) {
	                try (Connection connection = MySqlConnection.getConnection()) {
	                    connection.setAutoCommit(false);

	                    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
	                    if (utilisateur == null) {
	                        request.setAttribute("erreur", "Vous devez être connecté pour valider une commande.");
	                        request.getRequestDispatcher("/WEB-INF/panier.jsp").forward(request, response);
	                        return;
	                    }

	                    PreparedStatement psCommande = connection.prepareStatement(
	                    	    "INSERT INTO commande (id_utilisateur, date_commande, statut_commande) VALUES (?, NOW(), 'EN_ATTENTE')",
	                    	    Statement.RETURN_GENERATED_KEYS);

	                    psCommande.setInt(1, utilisateur.getId());
                    	psCommande.executeUpdate();

	                    ResultSet rs = psCommande.getGeneratedKeys();
	                    int idCommande = 0;
	                    if (rs.next()) idCommande = rs.getInt(1);

	                    PreparedStatement psLigne = connection.prepareStatement(
	                    	    "INSERT INTO lignecommande (id_commande, id_vinyle, quantite) VALUES (?, ?, ?)");
	                    	for (LignePanier lp : panier.values()) {
	                    	    psLigne.setInt(1, idCommande);
	                    	    psLigne.setInt(2, lp.getVinyle().getId());
	                    	    psLigne.setInt(3, lp.getQuantite());
	                    	    psLigne.addBatch();
	                    	}


	                    psLigne.executeBatch();
	                    connection.commit();
	                    panier.clear();
	                    session.removeAttribute("panier");
	                    session.setAttribute("panier", new HashMap<>());
	                    request.setAttribute("message", "Commande validée avec succès !");

	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    request.setAttribute("erreur", "Erreur SQL lors de la validation de la commande : " + e.getMessage());
	                }

	            }
	            break;

	        default:
	            break;
	    }

	    session.setAttribute("panier", panier);
	    request.getRequestDispatcher("/WEB-INF/panier.jsp").forward(request, response);
	}

}
