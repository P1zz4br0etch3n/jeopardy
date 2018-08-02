package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.Category;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;
import org.dhbw.mosbach.ai.javae.jeopardy.model.Question;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Bean for managing entity persistence
 */
@Named
@ApplicationScoped
public class PersistenceBean {

    @PersistenceContext
    private EntityManager em;

    // ---------------------------------------------------------------------------
    // User Methods
    // ---------------------------------------------------------------------------

    /**
     * Get all User entities from database
     * @return List of Users
     */
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * Get single User entity by id
     * @param id User id
     * @return User if found or null if not
     */
    public User getUser(long id) {
        return em.find(User.class, id);
    }

    /**
     * Get User entity by username
     * @param username Username to look for
     * @return User entity on success
     * @throws NoResultException if there is no result
     */
    public User getUserByUsername(String username) throws NoResultException {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class);
        return query.setParameter("user", username).getSingleResult();
    }

    /**
     * Saves a newly created user (id will be generated, just keep it empty)
     * @param user User entity instance
     */
    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }

    /**
     * Update an existent User
     * @param user User entity instance with new values
     */
    @Transactional
    public void updateUser(User user) {
        em.persist(em.merge(user));
    }

    /**
     * Delete User from database
     * @param user User entity instance containing at least an id
     */
    @Transactional
    public void deleteUser(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }


    // ---------------------------------------------------------------------------
    // Game Methods
    // ---------------------------------------------------------------------------

    /**
     * Get all Game entities from database
     * @return List of Games
     */
    @Transactional
    public List<Game> getAllGames() {
        return em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
    }

    /**
     * Get single Game entity by id
     * @param id Game id
     * @return Game if found or null if not
     */
    public Game getGame(long id) {
        return em.find(Game.class, id);
    }

    /**
     * Saves a newly created game (ids will be generated, just keep them empty).
     * creatorUser must be a User which already exists or creator an id of an
     * existing user.
     * @param game Game entity instance
     */
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
        em.persist(game);
    }

    /**
     * Update an existent Game
     * @param game Game entity instance with new values
     */
    @Transactional
    public void updateGame(Game game) {
        em.persist(em.merge(game));
    }

    /**
     * Delete Game from database
     * @param game Game entity instance containing at least an id
     */
    @Transactional
    public void deleteGame(Game game) {
        em.remove(em.contains(game) ? game : em.merge(game));
    }

    /**
     * Get all games created by an User. Fetched by User id.
     * @param uid Valid User id
     * @return List of Games
     */
    public List<Game> getGamesOfCreator(long uid) {
        User user = em.find(User.class, uid);
        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.creator = :user", Game.class);
        return query.setParameter("user", user).getResultList();
    }
}