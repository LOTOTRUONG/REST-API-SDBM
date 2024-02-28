package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Pays;

import java.util.List;

@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Pays", description = "Crud sur la table pays")

public class PayResource {
    @GET
    @Operation(summary = "Tous pays", description = "Liste des pays")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des pays introuvable")    public Response getAllPays() {
        List<Pays> pays = DAOFactory.getPaysDAO().getAll();
        return Response.ok(pays).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "pays par id", description = "Rechercher un pays par son identifiant")
    @ApiResponse(responseCode = "200", description = "Ok! pays trouvé")
    @ApiResponse(responseCode = "404", description = "pays introuvable")
    public Response getContinentById(@PathParam("id") Integer id) {
        Pays pays = DAOFactory.getPaysDAO().getByID(id);
        if (pays != null) {
            return Response.ok(pays).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Operation(summary = "Insérer", description = "Insérer un pays par nom")
    @ApiResponse(responseCode = "201", description = "La ressource a été crée")
    @ApiResponse(responseCode = "400", description = "pays null")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response insert(Pays pays){
        if (pays == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (DAOFactory.getPaysDAO().insert(pays)){
            return Response.ok().status(Response.Status.CREATED).build();
        }else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modifier", description = "Mettre à jour un pays")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "pays null")
    @ApiResponse(responseCode = "404", description = "pays introuvable")
    @ApiResponse(responseCode = "409", description = "Le nom du pays est dupliqué")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response update(@PathParam("id") Integer id, Pays pays){
        if (id == null || pays == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (id != pays.getId()){
            return Response.status(Response.Status.CONFLICT).entity(pays).build();
        }
        if (DAOFactory.getPaysDAO().update(pays)){
            return Response.ok(pays).status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer", description = "Supprimer un pays")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "pays null")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response delete(@PathParam("id") Integer id) {
        if (id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Pays pays = new Pays(id, ""); // Create a dummy object with ID for deletion
        if (DAOFactory.getPaysDAO().delete(pays)) {
            return Response.ok().status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
