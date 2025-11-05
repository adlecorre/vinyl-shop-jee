package org.eclipse.rest;

import java.util.Set;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/openapi")
public class OpenApiResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context Application app) throws Exception{
		
		SwaggerConfiguration cfg = new SwaggerConfiguration()
				.openAPI(new OpenAPI())
				.resourcePackages(Set.of("org.eclipse.rest")).prettyPrint(true)
				.prettyPrint(true);
		
		var ctx = new JaxrsOpenApiContextBuilder<JaxrsOpenApiContextBuilder>()
				.application(app)
				.openApiConfiguration(cfg)
				.buildContext(true);
		
		OpenAPI openAPI = ctx.read();
		String json = Json.mapper().writeValueAsString(openAPI);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
}
