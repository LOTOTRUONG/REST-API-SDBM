package vn.loto.rest01;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Couleur;

import java.util.List;

@Path("/color")
public class CouleurResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllColors() {
        List<Couleur> colors = DAOFactory.getCouleurDAO().getAll();
        return Response.ok(colors).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorById(@PathParam("id") Integer id) {
        Couleur color = DAOFactory.getCouleurDAO().getByID(id);
        if (color != null) {
            return Response.ok(color).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, Couleur couleur) {
        if (couleur == null || id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // Retrieve the color to be updated
        Couleur existingColor = DAOFactory.getCouleurDAO().getByID(id);
        if (existingColor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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
        if (DAOFactory.getCouleurDAO().update(couleur)) {
            return Response.ok(couleur).status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
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