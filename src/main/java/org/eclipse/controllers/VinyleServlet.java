package org.eclipse.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilitaires.ConnexionBD;

import java.io.IOException;
import java.util.List;

import GestionVinyle.Vinyle;
import GestionVinyle.VinyleDAO;


@WebServlet("/vinyle")
public class VinyleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	VinyleDAO vinyleDAO;
	
	@Override
	public void init() throws ServletException {
		vinyleDAO = new VinyleDAO(ConnexionBD.getConnection());
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Vinyle> vinyles = vinyleDAO.findAll();
		request.setAttribute("vinyles", vinyles);
		request.getRequestDispatcher("/WEB-INF/vinyle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
