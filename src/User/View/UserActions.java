package User.View;
import User.Model.User;
import User.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class UserActions {

    public static UserRepository getUserRepository(){
        return UserRepository.getInstance("src/Data/User_List.csv");

    }

    public static User login(String hospitalId, String password){
        UserRepository repo = getUserRepository();
        User foundUser = repo.read(hospitalId);
        if (foundUser == null) {
            System.out.println("User not found.");
            return null;
        }
        // user exists
        // 1. check the blacklist
        if (foundUser.isBlacklist()) {
            System.out.println("User is blacklisted.");
            return null;
        }
        // 2. check the password
        String hashedPassword = hashPassword(password);
//        System.out.println(hashedPassword);
        if(!hashedPassword.equals(foundUser.getPasswordHash())) {
            System.out.println("Incorrect password.");
            return null;
        }
        // 3. all checks passed
        System.out.println("Login successful.");
        return foundUser;
    }

    public static User updatePassword(User user, String password){
        //UserRepository repo = getUserRepository();
        if (user == null) {
            System.out.println("User not found, have you logged in?");
            return null;
        }
        // user not null, just call update...
        user.setPasswordHash(hashPassword(password));
        user = updateLastLoginToNow(user);
        return user;
    }

    public static User updateLastLoginToNow(User user){
        user.setLastLogin(LocalDateTime.now());
        UserRepository repo = getUserRepository();
        User res = repo.update(user);
        return res;
    }

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

    public void viewProfile(User user){
        System.out.println("====User Profile===");
        System.out.println(user);
    }
}
