package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.Game;
import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

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
    private SecureRandom rnd = new SecureRandom();
    private HashMap<String, User> TokenToUser = new HashMap<>();

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

    public User authenticateUserByUsernameAndPassword(String username, String password){
        List<User> allUsers = getAllUsers();
        for (User user : allUsers){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User authenticateUserByAuthToken(String authToken){
        if (TokenToUser.containsKey(authToken)){
            return TokenToUser.get(authToken);
        }
        return null;
    }

    public String generateUserAuthToken(User user){
        byte authTokenBytes[] = new byte[32];
        rnd.nextBytes(authTokenBytes);
        SetAuthTokenOfUser(user, Arrays.toString(authTokenBytes));
        return Arrays.toString(authTokenBytes);
    }

    private void SetAuthTokenOfUser(User user, String authToken){
        if (TokenToUser.containsKey(authToken) || TokenToUser.containsValue(user)){
            return;
        }
        TokenToUser.put(authToken, user);
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