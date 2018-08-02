package org.dhbw.mosbach.ai.javae.jeopardy.model;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * Helper class for hashing passwords
 */
final class HashHelper {
    static String Hash(String s){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = new byte[0];
        if (digest != null) {
            hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        }
        return new String(hash);
    }
}
