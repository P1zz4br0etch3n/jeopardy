package org.dhbw.mosbach.ai.javae.jeopardy.model;

import org.dhbw.mosbach.ai.javae.jeopardy.bean.PersistenceBean;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Authentication {
    private SecureRandom rnd = new SecureRandom();
    private HashMap<String, User> TokenToUser = new HashMap<>();

    public User authenticateUserByUsernameAndPassword(String username, String password){
        //TODO Doesn"t work, unknown reason        v
        List<User> allUsers = PersistenceBean.getAllUsers();
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
}
