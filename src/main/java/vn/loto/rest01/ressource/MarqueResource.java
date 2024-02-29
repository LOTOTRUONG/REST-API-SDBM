package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.dto.MarqueDTO;
import vn.loto.rest01.metier.Fabricant;
import vn.loto.rest01.metier.Marque;

import java.util.List;

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Marque", description = "Crud sur la table marque")
public class MarqueResource {
    @Context
    UriInfo uriInfo;
    @GET
    @Operation(summary = "Toutes marques", description = "Liste des marques")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des marques introuvable")
    public Response getAllMarques() {
        List<Marque> marques = DAOFactory.getMarqueDAO().getAll();
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        List<MarqueDTO> marqueDTOList = MarqueDTO.toDtoList(marques,uriBuilder);
        return Response.ok(marqueDTOList).status(200).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "marque par id", description = "Rechercher une marque par son identifiant")
    @ApiResponse(responseCode = "200", description = "Ok! marque trouvée")
    @ApiResponse(responseCode = "404", description = "marque introuvable")
    public Response getMarqueById(@PathParam("id") Integer id){
        Marque marque = DAOFactory.getMarqueDAO().getByID(id);
        if (marque != null) {
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("/brands");
            MarqueDTO marqueDTO = new MarqueDTO(marque, uriBuilder);
            return Response.ok(marqueDTO).status(200).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/fabricants")
    @Operation(summary = "Tous fabricant", description = "Liste des fabricants")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des fabricants introuvable")
    public Response getFabricantsForBrands(@PathParam("id") Integer fabricantId) {
        List<Marque> marques = DAOFactory.getFabricantDAO().getFabricantByBrand(fabricantId);
        if (!marques.isEmpty())
        return Response.ok(marques).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}
