package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Fabricant;
import vn.loto.rest01.security.Tokened;

import java.util.List;

@Path("/fabricants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Fabricant", description = "Crud sur la table fabricant")

public class FabricantResource {
        @GET
        @Operation(summary = "Tous fabricants", description = "Liste des fabricants")
        @ApiResponse(responseCode = "200", description = "Ok!")
        @ApiResponse(responseCode = "404", description = "Liste des fabricants introuvable")

        public Response getAllFabricant() {
            List<Fabricant> fabricants = DAOFactory.getFabricantDAO().getAll();
            return Response.ok(fabricants).build();
        }

        @GET
        @Path("/{id}")
        @Operation(summary = "fabricant par id", description = "Rechercher un fabricant par son identifiant")
        @ApiResponse(responseCode = "200", description = "Ok! Fabricant trouvé")
        @ApiResponse(responseCode = "404", description = "Fabricant introuvable")

        public Response getFabricantById(@PathParam("id") Integer id) {
            Fabricant fabricant = DAOFactory.getFabricantDAO().getByID(id);
            if (fabricant != null) {
                return Response.ok(fabricant).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        @POST
        @Operation(summary = "Insérer", description = "Insérer un fabricant par nom")
        @ApiResponse(responseCode = "201", description = "La ressource a été crée")
        @ApiResponse(responseCode = "400", description = "fabricant null")
        @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
        public Response insert(Fabricant fabricant){
            if (fabricant == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (DAOFactory.getFabricantDAO().insert(fabricant)){
                return Response.ok().status(Response.Status.CREATED).build();
            }else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PUT
        @Path("/{id}")
        @Operation(summary = "Modifier", description = "Mettre à jour un fabricant")
        @ApiResponse(responseCode = "200", description = "OK!")
        @ApiResponse(responseCode = "400", description = "fabricant null")
        @ApiResponse(responseCode = "404", description = "fabricant introuvable")
        @ApiResponse(responseCode = "409", description = "Le nom du fabricant est dupliqué")
        @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")

        public Response update(@PathParam("id") Integer id, Fabricant fabricant){
            if (id == null || fabricant == null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (id != fabricant.getId()){
                return Response.status(Response.Status.CONFLICT).entity(fabricant).build();
            }
            if (DAOFactory.getFabricantDAO().update(fabricant)){
                return Response.ok(fabricant).status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
    @DELETE
    @Path("/{id}")
    @Tokened
    @Operation(summary = "Supprimer", description = "Supprimer un fabricant")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "fabricant null")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")

    public Response delete(@PathParam("id") Integer id) {
        if (id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Fabricant fabricant = new Fabricant(id, ""); // Create a dummy object with ID for deletion
        if (DAOFactory.getFabricantDAO().delete(fabricant)) {
            return Response.ok().status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

