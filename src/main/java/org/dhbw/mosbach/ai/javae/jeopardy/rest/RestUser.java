package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.AuthenticationBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;
import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User REST Endpoints
 */
@Path("/users")
public class RestUser {

    @Inject
    private PersistenceBean pb;
    @Inject
    private AuthenticationBean ab;

    /**
     * Get all users from database
     * @param authToken user auth token
     * @return List of users on success or 401 if token is not valid
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            final List<User> users = pb.getAllUsers();
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Get single user by id
     * @param id user id
     * @param authToken user auth token
     * @return User object on success or 404 if no user is found or 401 if token is not valid
     */
    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String id, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            User user = pb.getUser(Long.parseLong(id));
            if (user != null) {
                return Response.ok(user, MediaType.APPLICATION_JSON).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Register new user.
     * @param user user object without id
     * @return 200 on success
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(User user) {
        // no auth for user registration
        pb.saveUser(user);
        return Response.ok().build();
    }

    /**
     * delet user
     * @param user user object containing id
     * @param authToken user auth token
     * @return 200 ok on success or 401 if token is not valid
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(User user, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.deleteUser(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Update user with new values
     * @param user user object with new values
     * @param authToken user auth token
     * @return 200 on success or 401 if token is not valid
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(User user, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.updateUser(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Get all games where the user with uid is creator
     * @param uid creator user id
     * @param authToken user auth token
     * @return List of games on success or 401 if token is not valid
     */
    @GET
    @Path("/{uid}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGamesOfUser(@PathParam("uid") String uid, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            final List<Game> games = pb.getGamesOfCreator(Long.parseLong(uid));
            return Response.ok(games, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
