package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.AuthenticationBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RestAuthentication {

    @Inject
    private AuthenticationBean ab;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        User authenticatedUser = ab.authenticateUserByUsernameAndPassword(user);
        if (authenticatedUser != null) {
            String authToken = ab.generateUserAuthToken(authenticatedUser.getUsername());
            if (authToken.equals("")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String responseObject = "{" +
                    "\"authToken\": \"" + authToken + "\", " +
                    "\"userId\": \"" + authenticatedUser.getId() + "\"" +
                    "}";
            return Response.ok(responseObject, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/validateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateToken(ValidationUser user) {
        User authenticatedUser = ab.authenticateUserByAuthToken(user.getAuthToken());
        String responseObject = "{" +
                "\"valid\": " + (
                        authenticatedUser != null &&
                                authenticatedUser.getId() == user.getId() &&
                                authenticatedUser.getUsername().equals(user.getUsername())) +
                "}";
        return Response.ok(responseObject, MediaType.APPLICATION_JSON).build();
    }
}