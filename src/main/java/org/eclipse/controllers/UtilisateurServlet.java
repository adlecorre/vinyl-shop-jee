package org.eclipse.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilitaires.ConnexionBD;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.config.MySqlConnection;

import GestionUtilisateurs.Utilisateur;
import GestionUtilisateurs.UtilisateurDAO;

@WebServlet("/utilisateur")
public class UtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UtilisateurDAO utilisateurDAO;

	@Override
	public void init() throws ServletException {
		try {
			utilisateurDAO = new UtilisateurDAO(MySqlConnection.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//aller chercher une ressource

		 String idParam = request.getParameter("id");
	        int id = (idParam != null) ? Integer.parseInt(idParam) : 1; // par défaut 1
	        
	        Utilisateur user = utilisateurDAO.findById(id);;
	        request.setAttribute("user", user);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/utilisateur.jsp");
	        dispatcher.forward(request, response);
	        System.out.println("user");
		/*RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utilisateur.jsp");
		dispatcher.forward(request, response);*/
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // enoyer et recevoir 
		/*try {
			
			int id = Integer.parseInt(request.getParameter("id")); // ex: ?id=1
			var user = utilisateurDAO.findById(id);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/personne.jsp").forward(request, response);
			System.out.println("user");
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
	}

}
