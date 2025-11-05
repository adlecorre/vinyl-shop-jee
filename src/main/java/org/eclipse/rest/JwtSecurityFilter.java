package org.eclipse.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtSecurityFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext ctx) {

        String auth = ctx.getHeaderString("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            // pas de token -> pas d'utilisateur
            return;
        }

        String token = auth.substring("Bearer ".length());
        try {
        	Claims claims = JwtUtil.parseToken(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            SecurityContext originalCtx = ctx.getSecurityContext();

            SecurityContext sc = new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override
                public boolean isUserInRole(String r) {
                    if (role == null) return false;
                    // admin hérite de user
                    if (role.equals("api-admin")) {
                        return r.equals("api-admin") || r.equals("api-user");
                    }
                    return role.equals(r);
                }

                @Override
                public boolean isSecure() {
                    return originalCtx.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "JWT";
                }
            };

            ctx.setSecurityContext(sc);

        } catch (JwtException e) {
            // token invalide -> on laisse sans user, @RolesAllowed refusera
        }
    }
}
