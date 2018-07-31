package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.Category;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Question;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class PersistenceBean {

    @PersistenceContext
    private EntityManager em;

    /*
        User Methods
     */
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User getUser(long id) {
        return em.find(User.class, id);
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class);
        return query.setParameter("user", username).getSingleResult();
    }

    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }

    @Transactional
    public void updateUser(User user) {
        em.persist(em.merge(user));
    }

    @Transactional
    public void deleteUser(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }


    /*
        Game Methods
     */
    @Transactional
    public List<Game> getAllGames() {
        return em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
    }

    public Game getGame(long id) {
        return em.find(Game.class, id);
    }

    @Transactional
    public void saveGame(Game game) {
        for (Category cat: game.getCategories()) {
            for (Question q: cat.getQuestions()) {
                q.setId(0); // this is only for no further changes in frontend
                em.persist(q);
            }
            cat.setId(0); // this is only for no further changes in frontend
            em.persist(cat);
        }
        if (game.getCreatorUser() == null) {
            if (game.getCreator() > 0) {
                User user = em.find(User.class, game.getCreator());
                game.setCreatorUser(user);
            }
        } else {
            game.setCreator(game.getCreatorUser().getId());
        }
        em.persist(game);
    }

    @Transactional
    public void updateGame(Game game) {
        em.persist(em.merge(game));
    }

    @Transactional
    public void deleteGame(Game game) {
        em.remove(em.contains(game) ? game : em.merge(game));
    }

    public List<Game> getGamesOfCreator(long uid) {
        User user = em.find(User.class, uid);
        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.creatorUser = :user", Game.class);
        return query.setParameter("user", user).getResultList();
    }
}