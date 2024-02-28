package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.dto.CouleurResponse;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.hateoas.Link;
import vn.loto.rest01.metier.Couleur;
import vn.loto.rest01.security.Tokened;

import java.net.URI;
import java.util.List;

@Path("/color")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Couleur", description = "Crud sur la table couleur")

public class CouleurResource {
    @Context
    UriInfo uriInfo;

    @GET
    @Operation(summary = "Toutes couleurs", description = "Liste des couleurs")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des couleurs introuvable")
    public Response getAllColors() {
        List<Couleur> colors = DAOFactory.getCouleurDAO().getAll();
        return Response.ok(colors).status(200).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Couleur par id", description = "Rechercher une couleur par son identifiant")
    @ApiResponse(responseCode = "200", description = "Ok! Couleur trouvée")
    @ApiResponse(responseCode = "404", description = "Couleur introuvable")
    public Response getColorById(@PathParam("id") Integer id) {
        Couleur color = DAOFactory.getCouleurDAO().getByID(id);

        if (color != null) {
            //link to access different path
            UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
            URI allColorURI = uriBuilder.clone().replacePath("api/color").build();
            URI specificColorURI = uriBuilder.clone().build();
            HateOas hateOas = new HateOas();
            hateOas.addLink(new Link("Toutes Couleurs", HttpMethod.GET, allColorURI));
            hateOas.addLink(new Link("Couleur", HttpMethod.GET, specificColorURI));

            CouleurResponse couleurResponse = new CouleurResponse(color, hateOas);
            return Response.ok(couleurResponse).build(); }
         else {
            return Response.status(Response.Status.NOT_FOUND).build(); }

    }

    @POST
    @Operation(summary = "Insérer", description = "Insérer une couleur par nom")
    @ApiResponse(responseCode = "201", description = "La ressource a été crée")
    @ApiResponse(responseCode = "400", description = "couleur nulle")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response insert(Couleur couleur) {
        if (couleur == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (DAOFactory.getCouleurDAO().insert(couleur)) {
            return Response.ok().status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modifier", description = "Mettre à jour une couleur")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "couleur nulle")
    @ApiResponse(responseCode = "404", description = "Couleur introuvable")
    @ApiResponse(responseCode = "409", description = "Le nom de la couleur est dupliqué")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response update(@PathParam("id") Integer id, Couleur couleur) {

        if (couleur == null || id == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        // Retrieve the color to be updated
        Couleur existingColor = DAOFactory.getCouleurDAO().getByID(id);
        if (existingColor == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        // Check if the name is being modified to a duplicate value
        if (!existingColor.getNomCouleur().equalsIgnoreCase(couleur.getNomCouleur())) {
            List<Couleur> existingColorsWithSameName = DAOFactory.getCouleurDAO().getLike(couleur);
            for (Couleur existing : existingColorsWithSameName) {
                if (existing.getId() != id && existing.getNomCouleur().equalsIgnoreCase(couleur.getNomCouleur())) {
                    return Response.status(Response.Status.CONFLICT).entity("Name Color already exists").build();
                }
            }
        }
        // Proceed with the update operation
        couleur.setId(id);
        if (DAOFactory.getCouleurDAO().update(couleur))
            return Response.ok(couleur).status(Response.Status.OK).build();
         else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    }

    @Tokened
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer", description = "Supprimer une couleur")
    @ApiResponse(responseCode = "200", description = "OK!")
    @ApiResponse(responseCode = "400", description = "couleur nulle")
    @ApiResponse(responseCode = "500", description = "Le serveur a rencontré un problème")
    public Response delete(@PathParam("id") Integer id) {
        if (id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Couleur couleur = new Couleur(id, ""); // Create a dummy object with ID for deletion
        if (DAOFactory.getCouleurDAO().delete(couleur)) {
            return Response.ok().status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}