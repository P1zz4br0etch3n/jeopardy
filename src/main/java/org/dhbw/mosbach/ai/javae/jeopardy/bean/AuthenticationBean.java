package org.dhbw.mosbach.ai.javae.jeopardy.bean;

import org.dhbw.mosbach.ai.javae.jeopardy.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Bean for Authentication tasks
 */
@Named
@ApplicationScoped
public class AuthenticationBean {

    @Inject
    private PersistenceBean pb;

    private SecureRandom rnd = new SecureRandom();
    private ConcurrentHashMap<String, User> TokenToUser = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Date> TokenToDate = new ConcurrentHashMap<>();

    /**
     * Check if a pair of username and password is correct.
     * @param user Any instance of an user entity which contains a username and password
     * @return Managed user entity on success and null on failure
     */
    public User authenticateUserByUsernameAndPassword(User user) {
        try {
            User u = pb.getUserByUsername(user.getUsername());
            if (u.getPasswordHash().equals(user.getPasswordHash())) {
                return u;
            }
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * Check if token is valid.
     * @param authToken Generated authToken from method generateUserAuthToken()
     * @return Authenticated User on success or null on failure
     */
    public User authenticateUserByAuthToken(String authToken) {
        if (authToken != null && TokenToUser.containsKey(authToken)) {
            if (!hasTokenExpired(authToken)){
                return TokenToUser.get(authToken);
            }
        }
        return null;
    }

    /**
     * Check if Token has expired.
     * Note: Automatically refreshes the Token if it is not expired.
     * @param authToken Generated authToken from method generateUserAuthToken()
     * @return true or false
     */
    private Boolean hasTokenExpired(String authToken) {
        int expirationSeconds = 300;
        if (TokenToDate.containsKey(authToken)){
            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TokenToDate.get(authToken).getTime());
            if(timeDiff > expirationSeconds) {
                return true;
            }
            refreshToken(authToken);
            return false;
        }
        return true;
    }

    /**
     * Refreshes Token. Use only for still valid tokens.
     * @param authToken Generated authToken from method generateUserAuthToken()
     */
    private void refreshToken(String authToken){
        if(TokenToDate.containsKey(authToken)){
            TokenToDate.get(authToken).setTime(System.currentTimeMillis());
        }
    }

    /**
     * Generates Token for an User
     * @param username Username of any User
     * @return Generated authToken
     */
    public String generateUserAuthToken(String username) {
        List<User> allUsers = pb.getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)){
                byte authTokenBytes[] = new byte[32];
                rnd.nextBytes(authTokenBytes);
                setAuthTokenOfUser(user, Arrays.toString(authTokenBytes));
                return Arrays.toString(authTokenBytes);
            }
        }
        return "";
    }

    /**
     * Puts generated Token for a specific User to a session map
     * @param user User entity instance
     * @param authToken Generated authToken from method generateUserAuthToken()
     */
    private void setAuthTokenOfUser(User user, String authToken) {
        if (TokenToUser.containsKey(authToken) || TokenToUser.containsValue(user)) {
            System.out.println("User already registered.");
            invalidateAuthToken(authToken);
            authToken = generateUserAuthToken(user.getUsername());
            System.out.println("New registration generated.");
        }
        TokenToUser.put(authToken, user);
        TokenToDate.put(authToken, new Date());
    }

    /**
     * Remove Token from session map
     * @param authToken Generated authToken from method generateUserAuthToken()
     */
    public void invalidateAuthToken(String authToken) {
        if (!TokenToUser.containsKey(authToken)) {
            System.out.println("Invalidating not existing token.");
        } else {
            TokenToUser.remove(authToken);
            TokenToDate.remove(authToken);
        }
    }
}
