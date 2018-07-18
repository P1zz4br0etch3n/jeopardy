package org.dhbw.mosbach.ai.javae.jeopardy.rest;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;
import org.dhbw.mosbach.ai.javae.jeopardy.exception.ScoreNotValidException;
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
        dummyuser.setId(1);
        dummyuser.setUsername("DummyUser");
        dummyuser.setPassword("geheim");
        //dummyuser.setEmail("dummy@email.tld");
        pb.saveUser(dummyuser);

        User dummyuserAlt = new User();
        dummyuserAlt.setId(2);
        dummyuserAlt.setUsername("DummyUserAlt");
        dummyuserAlt.setPassword("geheim");
        //dummyuserAlt.setEmail("dummyAlt@email.tld");
        pb.saveUser(dummyuserAlt);

        Game dummygame1 = genGame(10, dummyuser);
        Game dummygame2 = genGame(100, dummyuser);
        Game dummygame3 = genGame(1000, dummyuserAlt);

        pb.saveGame(dummygame1);
        pb.saveGame(dummygame2);
        pb.saveGame(dummygame3);

            /*try{
            Question q1 = new Question();
            q1.setId(10);
            q1.setText("Question1");
            q1.setScore(100);
            q1.setAnswer("42");

            Question q2 = new Question();
            q1.setId(20);
            q1.setText("Question2");
            q1.setScore(300);
            q1.setAnswer("42");

            Question q3 = new Question();
            q1.setId(30);
            q1.setText("Question3");
            q1.setScore(500);
            q1.setAnswer("42");

            Question q4 = new Question();
            q1.setId(40);
            q1.setText("Question4");
            q1.setScore(800);
            q1.setAnswer("42");

            Question q5 = new Question();
            q1.setId(50);
            q1.setText("Question5");
            q1.setScore(1000);
            q1.setAnswer("42");

            Category c1 = new Category();
            c1.setId(100);
            c1.setName("CategoryDummy1");
            c1.setQuestions(new ArrayList<Question>(){{add(q1);add(q2);add(q3);add(q4);add(q5);}});

            Question q11 = new Question();
            q1.setId(11);
            q1.setText("Question11");
            q1.setScore(100);
            q1.setAnswer("42");

            Question q21 = new Question();
            q1.setId(21);
            q1.setText("Question21");
            q1.setScore(300);
            q1.setAnswer("42");

            Question q31 = new Question();
            q1.setId(31);
            q1.setText("Question31");
            q1.setScore(500);
            q1.setAnswer("42");

            Question q41 = new Question();
            q1.setId(41);
            q1.setText("Question41");
            q1.setScore(800);
            q1.setAnswer("42");

            Question q51 = new Question();
            q1.setId(51);
            q1.setText("Question51");
            q1.setScore(1000);
            q1.setAnswer("42");

            Category c11 = new Category();
            c1.setId(1001);
            c1.setName("CategoryDummy2");
            c1.setQuestions(new ArrayList<Question>(){{add(q11);add(q21);add(q31);add(q41);add(q51);}});

            Question q12 = new Question();
            q1.setId(12);
            q1.setText("Question12");
            q1.setScore(100);
            q1.setAnswer("42");

            Question q22 = new Question();
            q1.setId(22);
            q1.setText("Question22");
            q1.setScore(300);
            q1.setAnswer("42");

            Question q32 = new Question();
            q1.setId(32);
            q1.setText("Question32");
            q1.setScore(500);
            q1.setAnswer("42");

            Question q42 = new Question();
            q1.setId(42);
            q1.setText("Question42");
            q1.setScore(800);
            q1.setAnswer("42");

            Question q52 = new Question();
            q1.setId(52);
            q1.setText("Question52");
            q1.setScore(1000);
            q1.setAnswer("42");

            Category c12 = new Category();
            c1.setId(1002);
            c1.setName("CategoryDummy3");
            c1.setQuestions(new ArrayList<Question>(){{add(q12);add(q22);add(q32);add(q42);add(q52);}});

            Question q13 = new Question();
            q1.setId(13);
            q1.setText("Question13");
            q1.setScore(100);
            q1.setAnswer("42");

            Question q23 = new Question();
            q1.setId(23);
            q1.setText("Question23");
            q1.setScore(300);
            q1.setAnswer("42");

            Question q33 = new Question();
            q1.setId(33);
            q1.setText("Question33");
            q1.setScore(500);
            q1.setAnswer("42");

            Question q43 = new Question();
            q1.setId(43);
            q1.setText("Question43");
            q1.setScore(800);
            q1.setAnswer("42");

            Question q53 = new Question();
            q1.setId(53);
            q1.setText("Question53");
            q1.setScore(1000);
            q1.setAnswer("42");

            Category c13 = new Category();
            c1.setId(1003);
            c1.setName("CategoryDummy4");
            c1.setQuestions(new ArrayList<Question>(){{add(q13);add(q23);add(q33);add(q43);add(q53);}});

            Question q14 = new Question();
            q1.setId(14);
            q1.setText("Question14");
            q1.setScore(100);
            q1.setAnswer("42");

            Question q24 = new Question();
            q1.setId(24);
            q1.setText("Question24");
            q1.setScore(300);
            q1.setAnswer("42");

            Question q34 = new Question();
            q1.setId(34);
            q1.setText("Question34");
            q1.setScore(500);
            q1.setAnswer("42");

            Question q44 = new Question();
            q1.setId(44);
            q1.setText("Question44");
            q1.setScore(800);
            q1.setAnswer("42");

            Question q54 = new Question();
            q1.setId(54);
            q1.setText("Question54");
            q1.setScore(1000);
            q1.setAnswer("42");

            Category c14 = new Category();
            c1.setId(1004);
            c1.setName("CategoryDummy14");
            c1.setQuestions(new ArrayList<Question>(){{add(q14);add(q24);add(q34);add(q44);add(q54);}});

            Game dummygame = new Game();
            dummygame.setId(2);
            dummygame.setName("GameDummy");
            dummygame.setCreator(dummyuser);
            dummygame.setCategories(new ArrayList<Category>(){{add(c1);add(c11);add(c12);add(c13);add(c14);}});
            pb.saveGame(dummygame);
        }
        catch (ScoreNotValidException s) {}//*/
    }

    private Game genGame(int start, User creator){
        Game g = new Game();
        g.setId(++start);
        g.setName("Game"+start);
        g.setCreator(creator);

        ArrayList<Category> categories = new ArrayList<>();
        for(int a = 0; a < 5; a++){
            Category c = new Category();
            c.setId(++start);
            c.setName("Category"+start);

            ArrayList<Question> questions = new ArrayList<>();
            for (int b = 0; b < 5; b++){
                Question q = new Question();
                q.setId(++start);
                q.setText("Question"+start);
                q.setAnswer("42");

                try {
                switch(b) {
                    case 0: q.setScore(100);  break;
                    case 1: q.setScore(300);  break;
                    case 2: q.setScore(500);  break;
                    case 3: q.setScore(800);  break;
                    case 4: q.setScore(1000); break;
                }
                }catch (Exception e){}

                questions.add(q);
            }

            c.setQuestions(questions);

            categories.add(c);
        }
        g.setCategories(categories);

        return g;
    }
}