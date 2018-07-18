package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/auth")
public class RestAuthentication {

    @Inject
    private PersistenceBean pb;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(String username, String password) {
        pb.authenticateUserByUsernameAndPassword(username, password);
    }
}
