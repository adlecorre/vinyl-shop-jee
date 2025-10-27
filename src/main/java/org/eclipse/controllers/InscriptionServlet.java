package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilitaires.ConnexionBD;
import java.util.Date;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.eclipse.services.InscriptionService;

import GestionUtilisateurs.Role;
import GestionUtilisateurs.Utilisateur;
import GestionUtilisateurs.UtilisateurDAO;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDAO utilisateurDao;
	private InscriptionService inscriptionService;
	
	@Override
	public void init() throws ServletException {
		utilisateurDao = new UtilisateurDAO();
		inscriptionService = new InscriptionService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/inscription.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String mdp = request.getParameter("motDePasse");
		String dateNaissanceString = request.getParameter("dateNaissance");
		Date dateNaissance = null;
		String adresse = request.getParameter("adresse");
		String telephone = request.getParameter("telephone");
		
		var nomValide = inscriptionService.verificationNomPrenom(nom);
		var prenomValide = inscriptionService.verificationNomPrenom(prenom);
		var emailValide = inscriptionService.verificationEmail(email);
		
		if (dateNaissanceString != null && !dateNaissanceString.isEmpty()) {
		    try {
		        java.sql.Date sqlDate = java.sql.Date.valueOf(dateNaissanceString);
		        dateNaissance = new Date(sqlDate.getTime());
		    } catch (IllegalArgumentException e) {
		        e.printStackTrace();
		    }
		}
		
		if(!nomValide) {
			request.setAttribute("erreurNom", "Longueur min 2, première lettre en majuscule");
		}
		if(!prenomValide) {
			request.setAttribute("erreurPrenom", "Longueur min 2, première lettre en majuscule");
		}
		if(!emailValide) {
			request.setAttribute("erreurEmail", "L'email doit contenir un @");
		}
		
		// Vérification email et numéro de tels uniques
		boolean emailEtTelepehoneUniques = true;
		var utilisateurs = utilisateurDao.findAll();
		for (Utilisateur utilisateur : utilisateurs) {
			if(email.equals(utilisateur.getEmail())) {
				request.setAttribute("erreurEmail", "Un compte existe déjà avec cette adresse mail.");
				emailEtTelepehoneUniques = false;
			}
			
			if(telephone != null && !telephone.isEmpty()) {
				if (telephone.equals(utilisateur.getNumTel())) {
					request.setAttribute("erreurNumTel", "Un compte existe déjà avec ce numéro de téléphone.");
					emailEtTelepehoneUniques = false;
				}
			}
		}
		
		if (nomValide && prenomValide && emailValide && emailEtTelepehoneUniques) {
			utilisateurDao.create(new Utilisateur(nom, prenom, dateNaissance, email, adresse, telephone, Role.CLIENT, mdp));
			response.sendRedirect(request.getContextPath() + "/connexion");
		} else {
			request.setAttribute("utilisateurSaisi", new Utilisateur(nom, prenom, dateNaissance, email, adresse, telephone, Role.CLIENT, mdp));
			request.getRequestDispatcher("WEB-INF/inscription.jsp").forward(request, response);
		}
	}

}
