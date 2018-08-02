package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.AuthenticationBean;
import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/games")
public class RestGame {

    @Inject
    private PersistenceBean pb;
    @Inject
    private AuthenticationBean ab;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            final List<Game> games = pb.getAllGames();
            return Response.ok(games, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Game game, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.saveGame(game);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Game game, @HeaderParam("X-Auth-Token") String authToken) {
        if (ab.authenticateUserByAuthToken(authToken) != null) {
            pb.deleteGame(game);
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

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
