package org.eclipse.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		var path = req.getServletPath();
		var utilisateur = req.getSession().getAttribute("utilisateur");
		
		if(path.contains("utilisateur") && utilisateur == null) { // non authentifié
			res.sendRedirect(req.getContextPath() + "/connexion");
		} else {
			chain.doFilter(request, response); // je passe la main au filtre suivant
		}
	}

}
