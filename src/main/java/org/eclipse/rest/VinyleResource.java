package org.eclipse.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("/vinyle")
@Tag(name = "vinyles", description = "API de gestion dse vinyles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VinyleResource {
	
	VinyleServices vinyleServices = new VinyleServices();
	
	@GET
	@Operation(summary = "Lister les vinyles", description = "Retourne la liste des vinyles.")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Liste des vinyles",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = VinyleDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de récupérer les vinyles",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	
	//@RolesAllowed({"api-user", "api-admin"})
	public Response findAll(@Context UriInfo uriInfo) {
		return Response.ok(vinyleServices.findAll()).build();
	}
	
	@GET
	@Path("{idVinyle}")
	@Operation(summary = "Afficher un vinyle selon l'ID", description = "Retourne le vinyle demandé")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Vinyle demandé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = VinyleDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de récupérer le vinyle",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	public Response findById(@PathParam("idVinyle") int id, @Context UriInfo uriInfo) {
		VinyleDTO dto = vinyleServices.findBy(id);
		if(dto == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorDTO(404, "Vinyle introuvable", uriInfo.getPath()))
					.build();
		}
		return Response.ok(dto).build();
	}
	
	@Operation(summary = "Créer un vinyle", description = "Crée un vinyle (titre, artiste, prix, stock, url pochette, description)")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Vinyle créé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = VinyleDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de créer le vinyle",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	@POST
	//@RolesAllowed({"api-admin"})
	public Response create(VinyleDTO dto, @Context UriInfo uriInfo) {
		VinyleDTO created = vinyleServices.create(dto);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(String.valueOf(created.getId()));
		return Response.created(builder.build()).entity(created).build();
	}
	
	@Operation(summary = "Met à jour un vinyle", description = "Met à jour les données du vinyle grâce à son ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Vinyle mis à jour",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = VinyleDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de mettre à jour le vinyle",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	@PUT
	@Path("{idVinyle}")
	public Response update(@PathParam("idVinyle") int id, VinyleDTO dto, @Context UriInfo uriInfo) {
		VinyleDTO updated = vinyleServices.update(id, dto);
		if(updated == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorDTO(404, "Vinyle introuvable", uriInfo.getPath()))
					.build();
		}
		return Response.ok(updated).build();
	}
	
	@Operation(summary = "Supprime un vinyle", description = "Supprime un vinyle selon l'ID donné")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
					 description = "Vinyle supprimé",
					 content = @Content(mediaType = "application/json",
					 array = @ArraySchema(schema = @Schema(implementation = VinyleDTO.class)))), 
		@ApiResponse(responseCode = "400",
					 description = "Impossible de supprimer le vinyle",
					 content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
		
	})
	@DELETE
	@Path("{idVinyle}")
	public Response delete(@PathParam("idVinyle") int id) {
		boolean deleted = vinyleServices.delete(id);
		if (deleted) return Response.ok().build();
		return Response.noContent().build(); // erreur 204
	}
	
}
