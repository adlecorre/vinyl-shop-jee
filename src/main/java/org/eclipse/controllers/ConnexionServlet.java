package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import GestionUtilisateurs.Utilisateur;
import GestionUtilisateurs.UtilisateurDAO;


@WebServlet({"/connexion", "/deconnexion"})
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDAO utilisateurDAO;

	@Override
    public void init() throws ServletException {
        utilisateurDAO = new UtilisateurDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getServletPath().contains("deconnexion")) {
    		var session = request.getSession();
    		session.invalidate();
    	}
        request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        
        if (email == null || motDePasse == null || email.isBlank() || motDePasse.isBlank()) {
            request.setAttribute("erreur", "Veuillez saisir votre email et mot de passe.");
            request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
            return;
        }
        
        Utilisateur utilisateur = utilisateurDAO.findByEmailEtMdp(email.trim(), motDePasse.trim());
        
        if(utilisateur != null) {
        	request.getSession().setAttribute("utilisateur", utilisateur);
        	response.sendRedirect(request.getContextPath()+"/catalogue");
        } else {
        	request.setAttribute("erreur", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
        }
        
	}

}
