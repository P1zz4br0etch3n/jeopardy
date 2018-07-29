package org.dhbw.mosbach.ai.javae.jeopardy.rest;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Game[] getAll() {
        final List<Game> games = pb.getAllGames();
        return games.toArray(new Game[0]);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id, @HeaderParam("AuthToken") String authToken) {
        if (pb.authenticateUserByAuthToken(authToken) != null) {
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
    public void post(Game game) {
        pb.saveGame(game);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(Game game) {
        pb.deleteGame(game);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Game game) {
        pb.updateGame(game);
    }
}
