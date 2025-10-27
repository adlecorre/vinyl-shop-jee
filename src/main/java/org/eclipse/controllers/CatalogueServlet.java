package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;

@WebServlet("/catalogue")
public class CatalogueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VinyleDAO vinyleDAO;

	@Override
	public void init() throws ServletException {
		try (Connection connection = MySqlConnection.getConnection()) {
			vinyleDAO = new VinyleDAO(connection);
		} catch (Exception e) {
			throw new ServletException("Erreur d'initialisation de la connexion à la base de données", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String search = request.getParameter("search");
		List<Vinyle> vinyles;

		if (search != null && !search.trim().isEmpty()) {
			vinyles = vinyleDAO.findByTitreOuArtiste(search.trim());
			request.setAttribute("search", search);
		} else {
			vinyles = vinyleDAO.findAll();
		}

		request.setAttribute("vinyles", vinyles);
		request.getRequestDispatcher("/WEB-INF/catalogue.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		if ("ajouter".equals(action)) {
			int idVinyle = Integer.parseInt(request.getParameter("idVinyle"));
			Vinyle vinyle = vinyleDAO.findById(idVinyle);

			@SuppressWarnings("unchecked")
			List<Vinyle> panier = (List<Vinyle>) session.getAttribute("panier");

			if (panier == null) {
				panier = new ArrayList<>();
			}
			panier.add(vinyle);
			session.setAttribute("panier", panier);

			request.setAttribute("message", vinyle.getTitre() + " ajouté au panier !");
		}

		doGet(request, response);
	}
}
