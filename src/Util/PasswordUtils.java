package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    public static String hashPassword(String password) {
        try {
            // more info found here: https://www.baeldung.com/sha-256-hashing-java
            // create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // convert the password string to bytes and hash it
            byte[] hashedBytes = digest.digest(password.getBytes());

            // convert hashed bytes to a hexadecimal format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256 algorithm", e);
        }
    }
}
