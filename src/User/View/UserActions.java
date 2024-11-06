package User.View;
import User.Model.User;
import User.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserActions {
    public User user;

    public static void main(String[] args){
        User user = UserActions.login("guest", "password");
        if (user == null){
            System.out.println("User not found");
            return;
        }
        UserActions userActions = new UserActions(user);
        userActions.viewProfile();
    }

    public UserActions(User user){
        this.user = user;
    }

    public static User login(String hospitalId, String password){
        UserRepository repo = UserRepository.getInstance("src/Data/User_List.csv");
        User foundUser = repo.read(hospitalId);
        if (foundUser == null) {
            return null;
        }
        // user exists
        // 1. check the blacklist
        if (foundUser.isBlacklist())
            return null;
        // 2. check the password
        String hashedPassword = hashPassword(password);
//        System.out.println(hashedPassword);
        if(!hashedPassword.equals(foundUser.getPasswordHash())) // passwords don't match
            return null;
        // 3. all checks passed
        return foundUser;
    }

    public boolean updatePassword(String password){
        UserRepository repo = UserRepository.getInstance("src/Data/User_List.csv");
        if (this.user == null) {
            System.out.println("User not found, have you logged in?");
            return false;
        }
        // user not null, just call update...
        this.user.setPasswordHash(hashPassword(password));
        repo.update(this.user);
        repo.store();
        return true;
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

    public void viewProfile(){
        System.out.println("====User Profile===");
        System.out.println(this.user);
    }
}
