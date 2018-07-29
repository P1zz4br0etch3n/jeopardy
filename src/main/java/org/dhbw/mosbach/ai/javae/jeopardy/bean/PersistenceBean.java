package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
                em.persist(q);
            }
            em.persist(cat);
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
        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.creator = :user", Game.class);
        return query.setParameter("user", user).getResultList();
    }

    /*
        Authentication
     */
    private SecureRandom rnd = new SecureRandom();
    private HashMap<String, User> TokenToUser = new HashMap<>();
    private HashMap<String, Date> TokenToDate = new HashMap<>();

    public Boolean authenticateUserByUsernameAndPassword(User user) {
        List<User> allUsers = getAllUsers();
        for (User u : allUsers) {
            if (u.getUsername().equals(user.getUsername()) && u.getPasswordHash().equals(user.getPasswordHash())) {
                return true;
            }
        }
        return false;
    }

    public User authenticateUserByAuthToken(String authToken) {
        if (TokenToUser.containsKey(authToken)) {
            if (!HasTokenExpired(authToken)){
                return TokenToUser.get(authToken);
            }
        }
        return null;
    }

    //WARNING: Automatically refreshes the Token if it is not expired.
    public Boolean HasTokenExpired(String authToken){
        int expirationSeconds = 30;
        if (TokenToDate.containsKey(authToken)){
            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TokenToDate.get(authToken).getTime());
            if(timeDiff > expirationSeconds){
                return true;
            }
            RefreshToken(authToken);
            return false;
        }
        throw new NullPointerException("Token not found.");
    }

    private void RefreshToken(String authToken){
        if(TokenToDate.containsKey(authToken)){
            TokenToDate.get(authToken).setTime(System.currentTimeMillis());
        }
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
            TokenToDate.remove(authToken);
        }
    }
}