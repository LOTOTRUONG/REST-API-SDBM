package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import vn.loto.rest01.dao.DAOFactory;
import vn.loto.rest01.dto.UserResponse;
import vn.loto.rest01.hateoas.HateOas;
import vn.loto.rest01.hateoas.Link;
import vn.loto.rest01.metier.User;
import vn.loto.rest01.security.HashPassword;
import vn.loto.rest01.security.MyToken;
import vn.loto.rest01.security.Tokened;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Path("/authorization")
@Tag(name = "Authorization")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IdentificationResource {
    @GET
    @Tokened
    @Operation(summary = "Toutes user", description = "Liste des users")
    @ApiResponse(responseCode = "200", description = "Ok!")
    @ApiResponse(responseCode = "404", description = "Liste des users introuvable")
    public Response getAllUsers() {
        List<User> users = DAOFactory.getUserDAO().getAll();
        return Response.ok(users).status(200).build();
    }

    @Context UriInfo uriInfo;
    @POST
    @Path("login")
    public Response login(User user, @Context ServletContext servletContext) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());

        if (user == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (isUserValid(user))
            return Response.ok().header(HttpHeaders.AUTHORIZATION, MyToken.generate(user)).build();


        URI forgotPasswordURI = uriBuilder.clone().path(user.getLogin()).path("/forgotPassword").build();
        URI forgotLoginURI = uriBuilder.clone().path("/forgotLogin").queryParam("email", "yourEmail").build();

        HateOas hateOas = new HateOas();
        hateOas.addLink(new Link("Forgot Password", HttpMethod.GET, forgotPasswordURI));
        hateOas.addLink(new Link("Forgot Login", HttpMethod.GET, forgotLoginURI));

        return Response.ok(hateOas).status(Response.Status.UNAUTHORIZED).build();

    }
    private boolean isUserValid(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User storedUser = DAOFactory.getUserDAO().getByUsername(user);
        if (storedUser == null || storedUser.getPassword() == null)
            return false;
        String rawPassword = user.getPassword();
        String hashedPassword = storedUser.getPassword();

        return HashPassword.validate(rawPassword,hashedPassword);
    }

    @POST
    @Path("register")
    public Response register(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (!validSubmittedUser(user))
            return Response.status(Response.Status.BAD_REQUEST).build();

        String rawPassword = user.getPassword();
        user.setPassword(HashPassword.generate(rawPassword));
        if (DAOFactory.getUserDAO().insert(user))
            return Response.ok(new UserResponse(user.getLogin(), user.getEmail())).header(HttpHeaders.AUTHORIZATION, MyToken.generate(user)).build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    }
    private boolean validSubmittedUser(User user){
        if (user == null)
            return false;

        boolean firstCase = user.getLogin() != null && user.getLogin().isEmpty();
        boolean secondCase = user.getPassword() != null && user.getPassword().isEmpty();
        boolean thirdCase = user.getEmail() != null && user.getEmail().isEmpty();

        return firstCase && secondCase && thirdCase;
    }
}
