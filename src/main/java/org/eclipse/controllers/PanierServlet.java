package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.eclipse.config.MySqlConnection;

import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;
import GestionPanier.LignePanier;
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


    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Map<Integer, LignePanier> panier = (Map<Integer, LignePanier>) session.getAttribute("panier");

        if (panier == null) {
            panier = new HashMap<>();
        }

        String action = request.getParameter("action");
        int idVinyle = Integer.parseInt(request.getParameter("id"));

        Vinyle v = vinyleDAO.findById(idVinyle);
        if (v == null) {
            response.sendRedirect("catalogue");
            return;
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
                    if (qte > 0) ligne.setQuantite(qte);
                    else panier.remove(idVinyle);
                }
                break;

            default:
                break;
        }

        session.setAttribute("panier", panier);
        response.sendRedirect("catalogue");
    }
}
