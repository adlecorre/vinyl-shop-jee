package org.eclipse.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
	private final AuthService authService = new AuthService();
	
	public static class loginRequest{
		public String username;
		public String password;
	}
	
	@POST
	@Path("/login")
	public Response login(loginRequest req) {
		var user = authService.authentificate(req.username, req.password);
		if (user == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		String token = JwtUtil.generateToken(req.username, req.password);
		return Response.ok("{\"token\":\"" + token + "\"}").build();
	}
}
