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

@Named
@ApplicationScoped
public class AuthenticationBean {

    @Inject
    private PersistenceBean pb;


    /*
        Authentication
     */
    private SecureRandom rnd = new SecureRandom();
    private ConcurrentHashMap<String, User> TokenToUser = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Date> TokenToDate = new ConcurrentHashMap<>();

    public User authenticateUserByUsernameAndPassword(User user) {
        try {
            User u = pb.getUserByUsername(user.getUsername());
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
        List<User> allUsers = pb.getAllUsers();
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
