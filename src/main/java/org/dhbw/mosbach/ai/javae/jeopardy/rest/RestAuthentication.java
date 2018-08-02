package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.AuthenticationBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Authentication REST Endpoints
 */
@Path("/auth")
public class RestAuthentication {

    @Inject
    private AuthenticationBean ab;

    /**
     * Endpoint for User Login via username and password. Generates Token for further authentication.
     * @param user User object containing username and password
     * @return Object containing the user id and Token. If credentials aren't valid 401 Unauthorized
     * is returned.
     */
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

    /**
     * Endpoint for user logout. The user is identified by the token
     * which is expected to be in the X-Auth-Token header and will
     * be logged out.
     * @param authToken Token of the user
     * @return 200 ok on success or 401 unauthorized on failure
     */
    @HEAD
    @Path("/logout")
    public Response logout(@HeaderParam("X-Auth-Token") String authToken) {
        if (authToken != null) {
            ab.invalidateAuthToken(authToken);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Endpoint for validating the Token and matching a user id and name.
     * @param user Object containing a user id, username and Token.
     * @return Object containing a boolean attribute named 'valid'
     */
    @POST
    @Path("/validateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateUser(ValidationUser user) {
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