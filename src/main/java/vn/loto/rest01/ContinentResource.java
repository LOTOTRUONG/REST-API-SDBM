package vn.loto.rest01;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Continent;

import java.util.List;

@Path("/continent")
public class ContinentResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContinent() {
        List<Continent> continents = DAOFactory.getContinentDAO().getAll();
        return Response.ok(continents).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContinentById(@PathParam("id") Integer id) {
        Continent continent = DAOFactory.getContinentDAO().getByID(id);
        if (continent != null) {
            return Response.ok(continent).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Continent continent){
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
