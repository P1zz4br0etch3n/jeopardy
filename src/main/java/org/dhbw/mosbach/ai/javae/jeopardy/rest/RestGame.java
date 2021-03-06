package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.AuthenticationBean;
import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Game REST Endpoints
 */
@Path("/games")
public class RestGame {

    @Inject
    private PersistenceBean pb;
    @Inject
    private AuthenticationBean ab;

    /**
     * Get all games form database
     * @param authToken Valid user auth Token
     * @return List of Games. If token not valid 401 unauthorized
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            final List<Game> games = pb.getAllGames();
            return Response.ok(games, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Get single game by id
     * @param id valid game id
     * @param authToken valid user auth token
     * @return game if found or 404 if not or 401 if token is not valid
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            Game game = pb.getGame(Long.parseLong(id));
            if (game != null) {
                return Response.ok(game, MediaType.APPLICATION_JSON).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Create new game
     * @param game Game object without ids
     * @param authToken user auth token
     * @return 200 ok on success or 401 unauthorized if token is not valid
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Game game, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.saveGame(game);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Delete game by id
     * @param game Game object containing id
     * @param authToken user auth token
     * @return 200 ok on success or 401 unauthorized if token is not valid
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Game game, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.deleteGame(game);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Updates game with new values
     * @param game Game object with new values
     * @param authToken user auth token
     * @return 200 ok on success or 401 oif token is not valid
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Game game, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.updateGame(game);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
