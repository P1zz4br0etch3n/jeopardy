package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.model.User;
import org.dhbw.mosbach.ai.javae.jeopardy.bean.UserBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class RestUser {

    @Inject
    private UserBean userBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getAll() {
        final List<User> users = userBean.getAll();
        return users.toArray(new User[0]);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("name") String id) {
        return userBean.get(Long.parseLong(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(User user) {
        userBean.create(user);
    }
}
