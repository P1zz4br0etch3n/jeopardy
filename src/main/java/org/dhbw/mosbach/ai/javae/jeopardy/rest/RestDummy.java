package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.DummyBean;
import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * DummyData REST Endpoints
 */
@Path("/dummydata")
public class RestDummy {
    @Inject
    private PersistenceBean pb;
    @Inject
    private DummyBean db;

    /**
     * Endpoint to generate dummydata for testing
     * @return 204 no content on success or 409 conflict
     * if data was already created.
     */
    @POST
    public Response makeDummyData() {

        if (!db.isDummyDataCreated()) {
            User dummyuser = new User();
            dummyuser.setUsername("DummyUser");
            dummyuser.setPassword("geheim");
            pb.saveUser(dummyuser);

            User dummyuserAlt = new User();
            dummyuserAlt.setUsername("DummyUserAlt");
            dummyuserAlt.setPassword("geheim");
            pb.saveUser(dummyuserAlt);

            Game dummygame1 = genGame(10, dummyuser);
            Game dummygame2 = genGame(100, dummyuser);
            Game dummygame3 = genGame(1000, dummyuserAlt);

            pb.saveGame(dummygame1);
            pb.saveGame(dummygame2);
            pb.saveGame(dummygame3);

            db.setDummyDataCreated(true);

            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    /**
     * Endpoint to check if dummydata was created
     * @return "true" or "false" as plain text
     */
    @GET
    public Response isDummyDataCreated() {
        return Response.ok(db.isDummyDataCreated(), MediaType.TEXT_PLAIN).build();
    }

    /**
     * Generate dummy game
     * @param start integer to start from for value generation
     * @param creator creator to set for the game
     * @return Dummy Game entity instance
     */
    private Game genGame(int start, User creator){
        Game g = new Game();
        //g.setId(++start);
        g.setName("Game"+(++start));
        g.setCreator(creator);

        ArrayList<Category> categories = new ArrayList<>();
        for(int a = 0; a < 5; a++){
            Category c = new Category();
            //c.setId(++start);
            c.setName("Category"+(++start));

            ArrayList<Question> questions = new ArrayList<>();
            for (int b = 0; b < 5; b++){
                Question q = new Question();
                //q.setId(++start);
                q.setName("Question"+(++start));
                q.setAnswer("42");
                try {
                switch(b) {
                    case 0: q.setPoints(100);  break;
                    case 1: q.setPoints(200);  break;
                    case 2: q.setPoints(300);  break;
                    case 3: q.setPoints(400);  break;
                    case 4: q.setPoints(500); break;
                }}catch (Exception ignored){}

                questions.add(q);
            }
            c.setQuestions(questions);
            categories.add(c);
        }
        g.setCategories(categories);
        return g;
    }
}