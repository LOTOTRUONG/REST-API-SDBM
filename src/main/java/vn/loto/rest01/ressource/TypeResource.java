package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.Uri;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.dto.TypeDTO;
import vn.loto.rest01.metier.Type;
import vn.loto.rest01.security.Tokened;

import java.util.List;

@Path("/types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Type", description = "Crud sur la table type")

public class TypeResource {
    @Context
    UriInfo uriInfo;
    @GET
    @Operation(summary = "Toutes types", description = "Liste des types")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des types introuvable")
    public Response getAllTypes(){
        List<Type> types = DAOFactory.getTypeDAO().getAll();
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        List<TypeDTO> typeDTOList = TypeDTO.toDtoList(types, uriBuilder);
        return Response.ok(typeDTOList).status(200).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Type par id", description = "Rechercher une type par son identifiant")
    @ApiResponse(responseCode = "200", description = "Ok! Type trouvée")
    @ApiResponse(responseCode = "404", description = "Type introuvable")
    public Response getTypeById(@PathParam("id") Integer id){
        Type type = DAOFactory.getTypeDAO().getByID(id);
        if (type != null){
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("/types");
            TypeDTO typeDTO = new TypeDTO(type, uriBuilder);
            return Response.ok(typeDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    //method to create new Type
    @POST
    @Operation(summary = "Insérer", description = "Insérer une type par nom")
    @ApiResponse(responseCode = "201", description = "La ressource a été crée")
    @ApiResponse(responseCode = "400", description = "type nulle")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response insert(Type type){
        if (type == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (DAOFactory.getTypeDAO().insert(type)){
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modifier", description = "Mettre à jour une type")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "type nulle")
    @ApiResponse(responseCode = "404", description = "Type introuvable")
    @ApiResponse(responseCode = "409", description = "Le nom de la type est dupliqué")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response update(@PathParam("id") Integer id, Type type){
        if (type == null || id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (id != type.getId()){
            return Response.status(Response.Status.CONFLICT).build();
        }
        type.setId(id);
        if (DAOFactory.getTypeDAO().update(type)){
            return Response.ok(type).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Tokened
    @Operation(summary = "Supprimer", description = "Supprimer une type")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "type nulle")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response delete(@PathParam("id") Integer id){
        if (id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Type type = new Type();
        if (DAOFactory.getTypeDAO().delete(type)){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
