package vn.loto.rest01;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Fabricant;

import java.util.List;

@Path("/fabricant")
public class FabricantResource {
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllFabricant() {
            List<Fabricant> fabricants = DAOFactory.getFabricantDAO().getAll();
            return Response.ok(fabricants).build();
        }

        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getFabricantById(@PathParam("id") Integer id) {
            Fabricant fabricant = DAOFactory.getFabricantDAO().getByID(id);
            if (fabricant != null) {
                return Response.ok(fabricant).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
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

