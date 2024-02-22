package vn.loto.rest01;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Pays;

import java.util.List;

@Path("/country")
public class PayResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPays() {
        List<Pays> pays = DAOFactory.getPaysDAO().getAllWithContinent();
        return Response.ok(pays).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContinentById(@PathParam("id") Integer id) {
        Pays pays = DAOFactory.getPaysDAO().getByID(id);
        if (pays != null) {
            return Response.ok(pays).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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
