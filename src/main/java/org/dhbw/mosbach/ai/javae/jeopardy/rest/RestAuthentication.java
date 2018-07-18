package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class RestAuthentication {

    @Inject
    private PersistenceBean pb;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(User user) {
        if(pb.authenticateUserByUsernameAndPassword(user)) {
            String authToken = pb.generateUserAuthToken(user.getUsername());
            if (authToken.equals(""))
                return Response.status(Response.Status.UNAUTHORIZED).build();
            return Response.ok("{\"authToken\": \"" + authToken + "\"}", MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
