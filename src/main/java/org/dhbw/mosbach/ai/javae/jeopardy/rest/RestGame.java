package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Game get(@PathParam("id") String id) {
        return pb.getGame(Long.parseLong(id));
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
