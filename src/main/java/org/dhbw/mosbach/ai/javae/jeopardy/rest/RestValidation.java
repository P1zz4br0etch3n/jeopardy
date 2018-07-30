package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/validateToken")
public class RestValidation{
    @Inject
    private PersistenceBean pb;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String authToken) {
        String responseObject = "{" +
                "\"valid\": \"" + (pb.authenticateUserByAuthToken(authToken) != null) +
                "}";
        return Response.ok(responseObject, MediaType.APPLICATION_JSON).build();
    }
}
