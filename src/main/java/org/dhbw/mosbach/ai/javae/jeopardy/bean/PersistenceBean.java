package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;
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

    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }

    @Transactional
    public void deleteUser(User user) {
        em.remove(user);
    }


    /*
        Game Methods
     */

    public List<Game> getAllGames() {
        return em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
    }

    public Game getGame(long id) {
        return em.find(Game.class, id);
    }

    @Transactional
    public void saveGame(Game game) {
        em.persist(game);
    }

    @Transactional
    public void deleteGame(Game game) {
        em.remove(game);
    }

    public List<Game> getGamesOfCreator(String uid) {
        TypedQuery<Game> query =  em.createQuery("SELECT g FROM Game g WHERE g.creator = :uid", Game.class);
        return query.setParameter("uid", uid).getResultList();
    }
}