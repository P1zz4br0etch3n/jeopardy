package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
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
                em.persist(q);
            }
            em.persist(cat);
        }
        em.persist(game);
    }

    @Transactional
    public void deleteGame(Game game) {
        em.remove(em.contains(game) ? game : em.merge(game));
    }

    public List<Game> getGamesOfCreator(String uid) {
        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.creator = :uid", Game.class);
        return query.setParameter("uid", uid).getResultList();
    }

    /*
        Authentication
     */
    private SecureRandom rnd = new SecureRandom();
    private HashMap<String, User> TokenToUser = new HashMap<>();

    public Boolean authenticateUserByUsernameAndPassword(String username, String password) {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPasswordHash().equals(HashHelper.Hash(password))) {
                return true;
            }
        }
        return false;
    }

    public User authenticateUserByAuthToken(String authToken) {
        if (TokenToUser.containsKey(authToken)) {
            return TokenToUser.get(authToken);
        }
        return null;
    }

    public String generateUserAuthToken(String username) {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)){
                byte authTokenBytes[] = new byte[32];
                rnd.nextBytes(authTokenBytes);
                SetAuthTokenOfUser(user, Arrays.toString(authTokenBytes));
                return Arrays.toString(authTokenBytes);
            }
        }
        return "";
    }

    private void SetAuthTokenOfUser(User user, String authToken) {
        if (TokenToUser.containsKey(authToken) || TokenToUser.containsValue(user)) {
            System.out.println("User already registered.");
            InvalidateAuthToken(authToken);
            authToken = generateUserAuthToken(user.getUsername());
            System.out.println("New registration generated.");
        }
        TokenToUser.put(authToken, user);
    }

    private void InvalidateAuthToken(String authToken) {
        if (!TokenToUser.containsKey(authToken)) {
            System.out.println("Invalidating not existing token.");
            return;
        } else {
            TokenToUser.remove(authToken);
        }
    }
}