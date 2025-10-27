package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import GestionCommande.CommandeDao;
import GestionUtilisateurs.Utilisateur;

@WebServlet("/commandes")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommandeDao commandeDao;
	
	@Override
	public void init() throws ServletException {
		commandeDao = new CommandeDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var path = request.getServletPath();
		var session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		System.out.println("CONNECTÉ:" + utilisateur);
		
		if(utilisateur == null) {
		    response.sendRedirect(request.getContextPath() + "/connexion");
		    return;
		}
	
		var commandes = commandeDao.findByUtilisateurId(utilisateur.getId());
		request.setAttribute("commandes", commandes);
		request.getRequestDispatcher("WEB-INF/commandes.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
