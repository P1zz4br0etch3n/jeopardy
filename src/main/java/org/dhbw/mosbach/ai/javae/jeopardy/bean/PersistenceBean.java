package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

    /*
        Authentication
     */
    private SecureRandom rnd = new SecureRandom();
    private ConcurrentHashMap<String, User> TokenToUser = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Date> TokenToDate = new ConcurrentHashMap<>();

    public User authenticateUserByUsernameAndPassword(User user) {
        try {
            User u = getUserByUsername(user.getUsername());
            if (u.getPasswordHash().equals(user.getPasswordHash())) {
                return u;
            }
        } catch (Exception ignored) {}
        return null;
    }

    public User authenticateUserByAuthToken(String authToken) {
        if (TokenToUser.containsKey(authToken)) {
            if (!hasTokenExpired(authToken)){
                return TokenToUser.get(authToken);
            }
        }
        return null;
    }

    //WARNING: Automatically refreshes the Token if it is not expired.
    private Boolean hasTokenExpired(String authToken) {
        int expirationSeconds = 300;
        if (TokenToDate.containsKey(authToken)){
            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TokenToDate.get(authToken).getTime());
            if(timeDiff > expirationSeconds) {
                return true;
            }
            RefreshToken(authToken);
            return false;
        }
        return true;
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
        TokenToDate.put(authToken, new Date());
    }

    private void InvalidateAuthToken(String authToken) {
        if (!TokenToUser.containsKey(authToken)) {
            System.out.println("Invalidating not existing token.");
        } else {
            TokenToUser.remove(authToken);
            TokenToDate.remove(authToken);
        }
    }
}