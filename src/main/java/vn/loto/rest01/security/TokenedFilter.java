package vn.loto.rest01.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.apache.http.HttpHeaders;

import java.io.IOException;

@Provider
@Tokened
@Priority(Priorities.AUTHENTICATION)
public class TokenedFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException{
        String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!MyToken.validate(token))
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
