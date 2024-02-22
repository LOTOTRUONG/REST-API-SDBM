package vn.loto.rest01;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.metier.Type;

import java.util.List;

@Path("/type")
public class TypeResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTypes(){
        List<Type> types = DAOFactory.getTypeDAO().getAll();
        return Response.ok(new GenericEntity<List<Type>>(types) {}).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTypeById(@PathParam("id") Integer id){
        Type type = DAOFactory.getTypeDAO().getByID(id);
        if (type != null){
            return Response.ok(type).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    //method to create new Type
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Consumes(MediaType.APPLICATION_JSON)
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
