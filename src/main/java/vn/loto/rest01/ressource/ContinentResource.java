package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.metier.Continent;
import vn.loto.rest01.metier.Pays;
import vn.loto.rest01.security.Tokened;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/continents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Continent", description = "Crud sur la table continent")
public class ContinentResource {
    @Context
    UriInfo uriInfo;
    @GET
    @Operation(summary = "Tous continents", description = "Liste des continents")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des continents introuvable")
    public Response getAllContinent() {
        List<Continent> continents = DAOFactory.getContinentDAO().getAll();
        return Response.ok(continents).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "continent par id", description = "Rechercher un continent par son identifiant")
    @ApiResponse(responseCode = "200", description = "Ok! continent trouvé")
    @ApiResponse(responseCode = "404", description = "continent introuvable")
    public Response getContinentById(@PathParam("id") Integer id) {
        Continent continent = DAOFactory.getContinentDAO().getByID(id);
        if (continent != null) {
            UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
            URI allContinentURI = uriBuilder.clone().replacePath("api/continent").build();
            URI specificContinentURI = uriBuilder.clone().build();

            Map<String, Object> response = new HashMap<>();
            response.put("continent", continent);

            HateOas hateOas = new HateOas();
            hateOas.addLink("Toutes Continents", HttpMethod.GET, allContinentURI);
            hateOas.addLink("Continent ID " + id, HttpMethod.GET, specificContinentURI);

            response.put("links", hateOas.getLinks());

            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/countries")
    @Operation(summary = "Pays par continent", description = "Liste des pays par continent")
    @ApiResponse(responseCode = "200", description = "OK! Liste des pays trouvés")
    @ApiResponse(responseCode = "404", description = "Pays introuvables pour ce continent")
    public Response getCountryByContinent(@PathParam("id") Integer continentId) {
        List<Pays> countries = DAOFactory.getContinentDAO().getPaysByContinent(continentId);
        if (!countries.isEmpty()) {
            return Response.ok(countries).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Operation(summary = "Insérer", description = "Insérer un continent par nom")
    @ApiResponse(responseCode = "201", description = "La ressource a été crée")
    @ApiResponse(responseCode = "400", description = "continent null")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")    public Response insert(Continent continent){
        if (continent == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (DAOFactory.getContinentDAO().insert(continent)){
            return Response.ok().status(Response.Status.CREATED).build();
        }else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modifier", description = "Mettre à jour un continent")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "continent null")
    @ApiResponse(responseCode = "404", description = "continent introuvable")
    @ApiResponse(responseCode = "409", description = "Le nom du continent est dupliqué")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response update(@PathParam("id") Integer id, Continent continent){
        if (id == null || continent == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (id != continent.getId()){
            return Response.status(Response.Status.CONFLICT).entity(continent).build();
        }
        if (DAOFactory.getContinentDAO().update(continent)){
            return Response.ok(continent).status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DELETE
    @Path("/{id}")
    @Tokened
    @Operation(summary = "Supprimer", description = "Supprimer un continent")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "continent null")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")

    public Response delete(@PathParam("id") Integer id) {
        if (id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Continent continent = new Continent(id, ""); // Create a dummy object with ID for deletion
        if (DAOFactory.getContinentDAO().delete(continent)) {
            return Response.ok().status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
