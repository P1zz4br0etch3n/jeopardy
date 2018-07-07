package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;
import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class RestUser {

    @Inject
    private PersistenceBean pb;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getAll() {
        final List<User> users = pb.getAllUsers();
        return users.toArray(new User[0]);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("name") String id) {
        return pb.getUser(Long.parseLong(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(User user) {
        pb.saveUser(user);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(User user) {
        pb.deleteUser(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(User user) {
        pb.saveUser(user);
    }

    @GET
    @Path("/{uid}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Game[] getGamesOfUser(@PathParam("uid") String uid) {
        final List<Game> games = pb.getGamesOfCreator(uid);
        return games.toArray(new Game[0]);
    }
}
