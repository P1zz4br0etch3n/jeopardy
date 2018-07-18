package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.model.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;

@Path("/dummydata")
public class RestDummy {
    @Inject
    private PersistenceBean pb;

    @GET
    public void makeDummyData() {

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
    }

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
                q.setText("Question"+(++start));
                q.setAnswer("42");
                try {
                switch(b) {
                    case 0: q.setScore(100);  break;
                    case 1: q.setScore(300);  break;
                    case 2: q.setScore(500);  break;
                    case 3: q.setScore(800);  break;
                    case 4: q.setScore(1000); break;
                }}catch (Exception e){}

                questions.add(q);
            }
            c.setQuestions(questions);
            categories.add(c);
        }
        g.setCategories(categories);
        return g;
    }
}