package org.eclipse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;

@Path("/artiste")
@Tag(name = "Artiste", description = "API de gestion des artistes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtisteResource {
	
	ArtisteServices artisteService = new ArtisteServices();
	
	@GET
	@RolesAllowed({"api-admin", "api-user"})
	@Operation(summary = "Lister les artistes", description = "Retourne la liste des artistes.")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Liste des artistes",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = ArtisteDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de récupérer les artistes",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	
	public Response findAll(@Context UriInfo uriInfo) {
		return Response.ok(artisteService.findAll()).build();
	}
	
	
	@GET
	@RolesAllowed({"api-admin", "api-user"})
	@Path("{idArtiste}")
	@Operation(summary = "Afficher un artiste selon l'ID", description = "Retourne l'artiste demandé")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Artiste demandé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = ArtisteDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de récupérer l'artiste",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	public Response findById(@PathParam("idArtiste") int id, @Context UriInfo uriInfo) {
		ArtisteDTO dto = artisteService.findById(id);
		if (dto == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorDTO(404, "Artiste introuvable", uriInfo.getPath()))
					.build();
		}
		return Response.ok(dto).build();
	}
	
	@POST
	@RolesAllowed({"api-admin"})
	@Operation(summary = "Créer un artiste", description = "Crée un artiste (nom)")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Artiste créé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = ArtisteDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de créer l'artiste",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	public Response create(ArtisteDTO dto, @Context UriInfo uriInfo) {
		ArtisteDTO created = artisteService.create(dto);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(String.valueOf(created.getIdArtiste()));
		return Response.created(builder.build()).entity(created).build();
	}
	
	@PUT
	@RolesAllowed({"api-admin"})
	@Path("{idArtiste}")
	@Operation(summary = "Met à jour un artiste", description = "Met à jour les données de l'artiste grâce à son ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Artiste mis à jour",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = ArtisteDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de mettre à jour l'artiste",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	public Response update(ArtisteDTO dto, @PathParam("idArtiste") int id, @Context UriInfo uriInfo) {
		ArtisteDTO updated = artisteService.update(id, dto);
		if (updated == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorDTO(404, "Artiste introuvable", uriInfo.getPath()))
					.build();
		}
		return Response.ok(updated).build();
	}
	
	@DELETE
	@RolesAllowed({"api-admin"})
	@Path("{idArtiste}")
	@Operation(summary = "Supprime un artiste", description = "Supprime un artiste selon l'ID donné")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Artiste supprimé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = ArtisteDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de supprimer l'artiste",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	public Response delete(@PathParam("idArtiste") int id) {
		boolean deleted = artisteService.delete(id);
		if (deleted) return Response.ok().build();
		return Response.noContent().build();
	}

}
