package org.eclipse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;

@Path("/categorie")
@Tag(name = "categorie", description = "API de gestion des categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategorieResource {

	
CategorieServices categorieServices = new CategorieServices();
	
	@GET
	@Operation(summary = "Lister des categories", description = "Retourne la liste des categories.")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Liste des categories",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = CategorieDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de récupérer les categories",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	
	//@RolesAllowed({"api-user", "api-admin"})
		public Response findAll(@Context UriInfo uriInfo) {
			return Response.ok(categorieServices.findAll()).build();
		}
		
	@GET
	@Path("{id}")
	@Operation(summary = "Afficher un categorie selon l'ID", description = "Retourne le categorie demandé")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Categorie demandé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = CategorieDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de récupérer le categorie",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	public Response findById(@PathParam("id") int id, @Context UriInfo uriInfo) {
		CategorieDTO dto = categorieServices.findBy(id);
		if(dto == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorDTO(404, "Categorie introuvable", uriInfo.getPath()))
					.build();
		}
		return Response.ok(dto).build();
	}
	
	@Operation(summary = "Créer une categorie", description = "Crée une categorie (id, nom)")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "CAtegorie créé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = CategorieDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de créer la categorie",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	@POST
	//@RolesAllowed({"api-admin"})
	public Response create(CategorieDTO dto, @Context UriInfo uriInfo) {
		CategorieDTO created = categorieServices.create(dto);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(String.valueOf(created.getIdCategorie()));
		return Response.created(builder.build()).entity(created).build();
	}
	
	@Operation(summary = "Met à jour une categorie", description = "Met à jour les données de la categorie grâce à son ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Categorie mis à jour",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = CategorieDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de mettre à jour le categorie",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") int id, CategorieDTO dto, @Context UriInfo uriInfo) {
		CategorieDTO updated = categorieServices.update(id, dto);
		if(updated == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorDTO(404, "categorie introuvable", uriInfo.getPath()))
					.build();
		}
		return Response.ok(updated).build();
	}
	
	@Operation(summary = "Supprime une categorie", description = "Supprime une categorie selon l'ID donné")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Categorie supprimé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = CategorieDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de supprimer le categorie",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		boolean deleted = categorieServices.delete(id);
		if (deleted) return Response.ok().build();
		return Response.noContent().build(); // erreur 204
	}
	
}
