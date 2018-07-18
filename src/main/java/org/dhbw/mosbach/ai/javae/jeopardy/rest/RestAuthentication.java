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
    public Response post(String username, String password) {
        if(pb.authenticateUserByUsernameAndPassword(username, password)) {
            String authToken = pb.generateUserAuthToken(username);
            if (authToken.equals(""))
                return Response.status(Response.Status.FORBIDDEN).build();
            return Response.ok(authToken).build();
        }
        return Response.serverError().build();
    }
}
