package org.eclipse.filters;


import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		System.out.println("Filter");

		var chemin = req.getServletPath();
		var utilisateur = req.getSession().getAttribute("utilisateur");
		if ((chemin.contains("catalogue") 
				|| chemin.contains("profil")
				|| chemin.contains("panier")
				|| chemin.contains("commandes")
				|| chemin.contains("utilisateur")) && utilisateur == null) {
			res.sendRedirect(req.getContextPath() + "/connexion");

		} else {
			chain.doFilter(request, response);
		}
	}

}
